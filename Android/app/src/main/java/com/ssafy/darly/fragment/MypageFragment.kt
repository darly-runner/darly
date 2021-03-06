package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.activity.FriendActivity
import com.ssafy.darly.activity.LoginActivity
import com.ssafy.darly.activity.MyPageUpdateActivity
import com.ssafy.darly.adapter.user.UserFeedListAdapter
import com.ssafy.darly.databinding.FragmentMypageBinding
import com.ssafy.darly.dialog.MyPageMenuDialog
import com.ssafy.darly.model.address.Address
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.viewmodel.MypageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var userFeedListAdapter: UserFeedListAdapter
    private lateinit var userAddresses: ArrayList<Address>
    private val model: MypageViewModel by viewModels()
    private val defaultImage =
        "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"

    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        userFeedListAdapter = UserFeedListAdapter()
        binding.recyclerView.adapter = userFeedListAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)

        binding.menuBtn.setOnClickListener {
            val myPageMenuDialog = MyPageMenuDialog(context as AppCompatActivity)
            myPageMenuDialog.show()
            myPageMenuDialog.setOnClickedListener(object : MyPageMenuDialog.ButtonClickListener {
                override fun onClickedLogout() {
                    UserApiClient.instance.logout { error ->
                        if (error == null)
                            Toast.makeText(context, "???????????? ??????", Toast.LENGTH_SHORT).show()
                    }

                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient?.signOut()

                    var logoutIntent = Intent(context, LoginActivity::class.java)
                    logoutIntent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                    GlobalApplication.prefs.setString("token", "noToken")
                    startActivity(logoutIntent)
                }

                override fun onClickedUpdate() {
                    var updateIntent = Intent(context, MyPageUpdateActivity::class.java)
                    updateIntent.putExtra("userNickname", model.userNickname.value)
                    updateIntent.putExtra("userMessage", model.userMessage.value)
                    updateIntent.putExtra("userAddresses", userAddresses)
                    updateIntent.putExtra("userImage", model.userImage.value ?: defaultImage)
                    startActivity(updateIntent)
                }
            })
        }

        binding.linearLayout.setOnClickListener {
            val intent = Intent(this.requireContext(), FriendActivity::class.java)
            startActivity(intent)
        }

        logout()
//        unlink()
        return binding.root
    }

//    fun subscribeObservers() {
//        model.userFeedList.observe(viewLifecycleOwner, Observer { feedList ->
//            userFeedListAdapter.submitList(feedList)
//        })
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        subscribeObservers()

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserProfile()
            model.userNickname.value = response.body()?.userNickname ?: "nickname"
            model.userMessage.value = response.body()?.userMessage ?: "message"
            var list = response.body()?.userAddresses ?: listOf()
            model.userAddress.value = if (list.isEmpty()) "address" else list.get(0).addressName
            userAddresses = arrayListOf()
            for (address in list)
                userAddresses.add(address)
            model.userTotalDistance.value = response.body()?.userTotalDistance ?: 0.0F
            val dec = DecimalFormat("#,###");
            model.userFriendNum.value =
                if (response.body()?.userFriendNum == null) "0" else dec.format(response.body()?.userFriendNum)
            model.userImage.value = response.body()?.userImage ?: defaultImage
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserFeedList(0)
            model.userFeedList.value = response.body()?.feeds ?: listOf()
            userFeedListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserProfile()
            model.userNickname.value = response.body()?.userNickname ?: "nickname"
            model.userMessage.value = response.body()?.userMessage ?: "message"
            var list = response.body()?.userAddresses ?: listOf()
            model.userAddress.value = if (list.isEmpty()) "address" else list.get(0).addressName
            userAddresses = arrayListOf()
            for (address in list)
                userAddresses.add(address)
            model.userTotalDistance.value = response.body()?.userTotalDistance ?: 0.0F
            val dec = DecimalFormat("#,###");
            model.userFriendNum.value =
                if (response.body()?.userFriendNum == null) "0" else dec.format(response.body()?.userFriendNum)
            model.userImage.value = response.body()?.userImage ?: defaultImage
        }
    }

    fun logout() {
        // ?????? ??????????????? ?????? ????????? ?????? ????????????
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
            .requestEmail()
            .build()
        googleSignInClient = this.context?.let { GoogleSignIn.getClient(it, gso) }

        // firebaseauth??? ???????????? ?????? ???????????? get
        auth = FirebaseAuth.getInstance()
    }

    // ????????? ?????? ??????
//    fun unlink() {
//        binding.kakaoUnlink.setOnClickListener {
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Toast.makeText(context, "?????? ?????? ?????? $error", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "?????? ?????? ??????", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this.context, LoginActivity::class.java)
//                    GlobalApplication.prefs.setString("token", "noToken")
//                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
//                }
//            }
//        }
//    }
}