package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.ssafy.darly.R
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCrewActivity : AppCompatActivity() {
    var crewName: String = ""
    var crewDesc: String = ""
    var crewAddress: Long = 0
    var crewJoin: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_crew)

        findViewById<EditText>(R.id.createCrewName).doAfterTextChanged {
            crewName = it.toString()
        }
        findViewById<EditText>(R.id.createCrewDesc).doAfterTextChanged {
            crewDesc = it.toString()
        }
        findViewById<EditText>(R.id.createCrewButton).setOnClickListener() {
            CoroutineScope(Dispatchers.Main).launch {
                val response = DarlyService.getDarlyService().createCrew(crewName = crewName, crewDesc = crewDesc, crewAddress = crewAddress, crewJoin = crewJoin)
//                model.myCrewList.value = response.body()?.crew ?: listOf()

                Log.d("Crew List", "${response}")
//                Log.d("Crew List 2", "${response.body()}")
            }
        }
    }
}