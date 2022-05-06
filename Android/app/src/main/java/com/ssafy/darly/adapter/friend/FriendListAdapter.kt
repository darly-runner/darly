package com.ssafy.darly.adapter.friend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.FriendListBinding
import com.ssafy.darly.dialog.FriendDeleteDialog
import com.ssafy.darly.model.friend.Friend

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListHolder>(), Filterable {
    lateinit var context: Context
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

    override fun getItemCount() = filteredFriendList.size


    fun deleteItem(position: Int) {
        this.filteredFriendList.removeAt(position)
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
