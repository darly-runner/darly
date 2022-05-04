package com.ssafy.darly.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.ssafy.darly.R
import com.ssafy.darly.activity.LoginActivity
import com.ssafy.darly.util.GlobalApplication

class MyPageMenuDialog(private val context: AppCompatActivity) {
    private lateinit var onClickedListener: ButtonClickListener
    private val dlg = BottomSheetDialog(context)   //부모 액티비티의 context 가 들어감
    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null

    interface ButtonClickListener {
        fun onClicked()
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    fun show() {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_mypage_menu)     //다이얼로그에 사용할 xml 파일을 불러옴

//        val imageView = dlg.findViewById<ImageView>(R.id.imageView)
//        imageView.clipToOutline = true
//
//        if (!content.feedImage.isNullOrEmpty()) {
//            Glide.with(imageView.context)
//                .load(content.feedImage)
//                .into(imageView)
//        }
        val logoutView = dlg.findViewById<TextView>(R.id.logoutText)
        logoutView?.setOnClickListener {
            onClickedListener.onClicked()
        }

        dlg.show()
    }

//    init {
//        val view: View = layoutInflater.inflate(R.layout.dialog_mypage_menu, null)
//        setContentView(view)
//        val distance = view.findViewById<TextView>(R.id.dialogDistance)
//        val time = view.findViewById<TextView>(R.id.dialogTime)
//
//        distance.setOnClickListener {
//            onClickedListener.onClicked("거리")
//            dismiss()
//        }
//
//        time.setOnClickListener {
//            onClickedListener.onClicked("시간")
//            dismiss()
//        }
//    }
}