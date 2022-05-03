package com.ssafy.darly.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.FragmentPauseBinding
import com.ssafy.darly.util.LocationHelper
import com.ssafy.darly.viewmodel.MainViewModel
import com.ssafy.darly.viewmodel.RunningViewModel

class PauseFragment : Fragment() , OnMapReadyCallback {
    private lateinit var binding: FragmentPauseBinding
    private lateinit var model : RunningViewModel

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pause,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            model = activity?.let { ViewModelProvider(it, ViewModelProvider.NewInstanceFactory()).get(RunningViewModel::class.java) }!!
            binding.viewModel = model
        }

        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val polylineOptions = PolylineOptions()

        // 현재 내위치 표시
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

//        LocationHelper().startListeningUserLocation(this.requireContext(), object : LocationHelper.MyLocationListener {
//            override fun onLocationChanged(location: Location) {
//                // Here you got user location :)
//                Log.d("Location","" + location.latitude + "," + location.longitude)
//                val marker = LatLng(location.latitude, location.longitude)
//
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,18f))
//                polylineOptions.points.add(marker)
//                map.addPolyline(polylineOptions)
//            }
//        })
    }
}

