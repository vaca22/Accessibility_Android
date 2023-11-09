package com.haohuoke.core.step

import android.content.Context
import com.haohuoke.core.HKAccessibility

object StepManager {

    private val stepCollector: HashMap<String, StepCollector> = hashMapOf()
    var isStop = false

    private fun <T : StepImpl> beginExecute(stepImpl: Class<T>, step: Int, delay: Long = HKAccessibility.Config.defaultStepDelay, data: Any? = null, content:Context?=null): StepManager {
        isStop = false
        execute(stepImpl, step, delay, data, content = content)
        return this
    }

    fun <T : StepImpl> execute(stepImpl: Class<T>, step: Int, delay: Long = HKAccessibility.Config.defaultStepDelay, data: Any? = null, isBegin: Boolean = false, content:Context?=null, attetionStepIndex:Int?=0) {
        if (isStop && !isBegin) return
        stepCollector[stepImpl.name] ?: register(stepImpl)
        stepCollector[stepImpl.name]?.get(step)?.let {
            it.data = data
            it.content = content
            it.attetionStepIndex = attetionStepIndex
            it.execute(delay)
        }
    }
    fun <T : StepImpl> register(stepImpl: Class<T>) {
        StepCollector(stepImpl.name).let {
            stepCollector[stepImpl.name] = it
            stepImpl.newInstance().onImpl(it)
        }
    }
}
