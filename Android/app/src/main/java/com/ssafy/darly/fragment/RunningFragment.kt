package com.ssafy.darly.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.dialog.TargetDialog
import com.ssafy.darly.viewmodel.RunningViewModel

class RunningFragment : Fragment() ,
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_running,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

//        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        // 목표 설정 버튼
        binding.targetButton.setOnClickListener {
            val targetDialog = TargetDialog(this.requireContext())

            targetDialog.show()
            targetDialog.setOnClickedListener(object : TargetDialog.ButtonClickListener{
                override fun onClicked(target : String) {
                    model.target.value = target
                }
            })
        }

        binding.startButton.setOnClickListener {
            val intent = Intent(this.requireContext(),RunningActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // 현재 내위치 표시
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }
    }
}