package com.ssafy.darly.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewCreateFeedBinding
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.File

class CrewCreateFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCreateFeedBinding
    private var imageURI= Uri.EMPTY
    var crewId: Long = 0
    private var feedDesc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_create_feed)
        binding.lifecycleOwner = this

        val selectedImageView = findViewById<ImageView>(R.id.selectedFeedImg)
        val glide = Glide.with(this)

        val imgPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                imageURI = it.data?.data ?: Uri.EMPTY
                if (imageURI != Uri.EMPTY) {
                    glide.load(imageURI).into(selectedImageView)
                    Log.d("select img", "" + imageURI)
                }
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

        crewId = intent.getLongExtra("crewId", 0)
        binding.createFeedButton.setOnClickListener {
            val file = getImgFile(imageURI)
            val requestFile =
                RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
            val feedImgBody =
                MultipartBody.Part.createFormData("feedImage", file!!.name, requestFile)
            val feedContent = RequestBody.create(MediaType.parse("text/plain"), feedDesc)
            val feedTitle = RequestBody.create(MediaType.parse("text/plain"), "title")

            val textHashMap = hashMapOf<String, RequestBody>()

            textHashMap["feedContent"] = feedContent
            textHashMap["feedTitle"] = feedTitle

            CoroutineScope(Dispatchers.IO).launch {
                val response = DarlyService.getDarlyService()
                    .createFeed(crewId = crewId, feedImage = feedImgBody, data = textHashMap)
                Log.d("Create Feeddd", "${response}")
            }
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