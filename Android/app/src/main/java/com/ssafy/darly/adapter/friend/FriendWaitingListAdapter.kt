package com.ssafy.darly.adapter.friend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.FriendWaitingListBinding
import com.ssafy.darly.model.friend.Friend
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendWaitingListAdapter(private val context: Context) :
    RecyclerView.Adapter<FriendWaitingListAdapter.FriendWaitingListHolder>(), Filterable {

    var friendList = mutableListOf<Friend>()
    var filteredFriendList = mutableListOf<Friend>()
    private var itemFilter = ItemFilter()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendWaitingListAdapter.FriendWaitingListHolder {
        return FriendWaitingListHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: FriendWaitingListAdapter.FriendWaitingListHolder,
        position: Int
    ) {
        var item = filteredFriendList[position]
        if (item.userImage == null)
            item.userImage =
                "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"
        if (item.userMessage != null && item.userMessage.length > 10)
            item.userMessage = item.userMessage.slice(IntRange(0, 10)) + "..."
        holder.binding.viewModel = item;
        holder.binding.executePendingBindings()
        holder.binding.acceptBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                DarlyService.getDarlyService().acceptFriend(item.userId)
                deleteItem(position)
            }
        }
        holder.binding.denyBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                DarlyService.getDarlyService().denyFriend(item.userId)
                deleteItem(position)
            }
        }
    }

    override fun getItemCount() = filteredFriendList.size

    fun deleteItem(position: Int) {
        this.filteredFriendList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    class FriendWaitingListHolder private constructor(val binding: FriendWaitingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): FriendWaitingListHolder {
                val binding = FriendWaitingListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FriendWaitingListHolder(binding)
            }
        }
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