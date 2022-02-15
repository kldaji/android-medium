package com.kldaji.android_medium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainViewPager2Adapter = MainViewPager2Adapter()
        binding.vpMain.adapter = mainViewPager2Adapter
        TabLayoutMediator(binding.tlMain, binding.vpMain) { _, _ -> }.attach()
    }
}
