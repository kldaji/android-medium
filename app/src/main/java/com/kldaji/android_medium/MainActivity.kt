package com.kldaji.android_medium

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.kldaji.android_medium.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var gaugeUpTask: TimerTask? = null
    private var gaugeDownTask: TimerTask? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val timer = Timer()
        var count = 0
        binding.btnMain.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    gaugeUpTask = object : TimerTask() {
                        override fun run() {
                            if (count < 100) {
                                count++
                            }
                            println(count)
                            setGaugeProgress(count)
                        }
                    }
                    timer.schedule(gaugeUpTask, 0, 20)
                    true
                }
                MotionEvent.ACTION_MOVE -> false
                MotionEvent.ACTION_UP -> {
                    gaugeUpTask?.cancel()
                    gaugeDownTask = object : TimerTask() {
                        override fun run() {
                            if (count == 0) {
                                this.cancel()
                            }
                            count--
                            println(count)
                            setGaugeProgress(count)
                        }
                    }
                    timer.schedule(gaugeDownTask, 0, 60)
                    true
                }
                else -> false
            }
        }
    }

    fun setGaugeProgress(_progress: Int) {
        if (_progress > 100) return
        binding.progressBarMain.progress = _progress
    }
}
