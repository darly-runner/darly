package com.ssafy.darly.fragment

<<<<<<< HEAD
import android.content.Intent
=======
<<<<<<< HEAD
=======
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
=======
<<<<<<< HEAD
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
<<<<<<< HEAD
import com.ssafy.darly.activity.LoginActivity
=======
=======
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.activity.LoginActivity
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
import com.ssafy.darly.databinding.FragmentMypageBinding
import com.ssafy.darly.viewmodel.MypageViewModel

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private val model: MypageViewModel by viewModels()

<<<<<<< HEAD
    var auth : FirebaseAuth?= null
    var googleSignInClient : GoogleSignInClient?= null

=======
<<<<<<< HEAD
=======
    var auth : FirebaseAuth?= null
    var googleSignInClient : GoogleSignInClient?= null

>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mypage,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
<<<<<<< HEAD

        logout()

        return binding.root
    }
<<<<<<< HEAD
=======
=======
        logout()
        unlink()
        return binding.root
    }
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330

    fun logout(){
        // 구글 로그아웃을 위해 로그인 세션 가져오기
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
            .requestEmail()
            .build()
        googleSignInClient = this.context?.let { GoogleSignIn.getClient(it, gso) }

        // firebaseauth를 사용하기 위한 인스턴스 get
        auth = FirebaseAuth.getInstance()

        // 구글 로그아웃 버튼 클릭 시 이벤트
<<<<<<< HEAD
        binding.googleLogout.setOnClickListener {
=======
        binding.logoutBtn.setOnClickListener {
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
            UserApiClient.instance.logout { error ->
                if (error == null)
                    Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
            }

            FirebaseAuth.getInstance().signOut()
            googleSignInClient?.signOut()

            var logoutIntent = Intent(this.context, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }
    }
<<<<<<< HEAD
=======

    // 카카오 회원 탈퇴
    fun unlink(){
        binding.kakaoUnlink.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(context, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this.context, LoginActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }
    }
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
}