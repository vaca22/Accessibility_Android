package com.haohuoke.homeindexmodule.ui.accessibility.step


import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.util.Log
import android.widget.Toast
import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager
import com.haohuoke.core.ui.UIOperate
import com.vaca.accessibility_android.MainApp


class OpenDouYinAttetionAcAutoOperation : StepImpl {

    override fun onImpl(collector: StepCollector) {
        var isSuccessFindUserFace = false
        collector.next(Step.STEP_1) { step ->
            Log.e("vaca", "step1")


            val intent =
                MainApp.application.packageManager.getLaunchIntentForPackage("com.tencent.mm")
            if (intent != null) {
                intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                MainApp.application.startActivity(intent)
            } else {
                Toast.makeText( MainApp.application, "没有安装", Toast.LENGTH_LONG).show()
            }
            StepManager.execute(
                this::class.java,
                Step.STEP_2,
                3500,
                data = step.data,
                content = step.content
            )
            return@next
        }.next(Step.STEP_2) { step ->
            Log.e("vaca", "step2")

            val more=UIOperate.findByText("更多功能按钮")
            val count=more.size
            if(count>0){
                StepManager.execute(
                    this::class.java,
                    Step.STEP_3,
                    2500,
                    data = step.data,
                    content = step.content
                )
                return@next
            }

            UIOperate.back()
            StepManager.execute(
                this::class.java,
                Step.STEP_2,
                2500,
                data = step.data,
                content = step.content
            )
            return@next
        }.next(Step.STEP_3) { step ->
            Log.e("vaca", "step3")


            UIOperate.findByTags("android.widget.ListView").forEach {
                val chileCount=it.childCount
                for(i in 0 until chileCount){
                    val child=it.getChild(i)
                    if(child!=null){
                        if(child.className=="android.widget.LinearLayout"){
                            val childCount=child.childCount
                            for(j in 0 until childCount){
                                val childChild=child.getChild(j)
                                if(childChild!=null){
                                    if(childChild.className=="android.widget.LinearLayout"){
                                       val childChildCount=childChild.childCount
                                            for(k in 0 until childChildCount){
                                                val childChildChild=childChild.getChild(k)
                                                if(childChildChild!=null){
                                                    if(childChildChild.className=="android.widget.LinearLayout"){
                                                        val childChildChildCount=childChildChild.childCount
                                                        if(childChildChildCount>0){
                                                            //get 0
                                                            val childChildChildChild=childChildChild.getChild(0)
                                                            if(childChildChildChild!=null){
                                                                if(childChildChildChild.className=="android.widget.LinearLayout"){
                                                                    //get 0
                                                                    val childChildChildChildChild=childChildChildChild.getChild(0)
                                                                    if(childChildChildChildChild!=null){
                                                                        if(childChildChildChildChild.className=="android.widget.LinearLayout"){
                                                                            //get 0
                                                                            val childChildChildChildChildChild=childChildChildChildChild.getChild(0)
                                                                            //get text
                                                                            if(childChildChildChildChildChild!=null){
                                                                                if(childChildChildChildChildChild.className=="android.view.View"){
                                                                                    Log.e("vaca",
                                                                                        childChildChildChildChildChild.text.toString()
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            return@next


        }.next(Step.STEP_4) { step ->
            StepManager.execute(
                this::class.java,
                Step.STEP_Me_Page,
                2500,
                data = step.data,
                content = step.content
            )
            return@next
            return@next


        }.next(Step.STEP_5) { step ->
                StepManager.execute(
                    this::class.java,
                    Step.STEP_Me_Page,
                    2500,
                    data = step.data,
                    content = step.content
                )
                return@next
                return@next}
    }
}