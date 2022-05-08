package com.ssafy.darly.adapter.friend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.activity.FriendProfileActivity
import com.ssafy.darly.databinding.FriendAddListBinding
import com.ssafy.darly.model.friend.Friend
import com.ssafy.darly.model.friend.FriendApplyReq
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendAddListAdapter(private val context: Context) :
    RecyclerView.Adapter<FriendAddListAdapter.FriendAddListHolder>() {
    var friendList = mutableListOf<Friend>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendAddListHolder {
        return FriendAddListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FriendAddListHolder, position: Int) {
        var item = friendList[position]
        if (item.userImage == null)
            item.userImage =
                "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"
        if (item.userMessage != null && item.userMessage.length > 15)
            item.userMessage = item.userMessage.slice(IntRange(0, 15)) + "..."
        holder.binding.viewModel = item
        holder.binding.applyBtn.visibility = android.view.View.VISIBLE
        holder.binding.completeBtn.visibility = android.view.View.GONE
        holder.binding.constraint.setOnClickListener {
            context.startActivity(
                Intent(context, FriendProfileActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("friendId", item.userId)
            )
        }
        holder.binding.applyBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                DarlyService.getDarlyService().applyFriend(FriendApplyReq(item.userId))
            }
            holder.binding.applyBtn.visibility = android.view.View.GONE
            holder.binding.completeBtn.visibility = android.view.View.VISIBLE
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = friendList.size

    class FriendAddListHolder private constructor(val binding: FriendAddListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): FriendAddListHolder {
                val binding = FriendAddListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FriendAddListHolder(binding)
            }
        }
    }

}