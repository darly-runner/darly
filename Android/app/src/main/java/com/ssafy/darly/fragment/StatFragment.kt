package com.ssafy.darly.fragment

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.github.mikephil.charting.utils.ViewPortHandler
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentStatBinding
import com.ssafy.darly.model.stat.CustomBarChartRender
import com.ssafy.darly.model.stat.StatGetRes
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.StatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat
import java.time.LocalDate


class StatFragment : Fragment() {
    private lateinit var binding: FragmentStatBinding
    private lateinit var currentBtn: Button
    private val model: StatViewModel by viewModels()
    private lateinit var monday: LocalDate
    private lateinit var selectedDate: LocalDate
    private lateinit var dayString: String

    @RequiresApi(Build.VERSION_CODES.O)
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
                changeColorAnimation(Color.WHITE, ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), currentBtn)
                changeColorAnimation(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), Color.WHITE, binding.weekBtn)
                binding.weekBtn.setTextColor(Color.WHITE)
                ObjectAnimator.ofFloat(binding.backgroundBtn, "translationX", it.x - (it.width / 4)).apply {
                    duration = 500
                    start()
                }
                binding.dateView.text = "이번주"
                CoroutineScope(Dispatchers.Main).launch {
                    dayString = monday.toString()
                    val response = DarlyService.getDarlyService().getWeekStat(dayString)
                    setModelData(response)
                    setChart(binding.barChart, response.body()?.distances ?: listOf(), WeekValueFormatter())
                }

                currentBtn = binding.weekBtn
            }
        }
        binding.monthBtn.setOnClickListener {
            if (currentBtn != binding.monthBtn) {
                changeColorAnimation(Color.WHITE, ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), currentBtn)
                changeColorAnimation(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), Color.WHITE, binding.monthBtn)
                ObjectAnimator.ofFloat(binding.backgroundBtn, "translationX", it.x - (it.width / 4)).apply {
                    duration = 500
                    start()
                }
                binding.dateView.text = String.format("%d년 %d월", selectedDate.year, selectedDate.month.value)
                CoroutineScope(Dispatchers.Main).launch {
                    val response = DarlyService.getDarlyService().getMonthStat(monday.toString())
                    setModelData(response)
                    setChart(binding.barChart, response.body()?.distances ?: listOf(), MonthValueFormatter())
                }

                currentBtn = binding.monthBtn
            }
        }
        binding.yearBtn.setOnClickListener {
            if (currentBtn != binding.yearBtn) {
                changeColorAnimation(Color.WHITE, ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), currentBtn)
                changeColorAnimation(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), Color.WHITE, binding.yearBtn)
                ObjectAnimator.ofFloat(binding.backgroundBtn, "translationX", it.x - (it.width / 4)).apply {
                    duration = 500
                    start()
                }
                binding.dateView.text = String.format("%d년", selectedDate.year)
                CoroutineScope(Dispatchers.Main).launch {
                    val response = DarlyService.getDarlyService().getYearStat(monday.toString())
                    setModelData(response)
                    setChart(binding.barChart, response.body()?.distances ?: listOf(), YearValueFormatter())
                }

                currentBtn = binding.yearBtn
            }
        }
        binding.allBtn.setOnClickListener {
            if (currentBtn != binding.allBtn) {
                changeColorAnimation(Color.WHITE, ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), currentBtn)
                changeColorAnimation(ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_700), Color.WHITE, binding.allBtn)
                ObjectAnimator.ofFloat(binding.backgroundBtn, "translationX", it.x - (it.width / 4)).apply {
                    duration = 500
                    start()
                }
                binding.dateView.text = String.format("전체")
                CoroutineScope(Dispatchers.Main).launch {
                    val response = DarlyService.getDarlyService().getAllStat()
                    Log.d("stat", "${response.body()}")
                    setModelData(response)
                    setChart(
                        binding.barChart,
                        response.body()?.distances ?: listOf(),
                        AllValueFormatter(response.body()?.startYear ?: 2022, response.body()?.distances?.size ?: 1)
                    )
                }

                currentBtn = binding.allBtn
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monday = LocalDate.now()
        selectedDate = LocalDate.now()
        monday = monday.minusDays((monday.dayOfWeek.value - 1).toLong())
        dayString = monday.toString()

        model.date.value = "이번주"

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getWeekStat(dayString)
            setModelData(response)
            setChart(binding.barChart, response.body()?.distances ?: listOf(), WeekValueFormatter())
        }
    }

    private fun setModelData(response: Response<StatGetRes>) {
        val totalDistance = response.body()?.totalDistance ?: 0
        model.totalDistance.value =  String.format("%.02f", totalDistance)
        model.totalNum.value = response.body()?.totalNum?.toString() ?: "0"
        val totalSecs = response.body()?.totalTime ?: 0
        model.totalTime.value = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60)
        val paceAvg: Int = response.body()?.paceAvg?.toInt() ?: 0
        model.paceAvg.value = String.format("%01d'%02d''", paceAvg / 60, paceAvg % 60)
        model.heartAvg.value = response.body()?.heartAvg?.toString()
        if (model.heartAvg.value == null || model.heartAvg.value == "0")
            model.heartAvg.value = "--"
        model.distances.value = response.body()?.distances ?: listOf()
    }

    private fun setChart(barChart: BarChart, distances: List<Float>, format: ValueFormatter) {
        initBarChart(barChart)

        var barData = ArrayList<BarEntry>()
        var maxValue = 0f;
        for ((index, distance) in distances.withIndex()) {
            barData.add(BarEntry((index + 1).toFloat(), if (distance > 0) distance else -3f))
            if (distance > maxValue) maxValue = distance
        }
        barData.add(BarEntry(7f, -3f))
        var gran = Math.ceil((maxValue / 3).toDouble()).toFloat()
        if(gran == 0f) gran = 1f
        var max = gran * 3

        var barDataSet = BarDataSet(barData, "")
        barDataSet.color = Color.parseColor("#fb5454")
        barDataSet.valueTextSize = 10f
        barDataSet.valueTextColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.gray_600)
        barDataSet.valueFormatter = FloatFormatter()

        val data = BarData(barDataSet)
        data.barWidth = 0.46f

        barChart.run {
            this.data = data
            setFitBars(true)

            axisRight.run {
                axisMaximum = max
                granularity = gran //선긋는단위
            }

            axisLeft.run {
                axisMaximum = max
                granularity = gran //선긋는단위
            }

            xAxis.run {
                valueFormatter = format
                setLabelCount(12, false)
            }

            invalidate()
        }
    }

    private fun initBarChart(barChart: BarChart) {
        barChart.run {
            setDrawBarShadow(false) //그림자 비활성화
            setTouchEnabled(false)
            setDrawValueAboveBar(true)
            setPinchZoom(false) //확대 비활성화
            setDrawGridBackground(false) //격자구조 비활성화
//            setMaxVisibleValueCount(13) //최대 개수

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
//                valueFormatter = WeekValueFormatter()
                textSize = 11f
                yOffset = 10f
                extraBottomOffset = 10f
            }

            legend.isEnabled = false

            description.isEnabled = false //description 비활성화

            axisLeft.isEnabled = false
            setTouchEnabled(false)
            setScaleEnabled(false)
//            animateY(500)

            val roundedBarChartRenderer =
                CustomBarChartRender(barChart, barChart.animator, barChart.viewPortHandler)
            roundedBarChartRenderer.setRadius(15)
            barChart.renderer = roundedBarChartRenderer
        }
    }

    inner class WeekValueFormatter : ValueFormatter() {
        private val days = arrayOf("월", "화", "수", "목", "금", "토", "일")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }

    inner class MonthValueFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return value.toInt().toString()
        }
    }

    inner class YearValueFormatter : ValueFormatter() {
        private var days = ArrayList<String>()

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            for (i in 1..12)
                days.add(i.toString())
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }

    inner class AllValueFormatter(private val startYear: Int, private val size: Int) : ValueFormatter() {
        private val days = ArrayList<String>()
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            for (i in 0 until size)
                days.add((startYear + i).toString())
            while (days.size < 7)
                days.add("")
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }

    inner class FloatFormatter : ValueFormatter() {
        private val mFormat: DecimalFormat
        fun getFormattedValue(value: Float, entry: Map.Entry<*, *>?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?): String {
            // write your logic here
            return mFormat.format(value).toString() + " $" // e.g. append a dollar-sign
        }

        init {
            mFormat = DecimalFormat("###,###,##0.0") // use one decimal
        }
    }

    private fun changeColorAnimation(fromColor: Int, toColor: Int, view: Button) {
        val valueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener { animator: ValueAnimator -> view.setTextColor(animator.animatedValue as Int) }
        valueAnimator.start()
    }
}