package com.ssafy.darly.activity

import android.app.Activity
import android.os.Bundle
import com.ssafy.darly.databinding.ActivityRunningBinding

class RunningActivity : Activity() {

    private lateinit var binding: ActivityRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}