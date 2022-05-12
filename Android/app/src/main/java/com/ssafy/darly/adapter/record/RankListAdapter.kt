package com.ssafy.darly.adapter.record

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.R
import com.ssafy.darly.databinding.RankListBinding
import com.ssafy.darly.model.record.RankString

class RankListAdapter : RecyclerView.Adapter<RankListAdapter.RankListHolder>() {
    var rankStringList = mutableListOf<RankString>()
    var myRank = 1

    class RankListHolder private constructor(val binding: RankListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RankListHolder {
                val binding = RankListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RankListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankListHolder {
        return RankListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RankListHolder, position: Int) {
        holder.binding.viewModel = rankStringList[position]
        if (position == 0)
            holder.binding.rankNum.setBackgroundResource(R.drawable.rectangle_red_round)
        Log.d("response", "${myRank}")
        if (myRank - 1 == position) {
//            holder.binding.rankNum.setBackgroundColor(Color.parseColor("#FB5454"))
            holder.binding.rankNum.setBackgroundResource(R.drawable.rectangle_red_round)
            holder.binding.constraint.setBackgroundResource(R.drawable.rectangle_gray200_round)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = rankStringList.size;
}