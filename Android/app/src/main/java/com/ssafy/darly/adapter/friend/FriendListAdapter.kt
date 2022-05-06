package com.ssafy.darly.adapter.friend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.activity.FriendProfileActivity
import com.ssafy.darly.databinding.FriendListBinding
import com.ssafy.darly.dialog.FriendDeleteDialog
import com.ssafy.darly.model.friend.Friend

class FriendListAdapter(private val context: Context) :
    RecyclerView.Adapter<FriendListAdapter.FriendListHolder>(), Filterable {
    //    lateinit var context: Context
    var friendList = mutableListOf<Friend>()
    var filteredFriendList = mutableListOf<Friend>()
    private var itemFilter = ItemFilter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListHolder {
        return FriendListHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FriendListHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val item = filteredFriendList[position]
        if (item.userImage == null)
            item.userImage =
                "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"
        if (item.userMessage != null && item.userMessage.length > 15)
            item.userMessage = item.userMessage.slice(IntRange(0, 15)) + "..."
        holder.binding.viewModel = item
        holder.binding.deleteBtn.setOnClickListener {
            val dlg = FriendDeleteDialog(it.context as AppCompatActivity)
            dlg.setOnClickedListener(object : FriendDeleteDialog.ButtonClickListener {
                override fun onClicked() {
                    deleteItem(holder)
                }
            })
            dlg.show(item)
        }
        holder.binding.constraint.setOnClickListener {
            context.startActivity(
                Intent(context, FriendProfileActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("friendId", item.userId)
            )
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = filteredFriendList.size


    fun deleteItem(holder: FriendListHolder) {
        val newPosition: Int = holder.getAdapterPosition()
        filteredFriendList.removeAt(newPosition)
        notifyItemRemoved(newPosition)
        notifyItemRangeChanged(newPosition, filteredFriendList.size)
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

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filterString = p0.toString()
            val results = FilterResults()

            val filteredList = ArrayList<Friend>()

            if (filterString.trim().isEmpty()) {
                results.values = friendList
                results.count = friendList.size
                return results
            } else {
                for (friend in friendList) {
                    if (friend.userNickname.contains(filterString))
                        filteredList.add(friend)
                }
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            filteredFriendList.clear()
            filteredFriendList.addAll(p1?.values as List<Friend> ?: listOf())
            notifyDataSetChanged()
        }

    }

}
