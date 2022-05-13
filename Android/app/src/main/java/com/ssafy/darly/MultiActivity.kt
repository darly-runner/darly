package com.ssafy.darly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.databinding.ActivityMultiBinding
import com.ssafy.darly.viewmodel.RunningViewModel

class MultiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultiBinding
    private val model : RunningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_multi)
        binding.lifecycleOwner = this
        binding.viewModel = model
    }
}