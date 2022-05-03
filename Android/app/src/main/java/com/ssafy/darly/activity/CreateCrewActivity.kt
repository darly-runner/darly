package com.ssafy.darly.activity

import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateCrewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCrewBinding
    var crewName: String = ""
    var crewDesc: String = ""
    var crewAddress: Long = 1
    var crewJoin: String = "Lock"

    private lateinit var imageURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_crew)
        binding.lifecycleOwner = this

        // FEAT: upload image
        val selectedImageView = findViewById<ImageView>(R.id.selectedCrewImg)
        val glide = Glide.with(this)

        val imgPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imageURI = it.data?.data!!
            glide.load(imageURI).into(selectedImageView)
            Log.d("select img", ""+imageURI)

//            val file = getImgFile(imageURI)
        }

        binding.uploadCrewImg.setOnClickListener {
            imgPickerLauncher.launch(
                Intent(Intent.ACTION_PICK).apply {
                    this.type = MediaStore.Images.Media.CONTENT_TYPE
                }
            )
        }

//        val = getImgFile(imageURI)file
//        val requestFile = RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
//        val crewImgBody = MultipartBody.Part.createFormData("crewImage", file!!.name, requestFile)
//        val crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
//        val crewAddressBody = RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
//        val crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
//        val crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)
//
//        val textHashMap = hashMapOf<String, RequestBody>()
//
//        textHashMap["crewName"] = crewNameBody
//        textHashMap["crewAddress"] = crewAddressBody
//        textHashMap["crewDesc"] = crewDescBody
//        textHashMap["crewJoin"] = crewJoinBody

        findViewById<EditText>(R.id.createCrewName).doAfterTextChanged {
            crewName = it.toString()
        }
        findViewById<EditText>(R.id.createCrewDesc).doAfterTextChanged {
            crewDesc = it.toString()
        }
        findViewById<TextView>(R.id.createCrewButton).setOnClickListener() {
            val file = getImgFile(imageURI)
            val requestFile = RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
            val crewImgBody = MultipartBody.Part.createFormData("crewImage", file!!.name, requestFile)
            val crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
            val crewAddressBody = RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
            val crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
            val crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)

            val textHashMap = hashMapOf<String, RequestBody>()

            textHashMap["crewName"] = crewNameBody
            textHashMap["crewAddress"] = crewAddressBody
            textHashMap["crewDesc"] = crewDescBody
            textHashMap["crewJoin"] = crewJoinBody

            CoroutineScope(Dispatchers.IO).launch {
                val response = DarlyService.getDarlyService().createCrew(crewImage = crewImgBody, data = textHashMap)
                Log.d("Create Crew", "${response}")
            }
        }

//        // FEAT: upload image
//        val selectedImageView = findViewById<ImageView>(R.id.selectedCrewImg)
//        val glide = Glide.with(this)
//
//        val imgPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            imageURI = it.data!!.data
//            glide.load(imageURI).into(selectedImageView)
//            Log.d("select img", ""+imageURI)
//        }
//
//        binding.uploadCrewImg.setOnClickListener {
//            imgPickerLauncher.launch(
//                Intent(Intent.ACTION_PICK).apply {
//                    this.type = MediaStore.Images.Media.CONTENT_TYPE
//                }
//            )
//        }
    }

    private fun getImgFile(uri: Uri): File? {
        var uri: Uri? = uri
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        var cursor: Cursor? = getContentResolver().query(
            uri!!,
            projection,
            null,
            null,
            MediaStore.Images.Media.DATE_MODIFIED + " desc"
        )
        if (cursor == null || cursor.getColumnCount() < 1) {
            return null
        }
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path: String = cursor.getString(column_index)
        if (cursor != null) {
            cursor.close()
            cursor = null
        }
        return File(path)
    }
}