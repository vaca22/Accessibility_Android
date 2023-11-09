package com.haohuoke.core.ext

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.haohuoke.core.ui.UIOperate

private val boundsInScreen = Rect()

fun AccessibilityNodeInfo.getBoundsInScreen(): Rect {
    getBoundsInScreen(boundsInScreen)
    return boundsInScreen
}

fun AccessibilityNodeInfo.clickScreen(): Long {
    val rect = getBoundsInScreen()
    return UIOperate.clickScreen(rect.left + 15F, rect.top + 15F, 10)
}

fun AccessibilityNodeInfo.click() {
    performAction(AccessibilityNodeInfo.ACTION_CLICK)
}

fun AccessibilityNodeInfo.clickUI() {
   //find this centerX centerY
    val rect = getBoundsInScreen()
    val centerX = rect.centerX()
    val centerY = rect.centerY()
    UIOperate.clickScreen(centerX.toFloat(), centerY.toFloat(), 30)
}

fun AccessibilityNodeInfo.clickScreenLong(): Long {
    val rect = getBoundsInScreen()
    return UIOperate.clickScreen(rect.left + 15F, rect.top + 15F, 1000)
}


