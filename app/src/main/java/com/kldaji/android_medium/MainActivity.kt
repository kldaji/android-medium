package com.kldaji.android_medium

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addPinView()
        buttonClickListener()
    }

    private fun addPinView() {
        val pinView = PinView(this)
        val pinBitmap = ResourcesCompat.getDrawable(resources, R.drawable.ic_pin, null)?.toBitmap()
        pinBitmap?.let {
            pinView.pinBitmap = it
        }
        binding.root.addView(pinView)
    }

    private fun buttonClickListener() {
        binding.btnMain.setOnClickListener {
            val degree = (3000..10000).random().toFloat()
            val duration = (degree * 2).toLong()
            binding.rouletteViewMain.rotateRouletteView(degree, duration)
        }
    }
}
