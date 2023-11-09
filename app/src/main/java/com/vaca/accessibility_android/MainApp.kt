package com.vaca.accessibility_android

import android.app.Application

class MainApp:Application() {
    companion object{
        lateinit var application: MainApp
    }
    override fun onCreate() {
        super.onCreate()
        application=this
    }
}