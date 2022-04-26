package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.viewmodel.RunningViewModel

class RunningFragment : Fragment() {
    private lateinit var binding: FragmentRunningBinding
    private val model: RunningViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_running,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        return binding.root
    }
}