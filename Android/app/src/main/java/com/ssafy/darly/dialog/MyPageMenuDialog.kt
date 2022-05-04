package com.ssafy.darly.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.darly.R
import com.ssafy.darly.model.user.Feed

class MyPageMenuDialog(private val context: AppCompatActivity) {
//    private lateinit var onClickedListener: ButtonClickListener
    private val dlg = BottomSheetDialog(context)   //부모 액티비티의 context 가 들어감

//    interface ButtonClickListener {
//        fun onClicked(target: String)
//    }

//    fun setOnClickedListener(listener: ButtonClickListener) {
//        onClickedListener = listener
//    }

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