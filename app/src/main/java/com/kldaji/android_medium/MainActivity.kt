package com.kldaji.android_medium

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kldaji.android_medium.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMain.setOnClickListener {
            if (job != null) finish()
            job = GlobalScope.launch(Dispatchers.IO) {
                val context: Context = this@MainActivity
                delay(100000)
            }
        }
    }
}
