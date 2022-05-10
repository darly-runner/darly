package com.ssafy.darly.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentPauseBinding
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
            model = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory()).get(RunningViewModel::class.java)
            binding.viewModel = model
        }

        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // 현재 내위치 표시
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        model.dist.observe(this, Observer {
            val polylineOptions = PolylineOptions()
            polylineOptions.color(resources.getColor(R.color.main))

            for(i in model.locationList.value ?: ArrayList()){
                val marker = LatLng(i.latitude, i.longitude)
                polylineOptions.points.add(marker)
            }
            map.addPolyline(polylineOptions)
        })
    }
}

