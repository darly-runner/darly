package com.ssafy.darly.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.MatchActivity
import com.ssafy.darly.databinding.DialogLottieBinding

class MatchLottieDialog : DialogFragment() {
    private lateinit var binding: DialogLottieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogLottieBinding.inflate(inflater, container, false)
        isCancelable = false

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()

            val countDown = object : CountDownTimer(1000 * 3, 1000) {
                override fun onTick(p0: Long) {
                    // countDownInterval 마다 호출 (여기선 1000ms)
                    Log.d("매치 진행중", "${p0} 초")
                }

                override fun onFinish() {
                    // 타이머가 종료되면 호출
                    cancel()

                    val intent = Intent(context, MatchActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        } catch (ignored: IllegalStateException) {

        }
    }
}
