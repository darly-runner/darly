package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.FragmentMultiBinding

class MultiFragment : Fragment() {
    private lateinit var binding: FragmentMultiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMultiBinding.inflate(inflater, container, false)

        binding.startText.setOnClickListener {
            val intent = Intent(context, RunningActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}