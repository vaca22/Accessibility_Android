package com.vaca.accessibility_android

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.app.Service
import android.content.Intent
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi


class MyService : AccessibilityService() {
    private val TAG = "vaca"
    var list2: List<AccessibilityNodeInfo>? = null
    var list3: List<AccessibilityNodeInfo>? = null
    var list4: List<AccessibilityNodeInfo>? = null
    var list5: List<AccessibilityNodeInfo>? = null
    var list6: List<AccessibilityNodeInfo>? = null
    var list7: List<AccessibilityNodeInfo>? = null
    var list8: List<AccessibilityNodeInfo>? = null

    var st = ""

    //初始化
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.e(
            TAG, """
     onServiceConnected:O(∩_∩)O~~
     锁定中...
     """.trimIndent()
        )
        mService = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val rootNodeInfo = rootInActiveWindow
        val eventType = event.eventType
        val packageName = event.packageName.toString()
        val className = event.className.toString()
        when (eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
               // Log.e(TAG, "AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED跳转到新的页面")
                if (className == "com.ss.android.ugc.aweme.main.MainActivity") {
                    list8 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/a-5")
                    if (null != list8) {
                        for (info in list8!!) {
                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }
                    }
                    list2 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/hox")
                    if (null != list2) {
                        for (info in list2!!) {
//                            Log.e(TAG, info.toString())
                            info.parent.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }
                    }
                }
                if (className == "com.lynx.tasm.behavior.KeyboardMonitor") {
                    Log.e(TAG, "执行了输入框")
                    list3 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/et_search_kw")
                    if (null != list3) {
                        for (nodeInfo in list3!!) {
                            val arguments = Bundle()
                            arguments.putCharSequence(
                                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                                "111111"
                            )
                            nodeInfo.performAction(
                                AccessibilityNodeInfo.ACTION_SET_TEXT,
                                arguments
                            )
                        }
                    }
                }
               // Log.e(TAG, "AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED某个view的内容发生的变化")
                Log.e(TAG, "onServiceConnected:" + "实现辅助功能")
                Log.d("vaca", "packageName = $packageName, className = $className")
                if (className == "com.lynx.tasm.behavior.KeyboardMonitor") {
                    Log.e(TAG, "执行了搜索按钮")
                    list5 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/rzy")
                    if (null != list5) {
                        for (info in list5!!) {
                            clickByNode(this, info)
                        }
                    }
                }
                if (className == "androidx.recyclerview.widget.RecyclerView") {
                    list6 = rootNodeInfo.findAccessibilityNodeInfosByText("用户")
                    if (null != list6) {
                        for (info in list6!!) {
                            //Log.e(TAG, info.toString())
                            clickByNode(this, info.parent)
                        }
                    }
                    list7 = rootNodeInfo.findAccessibilityNodeInfosByText("关注")
                    if (null != list7) {
                        for (info in list7!!) {
                           // Log.e(TAG, info.toString())
                        }
                    }
                }
                val rootNode = rootInActiveWindow
                //匹配Text获取节点
                val list1 = rootNode.findAccessibilityNodeInfosByText("match_text")
                //匹配id获取节点
                val list2 = rootNode.findAccessibilityNodeInfosByViewId("match_id")
                //获取子节点
//                val infoNode = rootNode.getChild(index)
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
//                Log.e(TAG, "AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED某个view的内容发生的变化")
//                Log.e(TAG, "onServiceConnected:" + "实现辅助功能")
                Log.d("TAG", "packageName = $packageName, className = $className")
                if (className == "com.lynx.tasm.behavior.KeyboardMonitor") {
                    Log.e(TAG, "执行了搜索按钮")
                    list5 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/rzy")
                    if (null != list5) {
                        for (info in list5!!) {
                            clickByNode(this, info)
                        }
                    }
                }
                if (className == "androidx.recyclerview.widget.RecyclerView") {
                    list6 = rootNodeInfo.findAccessibilityNodeInfosByText("用户")
                    if (null != list6) {
                        for (info in list6!!) {
                          //  Log.e(TAG, info.toString())
                            clickByNode(this, info.parent)
                        }
                    }
                    list7 = rootNodeInfo.findAccessibilityNodeInfosByText("关注")
                    if (null != list7) {
                        for (info in list7!!) {
                       //     Log.e(TAG, info.toString())
                        }
                    }
                }
                val rootNode = rootInActiveWindow
                val list1 = rootNode.findAccessibilityNodeInfosByText("match_text")
                val list2 = rootNode.findAccessibilityNodeInfosByViewId("match_id")
//                val infoNode = rootNode.getChild(index)
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                Log.e(TAG, "onServiceConnected:" + "实现辅助功能")
                Log.d("TAG", "packageName = $packageName, className = $className")
                if (className == "com.lynx.tasm.behavior.KeyboardMonitor") {
                    Log.e(TAG, "执行了搜索按钮")
                    list5 =
                        rootNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/rzy")
                    if (null != list5) {
                        for (info in list5!!) {
                            clickByNode(this, info)
                        }
                    }
                }
                if (className == "androidx.recyclerview.widget.RecyclerView") {
                    list6 = rootNodeInfo.findAccessibilityNodeInfosByText("用户")
                    if (null != list6) {
                        for (info in list6!!) {
                       //     Log.e(TAG, info.toString())
                            clickByNode(this, info.parent)
                        }
                    }
                    list7 = rootNodeInfo.findAccessibilityNodeInfosByText("关注")
                    if (null != list7) {
                        for (info in list7!!) {
                       //     Log.e(TAG, info.toString())
                        }
                    }
                }
                val rootNode = rootInActiveWindow
                val list1 = rootNode.findAccessibilityNodeInfosByText("match_text")
                val list2 = rootNode.findAccessibilityNodeInfosByViewId("match_id")
                //log all node info
                //log all node info
//                for(i in 0 until rootNode.childCount){
//                    Log.e(TAG, "onAccessibilityEvent: " + rootNode.getChild(i).toString())
//                }
//                val infoNode = rootNode.getChild(index)
            }
        }
    }

    override fun onInterrupt() {
        Log.e(
            TAG, """
     onInterrupt: (；′⌒`)
     功能被迫中断
     """.trimIndent()
        )
        mService = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(
            TAG, """
     onDestroy: %>_<%
     功能已关闭
     """.trimIndent()
        )
        mService = null
    }

    companion object {
        var mService: MyService? = null

        /**
         * 实现位置坐标点击
         * @param service
         * @param nodeInfo
         * @return
         */
        fun clickByNode(
            service: AccessibilityService?,
            nodeInfo: AccessibilityNodeInfo?
        ): Boolean {
            if (service == null || nodeInfo == null) {
                return false
            }
            val rect = Rect()
            nodeInfo.getBoundsInScreen(rect)
            val x = rect.centerX()
            val y = rect.centerY()
            Log.e("vaca", "要点击的像素点在手机屏幕位置::" + rect.centerX() + " " + rect.centerY())
            val point = Point(x, y)
            val builder = GestureDescription.Builder()
            val path = Path()
            path.moveTo(point.x.toFloat(), point.y.toFloat())
            builder.addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
            val gesture = builder.build()
            return service.dispatchGesture(gesture, object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                    //                LogUtil.d(TAG, "dispatchGesture onCompleted: 完成...");
                }

                override fun onCancelled(gestureDescription: GestureDescription) {
                    super.onCancelled(gestureDescription)
                    //                LogUtil.d(TAG, "dispatchGesture onCancelled: 取消...");
                }
            }, null)
        }
        // 公共方法
        /**
         * 辅助功能是否启动
         */
        val isStart: Boolean
            get() = mService != null
    }
}

