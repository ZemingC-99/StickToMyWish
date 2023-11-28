package com.cs501.sticktomywish

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class QuoteFetcher {

    private val client = OkHttpClient()

    fun fetchQuote(onResult: (String) -> Unit) {
        val request = Request.Builder()
            .url("https://zenquotes.io/api/random")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onResult("Error: ${response.code}")
                    return
                }

                val responseData = response.body?.string()
                onResult(responseData ?: "Error: Empty response")
            }

            override fun onFailure(call: Call, e: IOException) {
                onResult("Failed to fetch quote")
            }
        })
    }
}