package com.ssafy.darly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentActBinding
import com.ssafy.darly.databinding.FragmentCrewBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.ActViewModel
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFragment : Fragment() {
    private lateinit var binding: FragmentCrewBinding
    private val model: CrewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = DarlyService.getDarlyService().myCrewList()
            Log.d("Crew List", "${response}")
        }
    }
}