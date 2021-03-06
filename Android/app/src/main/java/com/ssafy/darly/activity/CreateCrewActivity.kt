package com.ssafy.darly.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.LocationListAdapter
import com.ssafy.darly.databinding.ActivityCreateCrewBinding
import com.ssafy.darly.fragment.SearchLocationFragment
//import com.ssafy.darly.fragment.SearchLocationFragment
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

class CreateCrewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCrewBinding
    var crewName: String = ""
    var crewDesc: String = ""
    var crewSearchAddress: String = ""
    var crewAddress: Long = 1
    var crewJoin: String = "Lock"

    private var imageURI= Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_crew)
        binding.lifecycleOwner = this

        // FEAT: upload image
        val selectedImageView = findViewById<ImageView>(R.id.selectedCrewImg)
        val glide = Glide.with(this)
        lateinit var dialog: SearchLocationFragment

        val imgPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                imageURI = it.data?.data ?: Uri.EMPTY
                if (imageURI != Uri.EMPTY) {
                    glide.load(imageURI).into(selectedImageView)
                    Log.d("select img", "" + imageURI)
                }
            }

        binding.uploadCrewImg.setOnClickListener {
            imgPickerLauncher.launch(
                Intent(Intent.ACTION_PICK).apply {
                    this.type = MediaStore.Images.Media.CONTENT_TYPE
                }
            )
        }

        binding.createCrewName.doAfterTextChanged {
            crewName = it.toString()
        }

        binding.createCrewDesc.doAfterTextChanged {
            crewDesc = it.toString()
        }

        binding.createCrewLocation.setOnClickListener {
            dialog = SearchLocationFragment()
            dialog.show(supportFragmentManager, "SearchLocationFragment")

            dialog.setOnClickedListener(object : SearchLocationFragment.ButtonClickListener {
                override fun onClicked(addressName: String, addressId: Long) {
                    crewAddress = addressId
                    crewSearchAddress = addressName

                    if (crewSearchAddress != "") {
                        binding.selectedLocation.setText(crewSearchAddress)
                    }
                }
            })
        }

        binding.createCrewButton.setOnClickListener {
            val file = getImgFile(imageURI)
            val requestFile =
                RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
            val crewImgBody =
                MultipartBody.Part.createFormData("crewImage", file!!.name, requestFile)
            val crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
            val crewAddressBody =
                RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
            val crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
            val crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)

            val textHashMap = hashMapOf<String, RequestBody>()

            textHashMap["crewName"] = crewNameBody
            textHashMap["crewAddress"] = crewAddressBody
            textHashMap["crewDesc"] = crewDescBody
            textHashMap["crewJoin"] = crewJoinBody

            CoroutineScope(Dispatchers.IO).launch {
                val response = DarlyService.getDarlyService()
                    .createCrew(crewImage = crewImgBody, data = textHashMap)
                Log.d("Create Crew", "${response}")
                finish()
//                val intent = Intent(this@CreateCrewActivity, )

            }
        }

        binding.crewJoin.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.crewJoinDirect -> crewJoin = "Free"
                R.id.crewJoinApproval -> crewJoin = "Lock"

            }
            Log.d("Join", crewJoin)
        }

        binding.back.setOnClickListener {
            finish()
        }
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