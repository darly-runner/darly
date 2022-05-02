package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.model.CreateCrewReq
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class CreateCrewActivity : AppCompatActivity() {
    var crewName: String = ""
    var crewDesc: String = ""
    var crewAddress: Long = 1
    var crewJoin: String = "Lock"

//    var crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
//    var crewAddressBody = RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
//    var crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
//    var crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)
//
//    var textHashMap = hashMapOf<String, RequestBody>()

//    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_crew)

        val crewNameBody = RequestBody.create(MediaType.parse("text/plain"), crewName)
        val crewAddressBody = RequestBody.create(MediaType.parse("text/plain"), crewAddress.toString())
        val crewDescBody = RequestBody.create(MediaType.parse("text/plain"), crewDesc)
        val crewJoinBody = RequestBody.create(MediaType.parse("text/plain"), crewJoin)

        val textHashMap = hashMapOf<String, RequestBody>()

        textHashMap["crewName"] = crewNameBody
        textHashMap["crewAddress"] = crewAddressBody
        textHashMap["crewDesc"] = crewDescBody
        textHashMap["crewJoin"] = crewJoinBody


//        DataBindingUtil.setContentView<EditText>(this, R.id.createCrewName)
//
//        binding = DataBindingUtil.setContentView(this, R.id.createCrewName)

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
    }
}