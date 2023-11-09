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
import com.vaca.accessibility_android.MyService.Companion.clickByNode


class MyService : AccessibilityService() {
    private val TAG = "vaca"




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
        Log.e(TAG, "onAccessibilityEvent: eventType = $eventType")
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
            if(x<500){
                return false
            }
            Log.e("vaca", "x = $x, y = $y")
            val point = Point(x, y)
            val builder = GestureDescription.Builder()
            val path = Path()
            path.moveTo(point.x.toFloat(), point.y.toFloat())
            builder.addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
            val gesture = builder.build()
            return service.dispatchGesture(gesture, object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                }

                override fun onCancelled(gestureDescription: GestureDescription) {
                    super.onCancelled(gestureDescription)

                }
            }, null)
        }

        val isStart: Boolean
            get() = mService != null
    }
}

