package com.haohuoke.homeindexmodule.ui.accessibility.step


import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager


class OpenDouYinAttetionAcAutoOperation : StepImpl {
	
	override fun onImpl(collector: StepCollector) {
		var isSuccessFindUserFace = false
		collector.next(Step.STEP_1) { step ->



			StepManager.execute(
				this::class.java,
				Step.STEP_2,
				2500,
				data = step.data,
				content = step.content
			)
			return@next
		}.nextLoop(Step.STEP_2) { step ->
			return@nextLoop true


		}
	}
}