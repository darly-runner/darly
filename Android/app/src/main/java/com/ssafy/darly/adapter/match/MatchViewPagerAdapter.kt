package com.ssafy.darly.adapter.match

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.R
import com.ssafy.darly.model.CompetitorInfo

class MatchViewPagerAdapter() : RecyclerView.Adapter<MatchViewPagerAdapter.PagerViewHolder>() {
    var list = mutableListOf<CompetitorInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.match_list_item, parent, false)){

            fun bind(item : CompetitorInfo){

            }
    }
}