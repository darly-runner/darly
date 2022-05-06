package com.ssafy.darly.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.darly.R

class MyPageMenuDialog(private val context: AppCompatActivity) {
    private lateinit var onClickedListener: ButtonClickListener
    private val dlg = BottomSheetDialog(context)   //부모 액티비티의 context 가 들어감

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

        val logoutView = dlg.findViewById<TextView>(R.id.logoutText)
        logoutView?.setOnClickListener {
            onClickedListener.onClicked()
        }

        dlg.show()
    }
}