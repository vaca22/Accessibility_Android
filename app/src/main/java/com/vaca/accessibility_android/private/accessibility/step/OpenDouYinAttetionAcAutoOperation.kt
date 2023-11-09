package com.haohuoke.homeindexmodule.ui.accessibility.step


import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.widget.Toast
import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager
import com.vaca.accessibility_android.MainApp


class OpenDouYinAttetionAcAutoOperation : StepImpl {

    override fun onImpl(collector: StepCollector) {
        var isSuccessFindUserFace = false
        collector.next(Step.STEP_1) { step ->


            val intent =
                MainApp.application.packageManager.getLaunchIntentForPackage("com.tencent.mm")
            if (intent != null) {
                intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                MainApp.application.startActivity(intent)
            } else {
                Toast.makeText( MainApp.application, "没有安装", Toast.LENGTH_LONG).show()
            }
            return@next
        }.nextLoop(Step.STEP_2) { step ->
            return@nextLoop true


        }
    }
}