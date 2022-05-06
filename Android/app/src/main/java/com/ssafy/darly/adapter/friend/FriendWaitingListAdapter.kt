package com.ssafy.darly.adapter.friend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.FriendWaitingListBinding
import com.ssafy.darly.model.friend.Friend

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
        val item = filteredFriendList[position]
        holder.binding.viewModel = item;
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = filteredFriendList.size

    fun deleteItem(position: Int) {
        this.filteredFriendList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
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