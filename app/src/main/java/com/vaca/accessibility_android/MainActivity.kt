package com.vaca.accessibility_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.haohuoke.core.HKAccessibility
import com.haohuoke.core.HKAccessibilityService
import com.haohuoke.core.step.StepManager
import com.haohuoke.homeindexmodule.ui.accessibility.data.IPO3MainAcData
import com.vaca.accessibility_android.private.accessibility.step.OpenDouYinAttetionAcAutoOperation
import com.vaca.accessibility_android.private.accessibility.step.Step
import com.vaca.accessibility_android.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        HKAccessibility.ListenerManager.globalListeners.add(object :
            HKAccessibility.ListenerManager.ServiceListener {
            override fun onServiceConnected(service: HKAccessibilityService) {
                StepManager.isStop = false
                StepManager.execute(
                    OpenDouYinAttetionAcAutoOperation::class.java,
                    Step.STEP_1,
                    isBegin = true,
                    data = IPO3MainAcData(
                        accoutName = "22",
                        keyworldList = null,
                        privateContent = null,
                        xiansuoCount = 5,
                        fansCount = 6,
                        mainTitle = "关注获客"
                    ),
                    content = this@MainActivity
                )
            }

            override fun onUnbind() {

            }
        })


        binding.barrierFree.setOnClickListener {
            Log.e("TAG", "onCreate:初始化")
            try {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                Log.e("TAG", "onCreate:初始化  try")
            } catch (e: Exception) {
                Log.e("TAG", "onCreate:初始化  catch")
                startActivity(Intent(Settings.ACTION_SETTINGS))
                e.printStackTrace()
            }
        }


//        binding.start.setOnClickListener {
//            Log.e("vaca", "onCreate: 开始")
//            val intent = packageManager.getLaunchIntentForPackage("com.tencent.mm")
//            if (intent != null) {
//                intent.flags = FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "没有安装", Toast.LENGTH_LONG).show()
//            }
//        }
    }


    external fun stringFromJNI(): String

    private fun checkAppInstalled(context: Context, pName: String?): Boolean {

        return true
    }

    companion object {
        init {
            System.loadLibrary("accessibility_android")
        }
    }
}