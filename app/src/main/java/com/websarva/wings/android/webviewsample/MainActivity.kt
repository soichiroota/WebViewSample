package com.websarva.wings.android.webviewsample

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    private var _myWebView: WebView? = null
    private val _myUrl: String = "http://google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _myWebView = findViewById(R.id.webview)
        _myWebView?.settings?.javaScriptEnabled = true
        _myWebView?.webViewClient = MyWebViewClient()
        _myWebView?.loadUrl(_myUrl)
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (Uri.parse(url).host == _myUrl) {
                // This is my web site, so launch another Activity that handles URLs
                    //
                Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    startActivity(this)
                }
                return true
            }
            // Otherwise, the link is not for a page on my site, so do not override; let my WebView load the page
            return false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && _myWebView?.canGoBack() == true) {
            _myWebView?.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}