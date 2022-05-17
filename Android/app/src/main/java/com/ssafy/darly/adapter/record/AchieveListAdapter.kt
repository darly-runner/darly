package com.ssafy.darly.adapter.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.AchieveListBinding
import com.ssafy.darly.model.record.Achieve

class AchieveListAdapter : RecyclerView.Adapter<AchieveListAdapter.AchieveListHolder>() {
    var achieveList = mutableListOf<Achieve>()

    class AchieveListHolder private constructor(val binding: AchieveListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AchieveListHolder {
                val binding = AchieveListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return AchieveListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveListHolder {
        return AchieveListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AchieveListHolder, position: Int) {
        holder.binding.viewModel = achieveList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = achieveList.size
}