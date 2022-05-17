package com.ssafy.darly.adapter.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.SectionBigListBinding
import com.ssafy.darly.model.record.SectionString

class SectionDetailListAdapter : RecyclerView.Adapter<SectionDetailListAdapter.SectionDetailListHolder>() {
    var sectionStringList = mutableListOf<SectionString>()
    var minValue = 0
    var gapValue = 0
    private var barWidth = 0

    class SectionDetailListHolder private constructor(val binding: SectionBigListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SectionDetailListHolder {
                val binding = SectionBigListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SectionDetailListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionDetailListHolder {
        return SectionDetailListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectionDetailListHolder, position: Int) {
        if (barWidth == 0)
            barWidth = holder.binding.barView.layoutParams.width
        holder.binding.viewModel = sectionStringList[position]
        if (gapValue != 0) {
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