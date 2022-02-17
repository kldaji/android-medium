package com.kldaji.android_medium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        calendarAdapter = CalendarAdapter(this)

        binding.vpMainCalendar.adapter = calendarAdapter
        binding.vpMainCalendar.setCurrentItem(CalendarAdapter.START_POSITION, false)
    }
}
