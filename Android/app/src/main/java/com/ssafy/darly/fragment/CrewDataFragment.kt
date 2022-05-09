package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentCrewDataBinding
import com.ssafy.darly.viewmodel.CrewViewModel

class CrewDataFragment : Fragment() {
    private lateinit var binding: FragmentCrewDataBinding
    private val model: CrewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_data, container, false)
        activity?.let {
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}