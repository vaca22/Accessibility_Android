package com.vaca.accessibility_android.utils

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity

import com.vaca.accessibility_android.MainApp

object CopyUtils {
    fun copyStr(copyStr: String): Boolean {
        return try {
            //获取剪贴板管理器
            val cm: ClipboardManager =
                MainApp.application.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            val mClipData = ClipData.newPlainText("Label", copyStr)
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData)
            true
        } catch (e: Exception) {
            false
        }
    }
}