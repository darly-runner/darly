package com.ssafy.darly.activity

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityRecordDetailBinding
import com.ssafy.darly.model.record.RecordDetailGetRes
import com.ssafy.darly.model.record.RecordTitlePatchReq
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat

class RecordDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordDetailBinding
    private val model: RecordDetailViewModel by viewModels()
    private var recordId = 0L
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        recordId = intent.getLongExtra("recordId", 0L)
        CoroutineScope(Dispatchers.Main).launch {
            setModelData(DarlyService.getDarlyService().getRecordDetail(recordId))
        }

        binding.backBtn.setOnClickListener { finish() }

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.editBtn.setOnClickListener {
            binding.titleText.requestFocus()
            binding.titleText.setSelection(binding.titleText.length())
            imm.showSoftInput(binding.titleText, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.titleText.setOnEditorActionListener { view, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                CoroutineScope(Dispatchers.Main).launch {
                    val textView = view as EditText
                    val response = DarlyService.getDarlyService().updateRecordTitle(recordId, RecordTitlePatchReq(textView.text.toString()))
                }
            }
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            binding.titleText.clearFocus()
            true
        }

    }

    private fun setModelData(response: Response<RecordDetailGetRes>) {
        model.recordTitle.value = response.body()?.recordTitle
        model.recordDate.value = response.body()?.recordDate
        model.recordDistance.value = String.format("%.02f", response.body()?.recordDistance)
        val totalSecs = response.body()?.recordTime ?: 0
        model.recordTime.value = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60)
        val pace: Int = response.body()?.recordPace ?: 0
        model.recordPace.value = String.format("%01d'%02d''", pace / 60, pace % 60)
        model.recordHeart.value = response.body()?.recordHeart?.toString()
        if (model.recordHeart.value == null || model.recordHeart.value == "0")
            model.recordHeart.value = "--"
        model.recordSpeed.value = response.body()?.recordSpeed?.toString()
        val dec = DecimalFormat("#,###")
        model.recordCalories.value = dec.format(response.body()?.recordCalories)
        model.coordinateLongitudes.value = response.body()?.coordinateLongitudes
        model.coordinateLatitudes.value = response.body()?.coordinateLatitudes
        model.recordRank.value = response.body()?.recordRank.toString()
        if (model.recordRank.value == null || model.recordRank.value == "0")
            model.recordRank.value = "--"
    }
}