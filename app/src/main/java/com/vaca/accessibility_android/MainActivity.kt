package com.vaca.accessibility_android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vaca.accessibility_android.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.barrierFree.setOnClickListener {

            if (!MyService.isStart) {
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
        }


        binding.start.setOnClickListener {
            val exist1 = checkAppInstalled(this@MainActivity, "com.ss.android.ugc.aweme")
            if (exist1) {
                val intent = Intent()
                intent.data = Uri.parse("snssdk1128://feed?refer=web&gd_label=1")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "请先安装此应用", Toast.LENGTH_SHORT).show()
            }
        }
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