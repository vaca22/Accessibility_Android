package com.vaca.accessibility_android.private.accessibility.step


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.blankj.utilcode.util.ScreenUtils
import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager
import com.haohuoke.core.ui.UIOperate
import com.tencent.bugly.proguard.x
import com.vaca.accessibility_android.MainApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class OpenDouYinAttetionAcAutoOperation : StepImpl {

    data class ContactItem(val index: Int, val name: String, val clickNode: AccessibilityNodeInfo)

    data class ChatItem(val name: String, val content: String)

    //"微健刘工刘世伟"  "黄健桓"
    var targetName = "小豆（小助手）"
    val dataScope = CoroutineScope(Dispatchers.IO)


    override fun onImpl(collector: StepCollector) {
        var isSuccessFindUserFace = false
        collector.next(Step.STEP_1) { step ->
            Log.e("vaca", "step1")


            val intent =
                MainApp.application.packageManager.getLaunchIntentForPackage("com.smile.gifmaker")
            if (intent != null) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                MainApp.application.startActivity(intent)
            } else {
                Toast.makeText(MainApp.application, "没有安装", Toast.LENGTH_LONG).show()
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
            UIOperate.findByText("查找").forEach {
                if (it.isClickable) {
                    it.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_3,
                        1500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }
            }


            return@next
        }.next(Step.STEP_3) { step ->
            Log.e("vaca", "step3")
            UIOperate.findByTags("android.widget.EditText").forEach {
                val arguments2 = Bundle()
                arguments2.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "好吃"
                )
                it.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments2)

                StepManager.execute(
                    this::class.java,
                    Step.STEP_4,
                    2500,
                    data = step.data,
                    content = step.content
                )
                return@next
            }


            return@next


        }.next(Step.STEP_4) { step ->
            Log.e("vaca", "step4")


            UIOperate.findById("com.smile.gifmaker:id/right_button").forEach {
                if (it.isClickable) {
                    if (it.text == "搜索") {
                        it.click()
                        StepManager.execute(
                            this::class.java,
                            Step.STEP_5,
                            2500,
                            data = step.data,
                            content = step.content
                        )
                        return@next
                    }
                }
            }


            return@next


        }.next(Step.STEP_5) { step ->
            Log.e("vaca", "step5")
            UIOperate.findById("com.smile.gifmaker:id/tab_filter_image").forEach {
                if (it.isClickable) {
                    it.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_6,
                        2500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }

            }


            return@next
        }.next(Step.STEP_6) { step ->
            Log.e("vaca", "step6")

            UIOperate.findByTags("android.widget.TextView").forEach {
                if (it.text == "近1月") {
                    if (it.isClickable) {
                        it.click()
                        StepManager.execute(
                            this::class.java,
                            Step.STEP_7,
                            2500,
                            data = step.data,
                            content = step.content
                        )
                        return@next
                    }
                }
            }
            StepManager.execute(
                this::class.java,
                Step.STEP_5,
                2500,
                data = step.data,
                content = step.content
            )
            return@next
            return@next
        }.next(Step.STEP_7) { step ->
            Log.e("vaca", "step7")
            UIOperate.findByTags("android.widget.TextView").forEach {
                if (it.text == "确定") {
                    if (it.isClickable) {
                        it.click()
                        StepManager.execute(
                            this::class.java,
                            Step.STEP_8,
                            2500,
                            data = step.data,
                            content = step.content
                        )
                        return@next
                    }
                }
            }

        }.next(Step.STEP_8) { step ->
            Log.e("vaca", "step8")
            UIOperate.findByTags("androidx.recyclerview.widget.RecyclerView").forEach {
                val count1 = it.childCount
                for (k in 0 until count1) {
                    val child = it.getChild(k)
                    if (child.className == "android.widget.RelativeLayout") {
                        if (child.isClickable) {
                            child.click()
                            StepManager.execute(
                                this::class.java,
                                Step.STEP_9,
                                3500,
                                data = step.data,
                                content = step.content
                            )
                            return@next
                        }

                    }

                }
            }


        }.next(Step.STEP_9) { step ->
            Log.e("vaca", "step9")
            UIOperate.findById("com.smile.gifmaker:id/comment_count_view").forEach {
                val centerY = it.getBoundsInScreen().centerY()
                Log.e("vaca", "centerY" + centerY + "  " + ScreenUtils.getAppScreenHeight())
                if (centerY > 0 && centerY < ScreenUtils.getAppScreenHeight()) {
                    val text = it.text.toString()
                    if (it.text.toString().contains("抢首评")) {
                        Log.e("vaca", "ftgftext" + text)
                        StepManager.execute(
                            this::class.java,
                            Step.STEP_Scoll_Video,
                            2500,
                            data = step.data,
                            content = step.content
                        )
                        return@next
                    }
                }
            }


            UIOperate.findById("com.smile.gifmaker:id/comment_button").forEach {
                val centerY = it.getBoundsInScreen().centerY()
                if (centerY > 0 && centerY < ScreenUtils.getAppScreenHeight()) {
                    if (it.className == "android.widget.FrameLayout" && it.isClickable) {
                        it.click()
                        StepManager.execute(
                            this::class.java,
                            Step.STEP_10,
                            2500,
                            data = step.data,
                            content = step.content
                        )
                        return@next
                    }
                }
            }

        }.next(Step.STEP_10) { step ->
            Log.e("vaca", "step10")
            UIOperate.findById("com.smile.gifmaker:id/tabs_panel_full").forEach {
                val centerY = it.getBoundsInScreen().centerY()
                if (centerY > 400) {
                    it.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_11,
                        2500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }
            }


        }.next(Step.STEP_11) { step ->
            Log.e("vaca", "step11")
            UIOperate.findById("com.smile.gifmaker:id/recycler_view").forEach {
                val count1 = it.childCount
                for (k in 0 until count1) {
                    val child1 = it.getChild(k)
                    if (child1.className == "android.view.ViewGroup" && child1.viewIdResourceName == "com.smile.gifmaker:id/comment_frame") {
                        UIOperate.findByTags("android.widget.TextView", child1).forEach {
                            if (it.viewIdResourceName == "com.smile.gifmaker:id/comment") {
                                val text = it.text.toString()
                                Log.e("vaca", "content" + text)
                            }
//                            com.smile.gifmaker:id/name
                            if (it.viewIdResourceName == "com.smile.gifmaker:id/name") {
                                val text = it.text.toString()
                                Log.e("vaca", "name" + text)
                            }
                        }
                        UIOperate.findByTags("android.widget.ImageView", child1).forEach {
                            if (it.viewIdResourceName == "com.smile.gifmaker:id/avatar") {
                                if (it.isClickable) {
                                    it.click()
                                    StepManager.execute(
                                        this::class.java,
                                        Step.STEP_12,
                                        2500,
                                        data = step.data,
                                        content = step.content
                                    )
                                    return@next

                                }
                            }
                        }
                    }

                }
            }

        }.next(Step.STEP_12) { step ->
            Log.e("vaca", "step11")
            UIOperate.findById("com.smile.gifmaker:id/send_message_small_icon").forEach {
                if (it.isClickable) {
                    it.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_13,
                        2500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }
            }


        }.next(Step.STEP_13) { step ->
            Log.e("vaca", "step_13")
            UIOperate.findByTags("android.widget.EditText").forEach {
                val arguments2 = Bundle()
                arguments2.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "好吃"
                )
                it.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments2)

                StepManager.execute(
                    this::class.java,
                    Step.STEP_14,
                    2500,
                    data = step.data,
                    content = step.content
                )
                return@next
            }

        }.next(Step.STEP_14) { step ->
            Log.e("vaca", "step_14")
            UIOperate.findById("com.smile.gifmaker:id/send_btn").forEach {
                if (it.isClickable) {
                    it.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_15,
                        2500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }
            }


        }.next(Step.STEP_15) { step ->
            Log.e("vaca", "step_15")
            UIOperate.findById("com.smile.gifmaker:id/tabs_panel_full").forEach {
                StepManager.execute(
                    this::class.java,
                    Step.STEP_11,
                    2500,
                    data = step.data,
                    content = step.content
                )
                return@next
            }
            UIOperate.back()
            StepManager.execute(
                this::class.java,
                Step.STEP_15,
                2500,
                data = step.data,
                content = step.content
            )

        }.next(Step.STEP_Scoll_Video) { step ->
            Log.e("vaca", "step_Scoll_Video")
            val x = ScreenUtils.getAppScreenWidth() / 2F
            val distance = ScreenUtils.getAppScreenHeight() / 2F
            val startY = distance + distance / 2F
            val endY = distance - distance / 2F
            val delay = UIOperate.gesture(
                floatArrayOf(x, startY), floatArrayOf(x, endY), 0, 300L
            )
            StepManager.execute(
                this::class.java,
                Step.STEP_9,
                2500,
                data = step.data,
                content = step.content
            )

        }.next(Step.STEP_Scoll_Comment) { step ->
            Log.e("vaca", "step_Scoll_Comment")

            val x = ScreenUtils.getAppScreenWidth() / 2F
            val distance = ScreenUtils.getAppScreenHeight() / 2F
            val startY = distance + distance / 2F
            val endY = distance - distance / 2F
            val delay = UIOperate.gesture(
                floatArrayOf(x, startY), floatArrayOf(x, endY), 0, 300L
            )
        }
    }
}