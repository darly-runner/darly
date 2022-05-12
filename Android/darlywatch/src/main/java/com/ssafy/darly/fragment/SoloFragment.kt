package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.FragmentSoloBinding

class SoloFragment : Fragment() {
    private lateinit var binding: FragmentSoloBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSoloBinding.inflate(inflater, container, false)

        binding.startText.setOnClickListener {
            val intent = Intent(context, RunningActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거

            startActivity(intent)
        }
        return binding.root
    }
}