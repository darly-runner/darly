package com.ssafy.darly.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCreateCrewBinding
import com.ssafy.darly.fragment.CrewImageUploadFragment
import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.model.CreateCrewReq
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class CreateCrewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCrewBinding
    var crewName: String = ""
    var crewDesc: String = ""
    var crewAddress: Long = 1
    var crewJoin: String = "Lock"

    var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_crew)
        binding.lifecycleOwner = this
//        setContentView(R.layout.activity_create_crew)

        val crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
        val crewAddressBody = RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
        val crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
        val crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)

        val textHashMap = hashMapOf<String, RequestBody>()

        textHashMap["crewName"] = crewNameBody
        textHashMap["crewAddress"] = crewAddressBody
        textHashMap["crewDesc"] = crewDescBody
        textHashMap["crewJoin"] = crewJoinBody

        findViewById<EditText>(R.id.createCrewName).doAfterTextChanged {
            crewName = it.toString()
        }
        findViewById<EditText>(R.id.createCrewDesc).doAfterTextChanged {
            crewDesc = it.toString()
        }
        findViewById<TextView>(R.id.createCrewButton).setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                val response = DarlyService.getDarlyService().createCrew(crewImage = null, data = textHashMap)
                Log.d("Create Crew", "${response}")
            }
        }

        // FEAT: upload image
        val selectedImageView = findViewById<ImageView>(R.id.selectedCrewImg)
        val glide = Glide.with(this)

        val imgPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imageURI = it.data!!.data
            glide.load(imageURI).into(selectedImageView)
            Log.d("select img", ""+imageURI)
        }

        binding.uploadCrewImg.setOnClickListener {
            imgPickerLauncher.launch(
                Intent(Intent.ACTION_PICK).apply {
                    this.type = MediaStore.Images.Media.CONTENT_TYPE
                }
            )
        }
    }
}