package com.ssafy.darly.fragment

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentStatBinding
import com.ssafy.darly.model.stat.CustomBarChartRender
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.StatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class StatFragment : Fragment() {
    private lateinit var binding: FragmentStatBinding
    private lateinit var currentBtn: Button
    private val model: StatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stat, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        currentBtn = binding.weekBtn
        binding.weekBtn.setOnClickListener {
            if (currentBtn != binding.weekBtn) {
                currentBtn.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700))
            }
            binding.weekBtn.setTextColor(Color.WHITE)
            currentBtn = binding.weekBtn

            ObjectAnimator.ofFloat(it, "translationX", 100f).apply {
                duration = 2000
                start()
            }
        }
        binding.monthBtn.setOnClickListener {
            if (currentBtn != binding.monthBtn) {
                currentBtn.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700))
                val anim = TranslateAnimation(0f, 800f, 0f, 1000f) // toYDelta
                anim.duration = 2000
                currentBtn.animation = anim
                currentBtn.setBackgroundResource(R.drawable.button_transparent_round)
            }
            binding.monthBtn.setTextColor(Color.WHITE)
            binding.monthBtn.setBackgroundResource(R.drawable.button_red_round)
            currentBtn = binding.monthBtn
        }
        binding.yearBtn.setOnClickListener {
            if (currentBtn != binding.yearBtn) {
                currentBtn.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700))
                val anim = TranslateAnimation(0f, 800f, 0f, 1000f) // toYDelta
                anim.duration = 2000
                currentBtn.animation = anim
                currentBtn.setBackgroundResource(R.drawable.button_transparent_round)
            }
            binding.yearBtn.setTextColor(Color.WHITE)
            binding.yearBtn.setBackgroundResource(R.drawable.button_red_round)
            currentBtn = binding.yearBtn
        }
        binding.allBtn.setOnClickListener {
            if (currentBtn != binding.allBtn) {
                currentBtn.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700))
                val anim = TranslateAnimation(0f, 800f, 0f, 1000f) // toYDelta
                anim.duration = 2000
                currentBtn.animation = anim
                currentBtn.setBackgroundResource(R.drawable.button_transparent_round)
            }
            binding.allBtn.setTextColor(Color.WHITE)
            binding.allBtn.setBackgroundResource(R.drawable.button_red_round)
            currentBtn = binding.allBtn
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val today: LocalDate = LocalDate.now()
        val mondayDate = today.minusDays((today.dayOfWeek.value - 1).toLong()).toString()

        model.date.value = "이번주"

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getWeekStat(mondayDate)
            model.totalDistance.value = response.body()?.totalDistance?.toString() ?: "0"
            model.totalNum.value = response.body()?.totalNum?.toString() ?: "0"
            model.totalTime.value = response.body()?.totalTime?.toString() ?: "00:00:00"
            model.paceAvg.value = response.body()?.paceAvg?.toString() ?: "0'0''"
            model.heartAvg.value = response.body()?.heartAvg?.toString()
            if (model.heartAvg.value == null)
                model.heartAvg.value = "--"
            model.distances.value = response.body()?.distances ?: listOf()
            setWeek(binding.barChart, response.body()?.distances ?: listOf())
        }
    }

    private fun setWeek(barChart: BarChart, distances: List<Float>) {
        initBarChart(barChart)

        var barData = ArrayList<BarEntry>()
        var maxValue = 0f;
        for ((index, distance) in distances.withIndex()) {
            barData.add(BarEntry((index + 1).toFloat(), distance))
            if (distance > maxValue) maxValue = distance
        }
        var gran = Math.ceil((maxValue / 3).toDouble()).toFloat()
        var max = gran * 3

        var barDataSet = BarDataSet(barData, "")
        barDataSet.color = Color.parseColor("#fb5454")
        barDataSet.valueTextSize = 10f
        barDataSet.valueTextColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_600)

        val data = BarData(barDataSet)
        data.barWidth = 0.46f

        val roundedBarChartRenderer =
            CustomBarChartRender(barChart, barChart.animator, barChart.viewPortHandler)
        roundedBarChartRenderer.setRadius(15) //context.resources.getDimension(R.dimen.barCornerRadius)
        barChart.renderer = roundedBarChartRenderer

        barChart.run {
            this.data = data
            setFitBars(true)
            invalidate()

            axisRight.run {
                axisMaximum = max
                granularity = gran //선긋는단위
            }

            axisLeft.run {
                axisMaximum = max
                granularity = gran //선긋는단위
            }
        }
    }

    private fun initBarChart(barChart: BarChart) {
        barChart.run {
            setDrawBarShadow(false) //그림자 비활성화
            setTouchEnabled(false)
            setDrawValueAboveBar(true)
            setPinchZoom(false) //확대 비활성화
            setDrawGridBackground(false) //격자구조 비활성화
            setMaxVisibleValueCount(31) //최대 개수

            axisRight.run {
                axisMaximum = 15f
                axisMinimum = 0f //최소값
                granularity = 5f //선긋는단위
                setDrawLabels(true) //값 적는것을 허용
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) //축 그리기 비활성화
                axisLineColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_400)
                gridColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_400)
                textColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_600)
                textSize = 12f
            }

            axisLeft.run {
                axisMaximum = 15f
                axisMinimum = 0f //최소값
                setDrawLabels(false) //값 적는것을 허용
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) //축 그리기 비활성화
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawLabels(true)
                setDrawAxisLine(false)
                setDrawGridLines(false)
                textColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_800)
                valueFormatter = WeekValueFormatter()
                textSize = 12f
                yOffset = 10f
            }

//            글씨가 잘림
//            legend.isEnabled = false
            legend.formSize = 0f

            description.isEnabled = false //description 비활성화

            axisLeft.isEnabled = false
            setTouchEnabled(false)
            setScaleEnabled(false)
            animateY(500)
        }
    }

    inner class WeekValueFormatter : ValueFormatter() {
        private val days = arrayOf("월", "화", "수", "목", "금", "토", "일")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }
}