package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentActBinding
import com.ssafy.darly.viewmodel.ActViewModel

class ActFragment : Fragment() {
    private lateinit var binding: FragmentActBinding
    private val model: ActViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_act,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        return binding.root
    }
}