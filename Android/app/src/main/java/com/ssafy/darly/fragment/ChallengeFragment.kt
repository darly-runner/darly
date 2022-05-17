package com.ssafy.darly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.challenge.ChallengesListAdapter
import com.ssafy.darly.databinding.FragmentChallengeBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.ChallengeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeFragment : Fragment() {
    private lateinit var binding: FragmentChallengeBinding
    private val model: ChallengeViewModel by viewModels()
    lateinit var adapter: ChallengesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_challenge,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glide = Glide.with(this@ChallengeFragment)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getChallengesList()
            model.challengesList.value = response.body()?.eventList
            Log.d("chch CHAL", "${response.body()}")

            adapter = ChallengesListAdapter(
                model.challengesList.value!!,
                LayoutInflater.from(context),
                glide
            )
            binding.challengesList.adapter = adapter
            binding.challengesList.layoutManager = GridLayoutManager(context, 1)
        }
//        adapter = model.challengesList.value?.let {
//            ChallengesListAdapter(
//                it,
//                LayoutInflater.from(context),
//                glide
//            )
//        }!!
//        binding.challengesList.adapter = adapter
//        binding.challengesList.layoutManager = GridLayoutManager(context, 1)
    }
}