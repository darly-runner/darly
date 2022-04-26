package com.ssafy.darly.fragment

<<<<<<< HEAD
import android.os.Bundle
=======
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
<<<<<<< HEAD
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.viewmodel.RunningViewModel

class RunningFragment : Fragment() {
    private lateinit var binding: FragmentRunningBinding
    private val model: RunningViewModel by viewModels()

=======
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentRunningBinding
import com.ssafy.darly.util.LocationHelper
import com.ssafy.darly.viewmodel.RunningViewModel

class RunningFragment : Fragment() , OnMapReadyCallback {
    private lateinit var binding: FragmentRunningBinding
    private val model: RunningViewModel by viewModels()

    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null

>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_running,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

<<<<<<< HEAD
        return binding.root
    }
=======
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        context?.let {
            LocationHelper().startListeningUserLocation(it, object : LocationHelper.MyLocationListener {
                override fun onLocationChanged(location: Location) {
                    // Here you got user location :)
                    Log.d("Location","" + location.latitude + "," + location.longitude)
                    val marker = LatLng(location.latitude, location.longitude)

                    map.addMarker(MarkerOptions().position(marker).title("마커 제목"))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,18f))
                }
            })
        }
    }
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
}