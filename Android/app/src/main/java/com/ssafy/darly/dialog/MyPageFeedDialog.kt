package com.ssafy.darly.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.model.user.Feed

class MyPageFeedDialog(private val context: AppCompatActivity) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show(content: Feed) {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_mypage_feed)     //다이얼로그에 사용할 xml 파일을 불러옴

        val imageView = dlg.findViewById<ImageView>(R.id.imageView)
        imageView.clipToOutline = true

        if (!content.feedImage.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(content.feedImage)
                .into(imageView)
        }

        dlg.show()
    }
}

