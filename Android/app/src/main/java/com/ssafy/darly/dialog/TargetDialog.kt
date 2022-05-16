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

        val distance1 = view.findViewById<TextView>(R.id.distance1)
        val distance3 = view.findViewById<TextView>(R.id.distance3)
        val distance5 = view.findViewById<TextView>(R.id.distance5)
        val distance10 = view.findViewById<TextView>(R.id.distance10)
        val distance15 = view.findViewById<TextView>(R.id.distance15)
        val distance20 = view.findViewById<TextView>(R.id.distance20)
        val distance42 = view.findViewById<TextView>(R.id.distance42)
        val reset = view.findViewById<TextView>(R.id.reset)

        distance1.setOnClickListener {
            onClickedListener.onClicked("1.00")
            dismiss()
        }
        distance3.setOnClickListener {
            onClickedListener.onClicked("3.00")
            dismiss()
        }
        distance5.setOnClickListener {
            onClickedListener.onClicked("5.00")
            dismiss()
        }
        distance10.setOnClickListener {
            onClickedListener.onClicked("10.00")
            dismiss()
        }
        distance15.setOnClickListener {
            onClickedListener.onClicked("15.00")
            dismiss()
        }
        distance20.setOnClickListener {
            onClickedListener.onClicked("20.00")
            dismiss()
        }
        distance42.setOnClickListener {
            onClickedListener.onClicked("42.00")
            dismiss()
        }
        reset.setOnClickListener {
            onClickedListener.onClicked("5.00")
            dismiss()
        }
    }
}

