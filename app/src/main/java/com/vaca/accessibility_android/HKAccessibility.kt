package com.haohuoke.core

import android.content.Intent
import android.provider.Settings
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.haohuoke.core.step.StepOperator

object HKAccessibility {
    @JvmStatic
    var service: HKAccessibilityService? = null
    fun init() {
        LogUtils.getConfig().globalTag = Config.logTag
    }

    object Config {
        //默认步骤间隔时间，毫秒
        var defaultStepDelay: Long = 2500

        //日志TAG
        var logTag = "hk_auto_operation_log"
    }
    @JvmStatic
    fun openAccessibilitySetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        ActivityUtils.startActivity(intent)
    }

    fun isAccessibilityServiceEnabled(): Boolean {
        return service != null
    }


    object ListenerManager {
        @JvmStatic
        val globalListeners: ArrayList<ServiceListener> = arrayListOf()
        val stepListener: ArrayList<StepListener> = arrayListOf()
        var gestureListener: GestureListener? = null

        interface ServiceListener {
            fun onServiceConnected(service: HKAccessibilityService) {}
            fun onUnbind() {}
        }

        interface StepListener {
            fun onStepStop() {}
            fun onStep(step: StepOperator) {}
            fun onLoop(step: StepOperator) {}
            fun onIntercept(step: StepOperator): Boolean {
                return false
            }
        }

        interface GestureListener {
            fun onGestureBegin(startLocation: FloatArray, endLocation: FloatArray): Long {
                return 0
            }
            fun onGestureCancelled() {}

            fun onGestureCompleted() {}

            fun onGestureEnd() {}
        }
    }
}