package com.ssafy.darly.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.dialog.CountDownDialog
import com.ssafy.darly.dialog.MatchLottieDialog
import com.ssafy.darly.dialog.TargetDialog
import com.ssafy.darly.viewmodel.RunningViewModel


class RunningFragment : Fragment(),
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_running, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit()
        addLocationListener()

        // ?????? ?????? ??????
        binding.targetButton.setOnClickListener {
            val targetDialog = TargetDialog(this.requireContext())

            targetDialog.show()
            targetDialog.setOnClickedListener(object : TargetDialog.ButtonClickListener {
                override fun onClicked(target: String) {
                    model.target.value = target
                }
            })
        }

        binding.startButton.setOnClickListener {
            if (binding.runningTab.selectedTabPosition == 0) {
                val intent = Intent(this.requireContext(), RunningActivity::class.java)
                intent.putExtra("target",model.target.value)
                val dialog = CountDownDialog(3,model.target.value ?: "5.0", "running")
                dialog.show(parentFragmentManager,"solo")
                //startActivity(intent)
            } else if (binding.runningTab.selectedTabPosition == 1) {
                val dialog = MatchLottieDialog(model.target.value ?: "5.0")
                dialog.show(parentFragmentManager, "match")
            }
        }

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.isBuildingsEnabled = true
        map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.map_style
            )
        )

        val initLocation = LatLng(37.5666805, 126.9784147)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLocation, 16f))

        // ?????? ????????? ??????
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
        locationCallback = MyLocationCallBack(fusedLocationProviderClient)
        locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_NO_POWER
            maxWaitTime = 10000
        }
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback, Looper.getMainLooper()
        )
    }

    inner class MyLocationCallBack(private val fusedLocationProviderClient: FusedLocationProviderClient) :
        LocationCallback() {
        override fun onLocationResult(locationRequest: LocationResult) {
            super.onLocationResult(locationRequest)
            val location = locationRequest.lastLocation

            location.run {
                val latLng = LatLng(latitude, longitude)
                if (::map.isInitialized) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                }
            }
        }
    }
}