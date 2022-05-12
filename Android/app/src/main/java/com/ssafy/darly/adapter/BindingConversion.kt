package com.ssafy.darly.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.darly.adapter.address.AddressListAdapter
import com.ssafy.darly.adapter.friend.FriendAddListAdapter
import com.ssafy.darly.adapter.friend.FriendListAdapter
import com.ssafy.darly.adapter.friend.FriendWaitingListAdapter
import com.ssafy.darly.adapter.mypage.MyPageListAdapter
import com.ssafy.darly.adapter.record.RankListAdapter
import com.ssafy.darly.adapter.record.RecordListAdapter
import com.ssafy.darly.adapter.record.SectionListAdapter
import com.ssafy.darly.adapter.user.UserFeedListAdapter
import com.ssafy.darly.model.address.Address
import com.ssafy.darly.model.friend.Friend
import com.ssafy.darly.model.record.RankString
import com.ssafy.darly.model.record.Record
import com.ssafy.darly.model.record.SectionString
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

    @JvmStatic
    @BindingAdapter("setFriendWaitingItems")
    fun setFriendWaitingItems(recyclerView: RecyclerView, items: List<Friend>) {
        var friendList = mutableListOf<Friend>()
        var filteredFriendList = mutableListOf<Friend>()
        for (item in items) {
            friendList.add(item)
        }
        filteredFriendList.addAll(friendList)
        items?.let {
            (recyclerView.adapter as FriendWaitingListAdapter).friendList = friendList
            (recyclerView.adapter as FriendWaitingListAdapter).filteredFriendList = filteredFriendList
        }
    }

    @JvmStatic
    @BindingAdapter("setFriendAddItems")
    fun setFriendAddItems(recyclerView: RecyclerView, items: List<Friend>) {
        var friendList = mutableListOf<Friend>()
        for (item in items) {
            friendList.add(item)
        }
        items?.let {
            (recyclerView.adapter as FriendAddListAdapter).friendList = friendList
        }
    }

    @JvmStatic
    @BindingAdapter("setMyPageUpdateItems")
    fun setMyPageUpdateItems(recyclerView: RecyclerView, items: List<Address>) {
        var addressList = mutableListOf<Address>()
        for (item in items) {
            addressList.add(item)
        }
        items?.let {
            (recyclerView.adapter as MyPageListAdapter).addressList = addressList
        }
    }

    @JvmStatic
    @BindingAdapter("setAddressSearchItems")
    fun setAddressSearchItems(recyclerView: RecyclerView, items: List<Address>) {
        var addressList = mutableListOf<Address>()
        for (item in items) {
            addressList.add(item)
        }
        items?.let {
            (recyclerView.adapter as AddressListAdapter).addressList = addressList
        }
    }

    @JvmStatic
    @BindingAdapter("setRecordItems")
    fun setRecordItems(recyclerView: RecyclerView, items: List<Record>) {
        var recordList = mutableListOf<Record>()
        for (item in items) {
            recordList.add(item)
        }
        items?.let {
            (recyclerView.adapter as RecordListAdapter).recordList = recordList
        }
    }

    @JvmStatic
    @BindingAdapter("setSectionItems")
    fun setSectionItems(recyclerView: RecyclerView, items: List<SectionString>) {
        var sectionList = mutableListOf<SectionString>()
        for (item in items) {
            sectionList.add(item)
        }
        items?.let {
            (recyclerView.adapter as SectionListAdapter).sectionStringList = sectionList
        }
    }

    @JvmStatic
    @BindingAdapter("setSectionMinValue")
    fun setSectionMinValue(recyclerView: RecyclerView, item: Int) {
        item?.let {
            (recyclerView.adapter as SectionListAdapter).minValue = item
        }
    }

    @JvmStatic
    @BindingAdapter("setSectionGapValue")
    fun setSectionGapValue(recyclerView: RecyclerView, item: Int) {
        item?.let {
            (recyclerView.adapter as SectionListAdapter).gapValue = item
        }
    }

    @JvmStatic
    @BindingAdapter("setSectionMinIndex")
    fun setSectionMinIndex(recyclerView: RecyclerView, item: Int) {
        item?.let {
            (recyclerView.adapter as SectionListAdapter).minIndex = item
        }
    }

    @JvmStatic
    @BindingAdapter("setRankItems")
    fun setRankItems(recyclerView: RecyclerView, items: List<RankString>) {
        var rankStringList = mutableListOf<RankString>()
        for (item in items) {
            rankStringList.add(item)
        }
        items?.let {
            (recyclerView.adapter as RankListAdapter).rankStringList = rankStringList
        }
    }

    @JvmStatic
    @BindingAdapter("setMyRank")
    fun setMyRank(recyclerView: RecyclerView, item: Int) {
        item?.let {
            (recyclerView.adapter as RankListAdapter).myRank = item
        }
    }
}