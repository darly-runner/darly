package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.viewmodel.RunningViewModel

class RunningFragment : Fragment() {
    private lateinit var binding: FragmentRunningBinding
    private lateinit var model: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        activity.let {
            model = ViewModelProvider(it!!, ViewModelProvider.NewInstanceFactory()).get(RunningViewModel::class.java)
        }
        binding.viewModel = model
        binding.lifecycleOwner = this

        return binding.root
    }
}