package com.ssafy.darly.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.darly.R
import com.ssafy.darly.model.friend.Friend
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendDeleteDialog(private val context: AppCompatActivity) {
    private lateinit var onClickedListener: FriendDeleteDialog.ButtonClickListener
    private val dlg = BottomSheetDialog(context)   //부모 액티비티의 context 가 들어감

    interface ButtonClickListener {
        fun onClicked()
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    fun show(content: Friend) {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_friend_delete)     //다이얼로그에 사용할 xml 파일을 불러옴

        val imageView = dlg.findViewById<ImageView>(R.id.imageView)
        val nicknameTextView = dlg.findViewById<TextView>(R.id.friendTextView)
        val deleteBtn = dlg.findViewById<Button>(R.id.deleteBtn)

        if (!content.userImage.isNullOrEmpty()) {
            Glide.with(imageView!!.context)
                .load(content.userImage)
                .into(imageView)
        }

        nicknameTextView?.setText(content.userNickname)
        deleteBtn?.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                DarlyService.getDarlyService().deleteFriend(content.userId)
                onClickedListener.onClicked()
                dlg.dismiss()
            }
        }
//        val logoutView = dlg.findViewById<TextView>(R.id.logoutText)
//        logoutView?.setOnClickListener {
//            onClickedListener.onClicked()
//        }

        dlg.show()
    }
}