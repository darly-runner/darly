package com.ssafy.darly.adapter.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.SectionSmallListBinding
import com.ssafy.darly.model.record.SectionString

class SectionListAdapter : RecyclerView.Adapter<SectionListAdapter.SectionListHolder>() {
    var sectionStringList = mutableListOf<SectionString>()

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
        holder.binding.viewModel = sectionStringList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = sectionStringList.size
}