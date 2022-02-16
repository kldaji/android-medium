package com.kldaji.android_medium

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadWebViewUrl()
        setButtonSubClickListener()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebViewUrl() {
        with(binding.wvMain) {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {}
            loadUrl("https://canvas.skku.edu/")
        }
    }

    private fun setButtonSubClickListener() {
        binding.btnMainSub.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }
}
