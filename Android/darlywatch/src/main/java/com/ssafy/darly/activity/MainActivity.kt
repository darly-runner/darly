package com.ssafy.darly.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = GlobalApplication.prefs.getString("token","noToken")
        Log.d("MainActivity", "$token")

        binding.startText.setOnClickListener {
            val intent = Intent(this, RunningActivity::class.java)
            startActivity(intent)
        }
    }
}