package com.ssafy.darly.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.darly.adapter.friend.FriendListAdapter
import com.ssafy.darly.adapter.user.UserFeedListAdapter
import com.ssafy.darly.model.friend.Friend
import com.ssafy.darly.model.user.Feed

object BindingConversion {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("setFeedItems")
    fun setFeedItems(recyclerView: RecyclerView, items: List<String>) {
        var feedList = mutableListOf<Feed>()
        for ((index, item) in items.withIndex()) {
            feedList.add(Feed(item, index))
        }
        items?.let {
            (recyclerView.adapter as UserFeedListAdapter).userFeedList = feedList
        }
    }

    @JvmStatic
    @BindingAdapter("setFriendItems")
    fun setFriendItems(recyclerView: RecyclerView, items: List<Friend>) {
        var friendList = mutableListOf<Friend>()
        var filteredFriendList = mutableListOf<Friend>()
        for (item in items) {
            friendList.add(item)
        }
        filteredFriendList.addAll(friendList)
        items?.let {
            (recyclerView.adapter as FriendListAdapter).friendList = friendList
            (recyclerView.adapter as FriendListAdapter).filteredFriendList = filteredFriendList
        }
    }
}