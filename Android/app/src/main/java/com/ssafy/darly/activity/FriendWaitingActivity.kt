package com.ssafy.darly.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.friend.FriendListAdapter
import com.ssafy.darly.adapter.friend.FriendWaitingListAdapter
import com.ssafy.darly.databinding.ActivityFriendWaitingBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.FriendWaitingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class FriendWaitingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendWaitingBinding
    private lateinit var friendWaitingListAdapter: FriendWaitingListAdapter
    private val model: FriendWaitingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend_waiting)
//        setContentView(R.layout.activity_friend_waiting)
        binding.lifecycleOwner = this
        binding.viewModel = model
        friendWaitingListAdapter = FriendWaitingListAdapter(this)
        binding.recyclerView.adapter = friendWaitingListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFriendWaitingList()
            val friendWaitingList = response.body()?.users ?: listOf()
            model.friendWaitingList.value = friendWaitingList
            friendWaitingListAdapter.notifyDataSetChanged()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                friendWaitingListAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}