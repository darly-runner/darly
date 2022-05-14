package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewCreateMatchBinding
import com.ssafy.darly.model.CreateMatchReq
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewCreateMatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCreateMatchBinding
    var crewId: Long=0
    var matchTitle: String=""
    var matchDistance: String=""

//    var matchMaxPerson: Short,
//    var matchGoalDistance: Float

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_create_match)
        binding.lifecycleOwner = this

        crewId = intent.getLongExtra("crewId", 0)

        binding.createMatchName.doAfterTextChanged {
            matchTitle = it.toString()
        }
        binding.createMatchDistance.doAfterTextChanged {
            matchDistance = it.toString()
        }

        binding.createMatchButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val CrewMathList = CreateMatchReq(matchTitle = matchTitle, matchMaxPerson = 6, matchGoalDistance = matchDistance.toFloat())
                val response = DarlyService.getDarlyService().createMatch(crewId = crewId, CrewMathList)
                Log.d("create MATCH", "${response}")
            }
        }
    }
}
