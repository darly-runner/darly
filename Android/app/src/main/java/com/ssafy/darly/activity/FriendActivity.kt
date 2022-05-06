package com.ssafy.darly.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.friend.FriendListAdapter
import com.ssafy.darly.databinding.ActivityFriendBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.FriendViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendBinding
    private lateinit var friendListAdapter: FriendListAdapter
    private val model: FriendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend)
//        setContentView(R.layout.activity_friend)
        binding.lifecycleOwner = this
        binding.viewModel = model
        friendListAdapter = FriendListAdapter()
        binding.recyclerView.adapter = friendListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFriendList()
            model.friendList.value = response.body()?.users ?: listOf()
            Log.d("response", "${response}")
            Log.d("response body", "${response.body()}")
            friendListAdapter.notifyDataSetChanged()
        }

    }


}