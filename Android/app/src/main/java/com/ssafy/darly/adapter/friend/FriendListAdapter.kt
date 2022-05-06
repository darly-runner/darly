package com.ssafy.darly.adapter.friend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.FriendListBinding
import com.ssafy.darly.dialog.FriendDeleteDialog
import com.ssafy.darly.model.friend.Friend

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListHolder>() {
    lateinit var friendList: MutableList<Friend>
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListHolder {
        return FriendListHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FriendListHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val item = friendList[position]
        holder.binding.viewModel = item;
        holder.binding.deleteBtn.setOnClickListener {
            val dlg = FriendDeleteDialog(it.context as AppCompatActivity)
            dlg.setOnClickedListener(object : FriendDeleteDialog.ButtonClickListener {
                override fun onClicked() {
                    deleteItem(position)
                }
            })
            dlg.show(item)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = friendList.size

    fun deleteItem(position: Int) {
        this.friendList.removeAt(position)
        notifyItemRemoved(position)
    }

    class FriendListHolder private constructor(val binding: FriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
