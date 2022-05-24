package com.ssafy.darly.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.databinding.ActivityLoginBinding
import com.ssafy.darly.R
import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    private lateinit var auth : FirebaseAuth
    private val RC_SIGN_IN = 99

    val darlyService = DarlyService.getDarlyService()
    var statusCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = model

        setGoogleButtonText(binding.googleLogin, "Google 계정으로 로그인")
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

    fun setGoogleButtonText(loginButton: SignInButton, buttonText: String) {
        var i = 0
        while (i < loginButton.childCount) {
            var v = loginButton.getChildAt(i)
            if (v is TextView) {
                var tv = v
                tv.setText(buttonText)
                tv.setGravity(Gravity.CENTER)
                return
            }
            i++
        }
    }

    fun googleLogin(){
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id2))
            .requestEmail()
            .build()
        var googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googleLogin.setOnClickListener {
            GlobalApplication.prefs.setString("token","noToken")
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }

    fun kakaoLogin(){
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
                        Log.d("LoginActivity", "$error , ${error.cause}")
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    GlobalApplication.prefs.setString("token","noToken")
                    val response = darlyService.accountKakao(AccountLoginReq(token.accessToken))
                    response?.body()?.accessToken?.let {GlobalApplication.prefs.setString("token",it)}
                    Log.d("LoginActivity","kakao access token : ${response.body()}")
                    statusCode = response?.body()?.statusCode ?: 200
                    Log.d("LoginActivity, Status Code","${response?.code()}")
                    checkFirstLogin()
                }
            }
        }
        binding.kakaoLogin.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("LoginActivity", "${account.idToken}")
                    val response = account.idToken?.let {
                        AccountLoginReq(it)
                    }?.let { darlyService.accountGoogle(it) }

                    // 얻어낸 access token을 Preference에 저장한다.
                    response?.body()?.accessToken?.let {GlobalApplication.prefs.setString("token",it)}
                    statusCode = response?.body()?.statusCode ?: 200
                    Log.d("LoginActivity, Status Code","${response?.body()?.statusCode}")
                }

                account.idToken?.let { firebaseAuthWithGoogle(it) }
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
                    if(user != null)
                        checkFirstLogin()
                }else{
                    Log.d("LoginActivity", "인증 실패 signInWithCredential : failire",task.exception)
                    Toast.makeText(this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT)
                }
            }
    }

    private fun checkFirstLogin(){
        if(statusCode == 200){
            toMainActivity()
        }else if(statusCode == 201){
            toUpdateActivity()
        }
    }

    private fun toUpdateActivity(){
        val intent = Intent(this, MyPageUpdateActivity::class.java)
        intent.putExtra("FirstLogin",true)
        startActivity(intent)
        finish()
    }

    // 메인 액티비티로 이동
    private fun toMainActivity() {
        //Toast.makeText(this,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}