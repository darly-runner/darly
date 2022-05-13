package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewCreateMatchBinding

class CrewCreateMatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCreateMatchBinding
    var crewId: Long=0
    var matchTitle: String=""
    var matchDistance: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_create_match)
        binding.lifecycleOwner = this

        binding.createMatchName.doAfterTextChanged {
            matchTitle = it.toString()
        }
        binding.createMatchDistance.doAfterTextChanged {
            matchDistance = it.toString()
        }

        binding.createMatchButton.setOnClickListener {
            
        }
    }
}
