package com.ssafy.darly.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityLoginBinding
import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : Activity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    private val RC_SIGN_IN = 99

    val darlyService = DarlyService.getDarlyService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        googleLogin()
    }

    override fun onStart() {
        super.onStart()

        // 구글 로그인 정보 확인
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null){
            Log.d("LoginActivity","구글로그인 되어있으므로 자동로그인 됩니다. ${account}")
            toMainActivity()
        }

//        // 카카오 로그인 정보 확인
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if (tokenInfo != null) {
//                Log.d("LoginActivity","카카오로그인 되어있으므로 자동로그인 됩니다. ${tokenInfo}")
//                toMainActivity()
//            }
//        }
    }

    fun googleLogin(){
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("233937818335-7mrg3j77q7ojjb5b0nlbptgrbrt2cnme.apps.googleusercontent.com")
            .requestEmail()
            .build()
        var googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googleLogin.setOnClickListener {
            GlobalApplication.prefs.setString("token","noToken")
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
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
                    response?.body()?.accessToken?.let { GlobalApplication.prefs.setString("token",it)}
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
                        toMainActivity()
                }else{
                    Log.d("LoginActivity", "인증 실패 signInWithCredential : failire",task.exception)
                    Toast.makeText(this,"로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
                }
            }
    }

    // 메인 액티비티로 이동
    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}