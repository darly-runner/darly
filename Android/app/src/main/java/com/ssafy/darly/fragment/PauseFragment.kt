package com.ssafy.darly.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentPauseBinding
import com.ssafy.darly.viewmodel.RunningViewModel

class PauseFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentPauseBinding
    private lateinit var model: RunningViewModel

    private lateinit var map: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pause, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            model = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory()).get(RunningViewModel::class.java)
            binding.viewModel = model
        }

        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        locationInit()
        addLocationListener()

        val initLocation = LatLng(37.5666805, 126.9784147)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLocation, 15f))
        // 현재 내위치 표시
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }

        model.dist.observe(this, Observer {
            val polylineOptions = PolylineOptions()
            polylineOptions.color(resources.getColor(R.color.main))

            for (i in model.locationList.value ?: ArrayList()) {
                val marker = LatLng(i.latitude, i.longitude)
                polylineOptions.points.add(marker)
            }
            map.addPolyline(polylineOptions)
        })

//        LocationHelper().startListeningUserLocation(requireContext(), object : LocationHelper.MyLocationListener {
//            override fun onLocationChanged(location: Location) {
//                val latLng = LatLng(location.latitude, location.longitude)
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
//            }
//        })
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
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}

