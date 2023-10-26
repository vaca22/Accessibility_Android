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

            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                Log.e(TAG, "TYPE_VIEW_SCROLLED")
                Log.d("TAG", "packageName = $packageName, className = $className")
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

