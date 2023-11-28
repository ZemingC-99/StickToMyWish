//Reference: https://stackoverflow.com/questions/59560933/how-to-compose-directly-a-tweet-from-my-android-application
package com.cs501.sticktomywish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class TweetCustomWebView : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)

        val extras = intent.extras
        if (extras != null) {
            val stringToShow = extras.getString("tweettext")
            webView = findViewById(R.id.wv)

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.contains("latest_status_id=")) {
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    }
                    view.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    // Finished loading URL
                }

                override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                    Log.e("", "Error: $description")
                    setResult(Activity.RESULT_CANCELED, Intent())
                }
            }
            webView.loadUrl("https://twitter.com/intent/tweet?text=$stringToShow")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED, Intent())
    }
}