package com.ssafy.darly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.darly.databinding.ActivityCrewCommentsBinding

class CrewCommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewCommentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crew_comments)
    }
}