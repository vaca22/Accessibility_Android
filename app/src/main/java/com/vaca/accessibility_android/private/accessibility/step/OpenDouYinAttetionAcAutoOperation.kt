package com.vaca.accessibility_android.private.accessibility.step


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager
import com.haohuoke.core.ui.UIOperate
import com.haohuoke.homeindexmodule.ui.accessibility.data.IPO3MainAcData
import com.haohuoke.homeindexmodule.ui.accessibility.step.Step
import com.vaca.accessibility_android.MainApp
import com.vaca.accessibility_android.net.NetCmd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class OpenDouYinAttetionAcAutoOperation : StepImpl {

    data class ContactItem(val index: Int, val name: String, val clickNode: AccessibilityNodeInfo)

    data class ChatItem(val name: String, val content: String)
//"微健刘工刘世伟"  "黄健桓"
    var targetName = "小豆（小助手）"
    val dataScope = CoroutineScope(Dispatchers.IO)


    fun getChatList(): ArrayList<ChatItem> {
        val result = ArrayList<ChatItem>()

        UIOperate.findByTags("androidx.recyclerview.widget.RecyclerView").forEach {
            //check chile is relativeLayout
            val chileCount = it.childCount
            for (i in 0 until chileCount) {
                val child = it.getChild(i)
                if (child.className == "android.widget.RelativeLayout") {
                    val childChildCount = child.childCount
                    for (j in 0 until childChildCount) {
                        val childChild = child.getChild(j)
                        if (childChild.className == "android.widget.LinearLayout") {
                            val childChildChildCount = childChild.childCount
                            var content = ""
                            var name = ""
                            for (k in 0 until childChildChildCount) {
                                val childChildChild = childChild.getChild(k)

                                if (childChildChild.className == "android.widget.LinearLayout") {
                                    UIOperate.findByTags("android.widget.TextView", childChildChild)
                                        .forEach {
                                            if (it.text.isNotEmpty()) {
                                                content = it.text.toString()
                                            }
                                        }
                                } else if (childChildChild.className == "android.widget.RelativeLayout") {
                                    val childChildChildChildCount = childChildChild.childCount
                                    for (l in 0 until childChildChildChildCount) {
                                        val childChildChildChild = childChildChild.getChild(l)
                                        if (childChildChildChild.className == "android.widget.ImageView") {
                                            val cds = childChildChildChild.contentDescription
                                            cds?.let {
                                                if (it.isNotEmpty()) {
                                                    name = it.toString()
                                                }
                                            }
                                        }
                                    }

                                }


                            }
                            if (content.isNotEmpty() && name.isNotEmpty()) {
                                val chatItem = ChatItem(name, content)
                                result.add(chatItem)
                            }
                        }
                    }
                }
            }


        }

        return result
    }


    fun getListName(): ArrayList<ContactItem> {
        val result = ArrayList<ContactItem>()

        UIOperate.findByTags("android.widget.ListView").forEach {
            val chileCount = it.childCount
            for (i in 0 until chileCount) {
                val child = it.getChild(i)
                if (child != null) {
                    if (child.className == "android.widget.LinearLayout") {
                        val childCount = child.childCount
                        for (j in 0 until childCount) {
                            val childChild = child.getChild(j)
                            if (childChild != null) {
                                if (childChild.className == "android.widget.LinearLayout") {
                                    val childChildCount = childChild.childCount
                                    for (k in 0 until childChildCount) {
                                        val childChildChild = childChild.getChild(k)
                                        if (childChildChild != null) {
                                            if (childChildChild.className == "android.widget.LinearLayout") {
                                                val childChildChildCount =
                                                    childChildChild.childCount
                                                if (childChildChildCount > 0) {
                                                    //get 0
                                                    val childChildChildChild =
                                                        childChildChild.getChild(0)
                                                    if (childChildChildChild != null) {
                                                        if (childChildChildChild.className == "android.widget.LinearLayout") {
                                                            //get 0
                                                            val childChildChildChildChild =
                                                                childChildChildChild.getChild(0)
                                                            if (childChildChildChildChild != null) {
                                                                if (childChildChildChildChild.className == "android.widget.LinearLayout") {
                                                                    //get 0
                                                                    val childChildChildChildChildChild =
                                                                        childChildChildChildChild.getChild(
                                                                            0
                                                                        )
                                                                    //get text
                                                                    if (childChildChildChildChildChild != null) {
                                                                        if (childChildChildChildChildChild.className == "android.view.View") {
                                                                            Log.e(
                                                                                "vaca",
                                                                                "text=${childChildChildChildChildChild.text.toString()}"
                                                                            )
                                                                            val contactItem =
                                                                                ContactItem(
                                                                                    i,
                                                                                    childChildChildChildChildChild.text.toString(),
                                                                                    child
                                                                                )
                                                                            result.add(contactItem)
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



        return result
    }


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
            UIOperate.findByText("查找").forEach{
                if(it.isClickable){
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
                if(it.isClickable){
                    if(it.text=="搜索"){
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
                if(it.isClickable){
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
                if(it.text=="请选择"){
                    if(it.isClickable){
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

            return@next
        }.next(Step.STEP_7) { step ->
            Log.e("vaca", "step7")

            UIOperate.getAllNodes2().forEach {

                   it.text?.let {
                       Log.e("vaca", "step11: $it")
                   }
                it.contentDescription?.let {
                    Log.e("vaca", "step11: $it")
                }
            }
        }
    }
}