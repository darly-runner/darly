package com.ssafy.darly.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.darly.R

class CrewCreateDialog(context: AppCompatActivity) {
    private lateinit var onClickedListener: ButtonClickListener
    private val dlg = BottomSheetDialog(context)

    interface ButtonClickListener {
        fun onClickedUploadFeed()
        fun onClickedCreateMatch()
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    fun show() {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_crew_create)

        val uploadFeedView = dlg.findViewById<TextView>(R.id.uploadFeed)
        uploadFeedView?.setOnClickListener {
            onClickedListener.onClickedUploadFeed()
            dlg.dismiss()
        }

        val createMatchView = dlg.findViewById<TextView>(R.id.createMatch)
        createMatchView?.setOnClickListener {
            onClickedListener.onClickedCreateMatch()
            dlg.dismiss()
        }
        dlg.show()
    }
}