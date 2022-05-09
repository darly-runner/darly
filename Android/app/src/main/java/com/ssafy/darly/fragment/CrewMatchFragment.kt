package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentCrewDataBinding
import com.ssafy.darly.databinding.FragmentCrewMatchBinding

class CrewMatchFragment : Fragment() {
    private lateinit var binding: FragmentCrewMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_match, container, false)
        activity?.let {
            binding.lifecycleOwner = this
        }
        return binding.root
    }

//    fun newInstant() : CrewMatchFragment
//    {
//        val args = Bundle()
//        val frag = CrewMatchFragment()
//        frag.arguments = args
//        return frag
//    }

}