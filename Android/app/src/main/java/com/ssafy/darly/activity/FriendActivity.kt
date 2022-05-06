package com.ssafy.darly.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.friend.FriendListAdapter
import com.ssafy.darly.databinding.ActivityFriendBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.FriendViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

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
        friendListAdapter = FriendListAdapter(this)
        binding.recyclerView.adapter = friendListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFriendList()
            model.friendList.value = response.body()?.users ?: listOf()
            friendListAdapter.notifyDataSetChanged()
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFriendWaitingList()
            val friendWaitingList = response.body()?.users ?: listOf()
            model.friendWaitingList.value = friendWaitingList
            if (friendWaitingList.size < 2) {
                binding.circleImageView1.visibility = android.view.View.VISIBLE
                binding.circleImageView2.visibility = android.view.View.GONE
                binding.circleImageView3.visibility = android.view.View.GONE
                if (friendWaitingList.isEmpty()) {
                    Glide.with(binding.circleImageView1.context)
                        .load("https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo.png")
                        .into(binding.circleImageView1)
                    model.friendApplyMessage.value = "친구 요청이 없습니다"
                } else {
                    Glide.with(binding.circleImageView1.context)
                        .load(
                            model.friendWaitingList.value?.get(0)?.userImage
                                ?: "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo.png"
                        )
                        .into(binding.circleImageView1)
                    model.friendApplyMessage.value =
                        model.friendWaitingList.value?.get(0)?.userNickname + "님"
                }
            } else {
                binding.circleImageView1.visibility = android.view.View.GONE
                binding.circleImageView2.visibility = android.view.View.VISIBLE
                binding.circleImageView3.visibility = android.view.View.VISIBLE
                Glide.with(binding.circleImageView2.context)
                    .load(
                        model.friendWaitingList.value?.get(1)?.userImage
                            ?: "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo.png"
                    )
                    .into(binding.circleImageView2)
                Glide.with(binding.circleImageView3.context)
                    .load(
                        model.friendWaitingList.value?.get(0)?.userImage
                            ?: "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo.png"
                    )
                    .into(binding.circleImageView3)

                val dec = DecimalFormat("#,###");
                model.friendApplyMessage.value =
                    model.friendWaitingList.value?.get(0)?.userNickname + "님 외 " + dec.format(
                        model.friendWaitingList.value?.size?.minus(1) ?: 1
                    ) + "명"
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                friendListAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.linearLayout.setOnClickListener {
            val nextIntent = Intent(this, FriendWaitingActivity::class.java)
            startActivity(nextIntent)
        }

    }


}