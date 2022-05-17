package com.ssafy.darly.adapter.match

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.MatchListItemBinding
import com.ssafy.darly.model.socket.UserModel

class MatchViewPagerAdapter() : RecyclerView.Adapter<MatchViewPagerAdapter.PagerViewHolder>() {
    var list = mutableListOf<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder.from(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.binding.viewModel = list[position]
        holder.binding.executePendingBindings()
    }

    class PagerViewHolder private constructor(val binding: MatchListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PagerViewHolder {
                val binding = MatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PagerViewHolder(binding)
            }
        }
    }
}