package com.ssafy.darly.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.darly.R

class TargetDialog(context: Context) : BottomSheetDialog(context){
    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(target: String)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
    init {
        val view: View = layoutInflater.inflate(R.layout.dialog_target, null)
        setContentView(view)

        val distance = view.findViewById<TextView>(R.id.dialogDistance)
        val time = view.findViewById<TextView>(R.id.dialogTime)

        distance.setOnClickListener {
            onClickedListener.onClicked("거리")
            dismiss()
        }

        time.setOnClickListener {
            onClickedListener.onClicked("시간")
            dismiss()
        }
    }
}

