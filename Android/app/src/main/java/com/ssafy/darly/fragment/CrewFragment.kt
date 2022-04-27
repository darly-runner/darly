package com.ssafy.darly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentActBinding
import com.ssafy.darly.databinding.FragmentCrewBinding
import com.ssafy.darly.model.MyCrewDetails
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.ActViewModel
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFragment : Fragment() {
    private lateinit var binding: FragmentCrewBinding
    private val model: CrewViewModel by viewModels()
    lateinit var myCrewItemList: List<MyCrewDetails>

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
            myCrewItemList = response.body()?.crew ?: listOf() //?.왼쪽 널이면 실행 안함 뒤엥 ㅣㅆ는건 엘비스
            Log.d("Crew List", "${response}")
            Log.d("Crew List 2", "${response.body()}")
        }
    }
}

