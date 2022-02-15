package com.kldaji.android_medium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainViewPager2Adapter = MainViewPager2Adapter()
        val pageOffsetPx = resources.getDimensionPixelOffset(R.dimen.page_preview_offset)
        val marginBetweenPagesPx = resources.getDimensionPixelOffset(R.dimen.margin_between_pages)
        with(binding.vpMain) {
            adapter = mainViewPager2Adapter
            offscreenPageLimit = 2
            setPageTransformer { page, position ->
                page.translationX = position * -(2 * pageOffsetPx + marginBetweenPagesPx)
            }
        }
    }
}
