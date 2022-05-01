package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.ssafy.darly.R

class CreateCrewActivity : AppCompatActivity() {
    var crewName: String = ""
    var crewDesc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_crew)

        findViewById<EditText>(R.id.createCrewName).doAfterTextChanged {
            crewName = it.toString()
        }
        findViewById<EditText>(R.id.createCrewDesc).doAfterTextChanged {
            crewDesc = it.toString()
        }
//        findViewById<EditText>(R.id.createCrewButton).setOnClickListener(
//
//        )
    }
}