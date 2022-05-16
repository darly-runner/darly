package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewCreateMatchBinding
import com.ssafy.darly.model.CreateMatchReq
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class CrewCreateMatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCreateMatchBinding
    var crewId: Long = 0
    private var myUserId: Long=3
    private var matchTitle: String = ""
    private var matchDistance: String = ""
    private var matchId: Long = 0

    private val url = "http://3.36.61.107:8000/ws/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    @SuppressLint("CheckResult")
    fun runStomp() {
        stompClient.connect()
        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("ENTER", "ENTERED!!")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
            }
        }

        stompClient.topic("/sub/creatematch").subscribe {
            val newMessage = JSONObject(it.payload)
            val type = newMessage.getString("type")
            val userId = newMessage.getString("userId")
            matchId = newMessage.getString("matchId").toLong()

            if ((type == "CREATE") && (userId == myUserId.toString())) {
                val intent = Intent(this, MatchLobbyActivity::class.java)
                intent.putExtra("matchId", matchId)
                finish()
                ContextCompat.startActivity(this, intent, null)
            }
        }

    }


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
            runStomp()
            val data = JSONObject()
            data.put("type", "CREATE")
            data.put("crewId", crewId)
            data.put("matchTitle", matchTitle)
            data.put("matchGoalDistance", matchDistance)
            data.put("matchMaxPerson", "6")
//            data.put("userId", myUserId)
            data.put("userId", "3")
            stompClient.send("/pub/creatematch", data.toString()).subscribe()
        }
    }
}
