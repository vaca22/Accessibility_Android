package com.haohuoke.core

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class HKAccessibilityService : AccessibilityService() {

    override fun onCreate() {
        super.onCreate()
        HKAccessibility.service = this
        HKAccessibility.init()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        HKAccessibility.service = this
        HKAccessibility.ListenerManager.globalListeners.forEach { it.onServiceConnected(this) }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        HKAccessibility.service = this
    }
    
    override fun onInterrupt() {
    
    }
    
    override fun onUnbind(intent: Intent?): Boolean {
        HKAccessibility.ListenerManager.globalListeners.forEach { it.onUnbind() }
        return super.onUnbind(intent)
    }

}