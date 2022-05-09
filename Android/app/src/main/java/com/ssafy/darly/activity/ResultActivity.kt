package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityResultBinding
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val model: RunningViewModel by viewModels()

    lateinit var record : RecordRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        binding.lifecycleOwner = this
        binding.viewModel = model

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        record = intent.getSerializableExtra("record") as RecordRequest
        model.pace.value = model.timeToStr(record.recordPace)
        model.calorie.value = "${record.recordCalories} kcal"
        model.speed.value = record.recordSpeed
        model.time.value = model.timeToStr(record.recordTime)

        // 구간별, 추후에 차트 추가
        var cnt = 1
        for(i in record.sections){
            val textView = TextView(this)
            textView.text = "${cnt++} / ${i.km} km , ${i.pace}, ${i.calories}"
            binding.sectionView.addView(textView)
        }

        // 기록 저장
        binding.writeButton.setOnClickListener {
            if(binding.titleText.text.isNotEmpty())
                record.recordTitle = binding.titleText.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                DarlyService.getDarlyService().postRecord(record)

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}