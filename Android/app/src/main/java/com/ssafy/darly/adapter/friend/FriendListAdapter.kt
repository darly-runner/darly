package com.ssafy.darly.adapter.friend

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.FriendListBinding
import com.ssafy.darly.model.friend.Friend

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListHolder>() {
    lateinit var friendList: List<Friend>
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListHolder {
        return FriendListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FriendListHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount() = friendList.size

    class FriendListHolder private constructor(val binding: FriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Friend) {
            binding.viewModel = item;
            Log.d("response-image", "${item.userImage}")
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FriendListHolder {
                val binding = FriendListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FriendListHolder(binding)
            }
        }
    }
}