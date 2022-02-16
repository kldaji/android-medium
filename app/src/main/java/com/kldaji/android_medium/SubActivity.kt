package com.kldaji.android_medium

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebViewClient
import com.kldaji.android_medium.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private val binding: ActivitySubBinding by lazy { ActivitySubBinding.inflate(layoutInflater) }
    private lateinit var cookieManager: CookieManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cookieManager = CookieManager.getInstance()
        loadWebViewUrl()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebViewUrl() {
        with(binding.wvSub) {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {}
            loadUrl("https://canvas.skku.edu/courses/28046/grades")
        }
    }
}
