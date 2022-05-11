package com.ssafy.darly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentMyChallengeBinding
import com.ssafy.darly.viewmodel.MyChallengeViewModel

class MyChallengeFragment : Fragment() {
    private lateinit var binding: FragmentMyChallengeBinding
    private val model: MyChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_challenge, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Coroutine
    }
}