package com.ssafy.darlywatch.activity

import android.app.Activity
import android.os.Bundle
import com.ssafy.darlywatch.databinding.ActivityRunningBinding

class RunningActivity : Activity() {

    private lateinit var binding: ActivityRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}