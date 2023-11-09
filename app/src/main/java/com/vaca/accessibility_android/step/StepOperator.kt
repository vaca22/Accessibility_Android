package com.haohuoke.core.step

import android.content.Context
import android.os.CountDownTimer
import com.blankj.utilcode.util.ThreadUtils
import com.haohuoke.core.HKAccessibility

class StepOperator(
	private val clazzName: String,
	val step: Int,
	val loopDuration: Long = 0,
	val next: ((stepOperator: StepOperator) -> Boolean)? = null,
) {
	
	private var loopDownTimer: CountDownTimer? = null
	var loopSurplusTime: Long = 0
	var loopSurplusSecond: Float = 0f
	var data: Any? = null
	var content: Context? = null
	var attetionStepIndex: Int? = 0
	
	fun execute(delay: Long) {
		if (StepManager.isStop) {
			HKAccessibility.ListenerManager.stepListener.forEach { it.onStepStop() }
			return
		}
		
		next?.let {
			if (loopDuration == 0L) {
				ThreadUtils.runOnUiThreadDelayed({
					if (StepManager.isStop) {
						HKAccessibility.ListenerManager.stepListener.forEach { it.onStepStop() }
						return@runOnUiThreadDelayed
					}
					onStep(it)
				}, delay)
			} else {
				ThreadUtils.runOnUiThreadDelayed({
					if (StepManager.isStop) {
						loopDownTimer?.cancel()
						loopDownTimer = null
						HKAccessibility.ListenerManager.stepListener.forEach { it.onStepStop() }
						return@runOnUiThreadDelayed
					}
					loopDownTimer?.cancel()
					loopDownTimer = object : CountDownTimer(loopDuration, 250) {
						override fun onTick(millisUntilFinished: Long) {
							loopSurplusTime = millisUntilFinished
							loopSurplusSecond = millisUntilFinished / 1000f
							if (StepManager.isStop) HKAccessibility.ListenerManager.stepListener.forEach { it.onStepStop() }
							HKAccessibility.ListenerManager.stepListener.forEach { it.onLoop(this@StepOperator) }
							if (onStep(it) || StepManager.isStop) {
								cancel()
								loopDownTimer = null
							}
						}
						
						override fun onFinish() {
							loopSurplusTime = 0
							loopSurplusSecond = 0f
							onStep(it)
						}
						
					}
					loopDownTimer?.start()
				}, delay)
			}
		} ?: let {
		}
	}
	
	private fun onStep(it: (stepOperator: StepOperator) -> Boolean): Boolean {
		HKAccessibility.ListenerManager.stepListener.forEach { if (it.onIntercept(this)) return true }
		HKAccessibility.ListenerManager.stepListener.forEach { it.onStep(this) }
		return it.invoke(this)
	}
}