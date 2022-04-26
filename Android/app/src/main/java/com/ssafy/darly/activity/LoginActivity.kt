package com.ssafy.darly.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
<<<<<<< HEAD
import android.widget.ImageButton
=======
<<<<<<< HEAD
=======
import android.widget.ImageButton
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
<<<<<<< HEAD
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityLoginBinding
=======
<<<<<<< HEAD
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityLoginBinding
import com.ssafy.darly.service.DarlyService
=======
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityLoginBinding
import com.ssafy.darly.model.GoogleAccountRequest
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.util.PreferenceUtil
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
import com.ssafy.darly.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    private lateinit var auth : FirebaseAuth
    private val RC_SIGN_IN = 99

<<<<<<< HEAD
=======
    val darlyService = DarlyService.getDarlyService()

>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = model

<<<<<<< HEAD
        googleLogin()
        kakaoLogin()
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null){
            Log.d("LoginActivity","이미 로그인 되어있는 계정입니다.")
            toMainActivity()
        }
    }

    fun googleLogin(){
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
=======
<<<<<<< HEAD
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
=======
        googleLogin()
        kakaoLogin()
    }

    override fun onStart() {
        super.onStart()

        // 구글 로그인 정보 확인
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null){
            Log.d("LoginActivity","구글로그인 되어있으므로 자동로그인 됩니다. ${account}")
            toMainActivity()
        }

        // 카카오 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (tokenInfo != null) {
                Log.d("LoginActivity","카카오로그인 되어있으므로 자동로그인 됩니다. ${tokenInfo}")
                toMainActivity()
            }
        }
    }

    fun googleLogin(){
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
            .requestEmail()
            .build()
        var googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googleLogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }

<<<<<<< HEAD
    fun kakaoLogin(){
        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
=======
<<<<<<< HEAD
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null){
            Log.d("LoginActivity","이미 로그인 되어있는 계정입니다.")
            toMainActivity(auth.currentUser)
=======
    fun kakaoLogin(){
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
<<<<<<< HEAD
=======
                Log.d("LoginActivity","kakao access token : ${token}")
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                toMainActivity()
            }
        }
        binding.kakaoLogin.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
<<<<<<< HEAD
=======
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
<<<<<<< HEAD
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
                Log.d("idToken", "${account.idToken}")
=======
                val account = task.getResult(ApiException::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    val response = account.idToken?.let {
                        GoogleAccountRequest(it)
                    }?.let { darlyService.accountGoogle(it) }

                    // 얻어낸 access token을 Preference에 저장한다.
                    response?.body()?.accessToken?.let {GlobalApplication.prefs.setString("token",it)}

                    Log.d("LoginActivity", "retrofit Test ${response?.body()}")
                }

                account.idToken?.let { firebaseAuthWithGoogle(it) }
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    Log.d("인증 성공", user.toString())
<<<<<<< HEAD
                    if(user != null)
                        toMainActivity()
=======
<<<<<<< HEAD

                    val response = DarlyService.getDarlyService().accountGoogle(idToken)
                    if(response.isSuccessful){
                        Log.d("뭐임 ㅡㅡ" , "${response.body()}")
                    }else{
                        Log.d("안되넹..","ㅇㅇ")
                    }

                    toMainActivity(auth.currentUser)
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
                }else{
                    Log.d("인증 실패", "signInWithCredential : failire",task.exception)
=======
                    if(user != null)
                        toMainActivity()
                }else{
                    Log.d("LoginActivity", "인증 실패 signInWithCredential : failire",task.exception)
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
                    Toast.makeText(this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT)
                }
            }
    }

    // 메인 액티비티로 이동
<<<<<<< HEAD
    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
=======
<<<<<<< HEAD
    private fun toMainActivity(user: FirebaseUser?) {
        if(user !=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
=======
    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
    }
}