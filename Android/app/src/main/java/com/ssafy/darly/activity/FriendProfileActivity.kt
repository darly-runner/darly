package com.ssafy.darly.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.user.UserFeedListAdapter
import com.ssafy.darly.databinding.ActivityFriendProfileBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.FriendProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class FriendProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendProfileBinding
    private lateinit var userFeedListAdapter: UserFeedListAdapter
    private val model: FriendProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_friend_profile)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend_profile)
        binding.lifecycleOwner = this
        binding.viewModel = model
        userFeedListAdapter = UserFeedListAdapter()
        binding.recyclerView.adapter = userFeedListAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        val friendId = intent.getLongExtra("friendId", 1)

        CoroutineScope(Dispatchers.Main).launch {
            val response =
                DarlyService.getDarlyService().getFriendProfile(friendId)
            model.userNickname.value = response.body()?.userNickname ?: "nickname"
            model.userMessage.value = response.body()?.userMessage ?: "message"
            var list = response.body()?.userAddress ?: listOf()
            model.userAddress.value = if (list.isEmpty()) "address" else list.get(0)
            model.userTotalDistance.value = response.body()?.userTotalDistance ?: 0.0F
            val dec = DecimalFormat("#,###");
            model.userFriendNum.value =
                if (response.body()?.userFriendNum == null) "0" else dec.format(response.body()?.userFriendNum)
            model.userImage.value = response.body()?.userImage ?: ""
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFriendFeedList(friendId, 0)
            model.userFeedList.value = response.body()?.feeds ?: listOf()
            userFeedListAdapter.notifyDataSetChanged()
        }

        binding.backBtn.setOnClickListener { finish() }
    }
}