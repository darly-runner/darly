package com.ssafy.darly.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityRecordDetailBinding
import com.ssafy.darly.model.record.*
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat

class RecordDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityRecordDetailBinding
    private val model: RecordDetailViewModel by viewModels()
    private var recordId = 0L
    private lateinit var imm: InputMethodManager
    private lateinit var map: GoogleMap
    private val defaultImage =
        "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"

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
                    DarlyService.getDarlyService().updateRecordTitle(recordId, RecordTitlePatchReq(textView.text.toString()))
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
        if (pace == 0)
            model.recordPace.value = "--"
        else
            model.recordPace.value = String.format("%01d'%02d''", pace / 60, pace % 60)
        model.recordHeart.value = response.body()?.recordHeart?.toString()
        if (model.recordHeart.value == null || model.recordHeart.value == "0")
            model.recordHeart.value = "--"
        model.recordSpeed.value = response.body()?.recordSpeed?.toString()
        val dec = DecimalFormat("#,###")
        model.recordCalories.value = dec.format(response.body()?.recordCalories)
        model.coordinateLongitudes.value = response.body()?.coordinateLongitudes
        model.coordinateLatitudes.value = response.body()?.coordinateLatitudes
        model.recordRank.value = response.body()?.recordRank ?: 0
        model.recordRankString.value = response.body()?.recordRank.toString()
        if (model.recordRank.value == null || model.recordRank.value == 0)
            model.recordRankString.value = "--"
        model.ranks.value = response.body()?.ranks ?: listOf()
        //
//        val rrr = mutableListOf<Rank>()
//        rrr.add(Rank("사용자1", null, 1, 1000, 300))
//        rrr.add(Rank("사용자2", null, 3, 2000, 600))
//        rrr.add(Rank("사용자3", null, 2, 3000, 700))
//        rrr.add(Rank("사용자4", null, 4, 4000, 1000))
        //
        val rankStringList = mutableListOf<RankString>()
        for (rank in model.ranks.value ?: listOf()) {
//        for (rank in rrr ?: listOf()) {
            val image = if (rank.userImage == null) defaultImage else rank.userImage
            val pace = if (rank.matchResultPace == 0) "--" else String.format("%01d'%02d''", rank.matchResultPace / 60, rank.matchResultPace % 60)
            val time = String.format("%02d:%02d:%02d", rank.matchResultTime / 3600, (rank.matchResultTime % 3600) / 60, rank.matchResultTime % 60)
            rankStringList.add(RankString(rank.userNickname, image, rank.matchResultRank.toString(), time, pace))
        }
        model.rankStringList.value = rankStringList

        val sectionList = mutableListOf<SectionString>()
        var min = Int.MAX_VALUE
        var minIndex = 0
        var max = -1
        for ((index, section) in response.body()?.sections?.withIndex() ?: listOf()) {
            if (section.pace < min) {
                min = section.pace
                minIndex = index
            }
            if (section.pace > max)
                max = section.pace
            val km = if (section.km % 1 == 0f) section.km.toInt().toString()
            else String.format("%.02f", section.km)
            val pace = if (section.pace == 0) "--" else String.format("%01d'%02d''", section.pace / 60, section.pace % 60)
            val calories = section.calories.toString()
            sectionList.add(SectionString(km, pace, calories, section.pace))
        }
        model.minSectionIndex.value = minIndex
        model.minSectionValue.value = min
        model.gapSectionValue.value = max - min
        model.sections.value = sectionList
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}