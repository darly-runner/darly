package com.ssafy.darly.adapter.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.MyChallengeListBinding
import com.ssafy.darly.model.record.MyChallenge

class MyChallengeListAdapter : RecyclerView.Adapter<MyChallengeListAdapter.MyChallengeListHolder>() {
    var myChallengeList = mutableListOf<MyChallenge>()

    class MyChallengeListHolder private constructor(val binding: MyChallengeListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MyChallengeListHolder {
                val binding = MyChallengeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyChallengeListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChallengeListHolder {
        return MyChallengeListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyChallengeListHolder, position: Int) {
        holder.binding.viewModel = myChallengeList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = myChallengeList.size
}