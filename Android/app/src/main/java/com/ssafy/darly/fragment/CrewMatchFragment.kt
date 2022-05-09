package com.ssafy.darly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.darly.R

class CrewMatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crew_match, container, false)
    }

    private fun newInstant() : CrewMatchFragment
    {
        val args = Bundle()
        val frag = CrewMatchFragment()
        frag.arguments = args
        return frag
    }

}