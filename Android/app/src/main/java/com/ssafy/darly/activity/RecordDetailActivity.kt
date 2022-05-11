package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.util.DataUtils
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityRecordDetailBinding
import com.ssafy.darly.model.record.RecordDetailGetRes
import com.ssafy.darly.model.stat.StatGetRes
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RecordDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordDetailBinding
    private val model: RecordDetailViewModel by viewModels()
    private var recordId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        recordId = intent.getLongExtra("recordId", 0L)
        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getRecordDetail(recordId)
//            model.records.value = response.body()?.records ?: listOf()
//            recordListAdapter.notifyDataSetChanged()
        }
    }

//    private fun setModelData(response: Response<RecordDetailGetRes>) {
//        model.recordTitle.value
//        model.totalDistance.value = response.body()?.totalDistance?.toString() ?: "0"
//        model.totalNum.value = response.body()?.totalNum?.toString() ?: "0"
//        val totalSecs = response.body()?.totalTime ?: 0
//        model.totalTime.value = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60)
//        val paceAvg: Int = response.body()?.paceAvg?.toInt() ?: 0
//        model.paceAvg.value = String.format("%01d'%02d''", paceAvg / 60, paceAvg % 60)
//        model.heartAvg.value = response.body()?.heartAvg?.toString()
//        if (model.heartAvg.value == null || model.heartAvg.value == "0")
//            model.heartAvg.value = "--"
//        model.distances.value = response.body()?.distances ?: listOf()
//    }
}