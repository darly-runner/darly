package com.ssafy.darly.adapter.record

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.R
import com.ssafy.darly.databinding.SectionSmallListBinding
import com.ssafy.darly.model.record.SectionString

class SectionListAdapter : RecyclerView.Adapter<SectionListAdapter.SectionListHolder>() {
    var sectionStringList = mutableListOf<SectionString>()
    var minValue = 0
    var gapValue = 0
    var minIndex = 0
    private var barWidth = 0

    class SectionListHolder private constructor(val binding: SectionSmallListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SectionListHolder {
                val binding = SectionSmallListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SectionListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionListHolder {
        return SectionListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectionListHolder, position: Int) {
        if (barWidth == 0)
            barWidth = holder.binding.barView.layoutParams.width
        holder.binding.viewModel = sectionStringList[position]
        if (position == minIndex)
            holder.binding.barView.setBackgroundResource(R.drawable.rectangle_red_round)
        else if(gapValue != 0){
            val widthPercent = getWidthPercent(position)
            holder.binding.barView.layoutParams.width = (barWidth * widthPercent).toInt()
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = sectionStringList.size

    private fun getWidthPercent(position: Int): Double {
        return 1 - ((sectionStringList[position].paceInt - minValue) / gapValue * 0.6)
    }
}