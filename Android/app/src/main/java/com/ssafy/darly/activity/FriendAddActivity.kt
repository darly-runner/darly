package com.ssafy.darly.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.friend.FriendAddListAdapter
import com.ssafy.darly.databinding.ActivityFriendAddBinding
import com.ssafy.darly.model.friend.FriendSearchReq
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.FriendAddViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendAddBinding
    private lateinit var friendAddListAdapter: FriendAddListAdapter
    private val model: FriendAddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend_add)
        binding.lifecycleOwner = this
        binding.viewModel = model
        friendAddListAdapter = FriendAddListAdapter(this)
        binding.recyclerView.adapter = friendAddListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.trim()?.length == 0) {
                    model.friendList.value = listOf()
                    friendAddListAdapter.notifyDataSetChanged()
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.d("add", p0.toString())
                        val response = DarlyService.getDarlyService().getSearchFriend(FriendSearchReq(p0.toString()))
                        Log.d("add-response", "${response.body()}")
                        model.friendList.value = response.body()?.users ?: listOf()
                        friendAddListAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}