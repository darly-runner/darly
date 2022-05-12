package com.ssafy.darly.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewCreateFeedBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.File

class CrewCreateFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCreateFeedBinding
    private lateinit var imageURI: Uri
    var feedDesc: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_create_feed)
        binding.lifecycleOwner = this

        val selectedImageView = findViewById<ImageView>(R.id.selectedFeedImg)
        val glide = Glide.with(this)

        val imgPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imageURI = it.data?.data!!
            glide.load(imageURI).into(selectedImageView)
        }

        binding.uploadFeedImg.setOnClickListener {
            imgPickerLauncher.launch(
                Intent(Intent.ACTION_PICK).apply {
                    this.type = MediaStore.Images.Media.CONTENT_TYPE
                }
            )
        }

        binding.createFeedDesc.doAfterTextChanged {
            feedDesc = it.toString()
        }

        binding.createFeedButton.setOnClickListener {
            val file = getImgFile(imageURI)
            val requestFile = RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
            val crewImgBody = MultipartBody.Part.createFormData("crewImage", file!!.name, requestFile)
            val crewDesc = RequestBody.create(MediaType.parse("text/plain"), feedDesc)
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