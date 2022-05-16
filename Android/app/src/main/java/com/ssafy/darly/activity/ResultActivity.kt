package com.ssafy.darly.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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
import com.ssafy.darly.databinding.ActivityResultBinding
import com.ssafy.darly.fragment.RunningFragment
import com.ssafy.darly.model.record.RecordRequest
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.LocationHelper
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityResultBinding
    private val model: RunningViewModel by viewModels()

    lateinit var record: RecordRequest
    private lateinit var map: GoogleMap

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: ResultActivity.MyLocationCallBack
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        binding.lifecycleOwner = this
        binding.viewModel = model

        init()

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun init() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분")
        val formatted = current.format(formatter)

        record = intent.getSerializableExtra("record") as RecordRequest
        binding.dateText.text = "$formatted"
        model.dist.value = record.recordDistance
        model.pace.value = model.timeToStr(record.recordPace)
        model.calorie.value = "${record.recordCalories} kcal"
        model.speed.value = record.recordSpeed
        model.time.value = model.timeToStr(record.recordTime)

        // 구간별, 추후에 차트 추가
        var cnt = 1
        for (i in record.sections) {
            val textView = TextView(this)
            textView.text = "${cnt++} / ${i.km} km , ${i.pace}, ${i.calories}"
            binding.sectionView.addView(textView)
        }

        // 기록 저장
        binding.writeButton.setOnClickListener {
            if (binding.titleText.text.isNotEmpty())
                record.recordTitle = binding.titleText.text.toString()

            var mapBitmap: Bitmap? = null
            map.snapshot {
                mapBitmap = it
                CoroutineScope(Dispatchers.Main).launch {
                    val recordImage = if (mapBitmap != null) {
                        var file = convertBitmapToFile(mapBitmap!!)
                        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
                        MultipartBody.Part.createFormData("recordImage", file.getName(), requestBody)
                    } else null

                    val coordinateLatitudes = mutableListOf<RequestBody>()
                    for (cl in record.coordinateLatitudes)
                        coordinateLatitudes.add(RequestBody.create(MediaType.parse("text/plain"), cl))
                    val coordinateLongitudes = mutableListOf<RequestBody>()
                    for (cl in record.coordinateLongitudes)
                        coordinateLongitudes.add(RequestBody.create(MediaType.parse("text/plain"), cl))
                    val sections = mutableListOf<MultipartBody.Part>()
                    for (i in record.sections.indices) {
                        record.sections[i].let {
                            sections.add(MultipartBody.Part.createFormData("sections[$i].km", it.km.toString()))
                            sections.add(MultipartBody.Part.createFormData("sections[$i].pace", it.pace.toString()))
                            sections.add(MultipartBody.Part.createFormData("sections[$i].calories", it.calories.toString()))
                        }
                    }
                    val matchId = RequestBody.create(MediaType.parse("text/plain"), record.matchId?.toString() ?: "")
                    val recordDistance = RequestBody.create(MediaType.parse("text/plain"), record.recordDistance.toString())
                    val recordPace = RequestBody.create(MediaType.parse("text/plain"), record.recordPace.toString())
                    val recordCalories = RequestBody.create(MediaType.parse("text/plain"), record.recordCalories.toString())
                    val recordHeart = RequestBody.create(MediaType.parse("text/plain"), record.recordHeart.toString())
                    val recordSpeed = RequestBody.create(MediaType.parse("text/plain"), record.recordSpeed.toString())
                    val recordTime = RequestBody.create(MediaType.parse("text/plain"), record.recordTime.toString())
                    val recordRank = RequestBody.create(MediaType.parse("text/plain"), record.recordRank?.toString() ?: "")
                    val recordTitle = RequestBody.create(MediaType.parse("text/plain"), record.recordTitle ?: "")

                    val textHashMap = hashMapOf<String, RequestBody>()
                    if (record.matchId != null) textHashMap["matchId"] = matchId
                    textHashMap["recordDistance"] = recordDistance
                    textHashMap["recordPace"] = recordPace
                    textHashMap["recordCalories"] = recordCalories
                    textHashMap["recordHeart"] = recordHeart
                    textHashMap["recordSpeed"] = recordSpeed
                    textHashMap["recordTime"] = recordTime
                    if (record.recordRank != null) textHashMap["recordRank"] = recordRank
                    if (record.recordTitle != null) textHashMap["recordTitle"] = recordTitle

                    Log.d("response", "${recordImage}")
                    var response = DarlyService.getDarlyService().postRecord(
                        data = textHashMap,
                        recordImage = recordImage,
                        coordinateLatitudes = coordinateLatitudes,
                        coordinateLongitudes = coordinateLongitudes,
                        sections = sections
                    )
                    Log.d("response", "${response.body()}")
                    Log.d("response", "${recordImage}")

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun convertBitmapToFile(bitmap: Bitmap): File {
        val filename = "${System.currentTimeMillis()}.jpg"
        val newFile = File(applicationContext.filesDir, filename)
        val out = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        return newFile
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        locationInit()
        addLocationListener()

        // 현재 내위치 표시
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        // 이동경로 표시
        Toast.makeText(this, "${record.coordinateLatitudes.size}",Toast.LENGTH_LONG).show()
        val polylineOptions = PolylineOptions()
        polylineOptions.color(resources.getColor(R.color.main))

        for(i in 0 until record.coordinateLatitudes.size){
            val marker = LatLng(record.coordinateLatitudes[i].toDouble(), record.coordinateLongitudes[i].toDouble())
            polylineOptions.points.add(marker)
        }
        map.addPolyline(polylineOptions)
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
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
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                }
            }
        }
    }
}