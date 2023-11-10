package com.vaca.accessibility_android.net

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object NetCmd {

    private val chatGpt=OkHttpClient().newBuilder().connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()


    fun postString(content:String): String {
        val url="https://vaca.vip/asr/asr"
        val requestBody: RequestBody = content.toRequestBody("application/octet-stream".toMediaTypeOrNull())
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val response = chatGpt.newCall(request).execute()
        return response.body!!.string()
    }
}