package com.ssafy.darly.adapter.user

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.databinding.MypageFeedGridBinding
import com.ssafy.darly.model.MyCrewDetails
import com.ssafy.darly.model.user.Feed

class UserFeedListAdapter : RecyclerView.Adapter<UserFeedListAdapter.UserFeedListHolder>() {
    lateinit var userFeedList: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedListHolder {
        return UserFeedListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserFeedListHolder, position: Int) {
        Log.d("response-binding item", userFeedList[position])
        holder.bind(userFeedList[position])
    }

    override fun getItemCount() = userFeedList.size


    class UserFeedListHolder private constructor(val binding: MypageFeedGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Log.d("response-per item", item)
            binding.viewModel = item;
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserFeedListHolder {
                val binding = MypageFeedGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UserFeedListHolder(binding)
            }
        }
    }

    fun submitList(feeds: List<String>) {
        Log.d("response-submit", "${feeds}")
        userFeedList = feeds
        this.notifyDataSetChanged()
    }
}