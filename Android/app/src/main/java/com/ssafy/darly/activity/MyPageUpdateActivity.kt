package com.ssafy.darly.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.mypage.MyPageListAdapter
import com.ssafy.darly.databinding.ActivityMyPageUpdateBinding
import com.ssafy.darly.model.address.Address
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.MypageUpdateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyPageUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageUpdateBinding
    private lateinit var myPageListAdapter: MyPageListAdapter
    private val model: MypageUpdateViewModel by viewModels()
    private var imageURI = Uri.EMPTY
    private var isNicknameOk = true
    private var originNickname = ""
    private val defaultImage =
        "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_update)
        binding.lifecycleOwner = this
        binding.viewModel = model
        myPageListAdapter = MyPageListAdapter()
        myPageListAdapter.setButtonClickListener(object : MyPageListAdapter.ButtonClickListener {
            override fun deleteAddressAtPosition(addressList: List<Address>) {
                model.userAddress.value = addressList
            }
        })

        binding.recyclerView.adapter = myPageListAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        model.userImage.value = intent.getStringExtra("userImage")
        originNickname = intent.getStringExtra("userNickname") ?: ""
        model.userNickname.value = intent.getStringExtra("userNickname")
        model.userMessage.value = intent.getStringExtra("userMessage")
        model.userAddress.value = intent.getSerializableExtra("userAddresses") as List<Address>

        binding.cancelBtn.setOnClickListener { finish() }
        binding.saveBtn.setOnClickListener {
            if (!isNicknameOk) {
                model.userNickname.value = originNickname
            } else if (model.userAddress.value?.size == 0) {
                Toast.makeText(this, "지역을 설정해야 합니다.", Toast.LENGTH_SHORT)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val userImage =
                        if (imageURI != Uri.EMPTY) {
                            val file = getImgFile(imageURI)
                            val requestFile =
                                RequestBody.create(MediaType.parse(contentResolver.getType(imageURI!!)), file)
                            MultipartBody.Part.createFormData("userImage", file!!.name, requestFile)
                        } else {
                            null
                        }
                    val userNickname = RequestBody.create(MediaType.parse("text/plain"), model.userNickname.value)
                    var userAddresses = mutableListOf<RequestBody>()
                    for (address in model.userAddress.value ?: listOf()) {
                        userAddresses.add(RequestBody.create(MediaType.parse("text/plain"), address.addressId.toString()))
                    }
                    val userMessage = RequestBody.create(MediaType.parse("text/plain"), model.userMessage.value)

                    val textHashMap = hashMapOf<String, RequestBody>()

                    textHashMap["userNickname"] = userNickname
                    textHashMap["userMessage"] = userMessage

                    if (userImage == null)
                        DarlyService.getDarlyService()
                            .updateUserProfileWithoutImage(data = textHashMap, userAddresses = userAddresses)
                    else DarlyService.getDarlyService()
                        .updateUserProfile(data = textHashMap, userImage = userImage, userAddresses = userAddresses)

                    finish()
                }
            }
        }

        binding.editNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userNickname.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.editMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userMessage.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        val imgPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                imageURI = it.data?.data ?: Uri.EMPTY
                if (imageURI != Uri.EMPTY) {
                    Log.d("response-imageUri", "${imageURI}")
                    Glide.with(this).load(imageURI).into(binding.circleImageView)
                }
            }

        binding.editImageBtn.setOnClickListener {
            imgPickerLauncher.launch(
                Intent(Intent.ACTION_PICK).apply {
                    this.type = MediaStore.Images.Media.CONTENT_TYPE
                }
            )
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