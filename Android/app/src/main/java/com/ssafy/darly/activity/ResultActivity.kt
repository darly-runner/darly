package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityResultBinding
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ResultActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityResultBinding
    private val model: RunningViewModel by viewModels()

    lateinit var record: RecordRequest
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        record = intent.getSerializableExtra("record") as RecordRequest
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
                val bitmap = it
                val filename = "${System.currentTimeMillis()}.jpg"

                // Output stream
                var fos: OutputStream? = null

                // For devices running android >= Q
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // getting the contentResolver
                    this.contentResolver?.also { resolver ->

                        // Content resolver will process the contentvalues
                        val contentValues = ContentValues().apply {

                            // putting file information in content values
                            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                        }

                        // Inserting the contentValues to
                        // contentResolver and getting the Uri
                        val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                        // Opening an outputstream with the Uri that we got
                        fos = imageUri?.let { resolver.openOutputStream(it) }
                    }
                } else {
                    // These for devices running on android < Q
                    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val image = File(imagesDir, filename)
                    fos = FileOutputStream(image)
                }

                fos?.use {
                    // Finally writing the bitmap to the output stream that we opened
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    Toast.makeText(this, "Captured View and saved to Gallery", Toast.LENGTH_SHORT).show()
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                val recordImage = if (mapBitmap != null) {
                    var file = convertBitmapToFile(mapBitmap!!)
                    val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
                    MultipartBody.Part.createFormData("profile", file.getName(), requestBody)
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
                        sections.add(MultipartBody.Part.createFormData("sections[$i][km]", it.km.toString()))
                        sections.add(MultipartBody.Part.createFormData("sections[$i][pace]", it.pace.toString()))
                        sections.add(MultipartBody.Part.createFormData("sections[$i][calories]", it.calories.toString()))
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

                DarlyService.getDarlyService().postRecord(
                    data = textHashMap,
                    recordImage = recordImage,
                    coordinateLatitudes = coordinateLatitudes,
                    coordinateLongitudes = coordinateLongitudes,
                    sections = sections
                )
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }


        }


    }

    fun convertBitmapToFile(bitmap: Bitmap): File {
        val newFile = File(applicationContext.filesDir, "picture")
        val out = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        return newFile
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}