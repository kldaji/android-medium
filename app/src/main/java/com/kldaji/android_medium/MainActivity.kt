package com.kldaji.android_medium

import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kldaji.android_medium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var myAsyncTask: MyAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnMain.setOnClickListener {
            if (myAsyncTask != null) {
                finish()
            }
            myAsyncTask = MyAsyncTask(this)
            myAsyncTask!!.execute()
        }
    }

    inner class MyAsyncTask(private val context: Context) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            val bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
            Thread.sleep(10000)
        }
    }
}
