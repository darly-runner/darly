package com.ssafy.darly.fragment

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.activity.LoginActivity
import com.ssafy.darly.adapter.user.UserFeedListAdapter
import com.ssafy.darly.databinding.FragmentMypageBinding
import com.ssafy.darly.model.user.Feed
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
    private val model: MypageViewModel by viewModels()

//    var auth: FirebaseAuth? = null
//    var googleSignInClient: GoogleSignInClient? = null

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
//        logout()
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
            var list = response.body()?.userAddress ?: listOf()
            model.userAddress.value = if (list.isEmpty()) "address" else list.get(0)
            model.userTotalDistance.value = response.body()?.userTotalDistance ?: 0.0F
            val dec = DecimalFormat("#,###");
            model.userFriendNum.value =
                if (response.body()?.userFriendNum == null) "0" else dec.format(response.body()?.userFriendNum)
            model.userImage.value = response.body()?.userImage ?: ""
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserFeedList(0)
            model.userFeedList.value = response.body()?.feeds ?: listOf()
            Log.d("response", "${response}")
            Log.d("response body", "${response.body()}")
            userFeedListAdapter.notifyDataSetChanged()
        }
    }

    //    fun logout(){
//        // 구글 로그아웃을 위해 로그인 세션 가져오기
//        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id2))
//            .requestEmail()
//            .build()
//        googleSignInClient = this.context?.let { GoogleSignIn.getClient(it, gso) }
//
//        // firebaseauth를 사용하기 위한 인스턴스 get
//        auth = FirebaseAuth.getInstance()
//
//        // 구글 로그아웃 버튼 클릭 시 이벤트
//        binding.logoutBtn.setOnClickListener {
//            UserApiClient.instance.logout { error ->
//                if (error == null)
//                    Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
//            }
//
//            FirebaseAuth.getInstance().signOut()
//            googleSignInClient?.signOut()
//
//            var logoutIntent = Intent(this.context, LoginActivity::class.java)
//            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//
//            GlobalApplication.prefs.setString("token","noToken")
//            startActivity(logoutIntent)
//        }
//    }
//
//    // 카카오 회원 탈퇴
//    fun unlink(){
//        binding.kakaoUnlink.setOnClickListener {
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Toast.makeText(context, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
//                }else {
//                    Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this.context, LoginActivity::class.java)
//                    GlobalApplication.prefs.setString("token","noToken")
//                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
//                }
//            }
//        }
//    }
}