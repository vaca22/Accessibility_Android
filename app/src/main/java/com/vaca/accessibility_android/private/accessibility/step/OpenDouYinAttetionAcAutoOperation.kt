package com.haohuoke.homeindexmodule.ui.accessibility.step


import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.haohuoke.core.ext.*
import com.haohuoke.core.step.StepCollector
import com.haohuoke.core.step.StepImpl
import com.haohuoke.core.step.StepManager
import com.haohuoke.core.ui.UIOperate
import com.vaca.accessibility_android.MainApp


class OpenDouYinAttetionAcAutoOperation : StepImpl {

    data class ContactItem(val index:Int,val name:String,val clickNode: AccessibilityNodeInfo)

    data class ChatItem(val name:String,val content:String)


    fun getChatList():ArrayList<ChatItem>{
        val result= ArrayList<ChatItem>()

        UIOperate.findByTags("androidx.recyclerview.widget.RecyclerView").forEach {
            //check chile is relativeLayout
            val chileCount=it.childCount
            for(i in 0 until chileCount){
                val child=it.getChild(i)
                if(child.className=="android.widget.RelativeLayout"){
                    val childChildCount=child.childCount
                    for(j in 0 until childChildCount){
                        val childChild=child.getChild(j)
                        if(childChild.className=="android.widget.LinearLayout"){
                            val childChildChildCount=childChild.childCount
                            var content=""
                            var name=""
                            for(k in 0 until childChildChildCount){
                                val childChildChild=childChild.getChild(k)

                                if(childChildChild.className=="android.widget.LinearLayout"){
                                    UIOperate.findByTags("android.widget.TextView",childChildChild).forEach {
                                        if(it.text.isNotEmpty()){
                                            content=it.text.toString()
                                            Log.e("vaca", "content=$content")
                                        }
                                    }
                                }else if(childChildChild.className=="android.widget.RelativeLayout"){
                                    val childChildChildChildCount=childChildChild.childCount
                                    for(l in 0 until childChildChildChildCount){
                                        val childChildChildChild=childChildChild.getChild(l)
                                        if(childChildChildChild.className=="android.widget.ImageView"){
                                            val cds=childChildChildChild.contentDescription
                                            cds?.let {
                                                if(it.isNotEmpty()){
                                                    name=it.toString()
                                                }
                                            }
                                        }
                                    }

                                }


                            }
                            if(content.isNotEmpty()&&name.isNotEmpty()){
                                val chatItem=ChatItem(name,content)
                                result.add(chatItem)
                            }
                        }
                    }
                }
            }


        }

        return result
    }


    fun getListName():ArrayList<ContactItem> {
        val result= ArrayList<ContactItem>()

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
                                                                            Log.e("vaca", "text=${childChildChildChildChildChild.text.toString()}")
                                                                            val contactItem=ContactItem(i,childChildChildChildChildChild.text.toString(),child)
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

            val result=getListName()

            val count=result.size
            Log.e("vaca", "count=$count")
            for(i in 0 until count){
                val name=result[i]
                Log.e("vaca", "name=${name.name} index=${name.index}")
                if(name.name=="全海燕"){
                    name.clickNode.click()
                    StepManager.execute(
                        this::class.java,
                        Step.STEP_4,
                        3500,
                        data = step.data,
                        content = step.content
                    )
                    return@next
                }
            }


            return@next


        }.next(Step.STEP_4) { step ->
            Log.e("vaca", "step4")

            val result=getChatList()
            result.forEach{
                Log.e("vaca", "name=${it.name} content=${it.content}")
            }

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