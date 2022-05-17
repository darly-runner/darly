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
import com.ssafy.darly.activity.MatchActivity
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.databinding.DialogCountdownBinding

class CountDownDialog (count : Long, target : String, next : String) : DialogFragment(){
    private lateinit var binding: DialogCountdownBinding
    private val target = target
    private var count = count
    private val next = next

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogCountdownBinding.inflate(inflater, container, false)
        isCancelable = false

        return binding.root
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()

            val countDown = object : CountDownTimer(1000 * count, 1000) {
                override fun onTick(p0: Long) {
                    binding.countdownText.text = count.toString()
                    count--
                }

                override fun onFinish() {
                    // 타이머가 종료되면 호출
                    cancel()
                    dismiss()

                    if(next == "running"){
                        val intent = Intent(context, RunningActivity::class.java)
                        intent.putExtra("target",target)
                        startActivity(intent)
                    }else if(next == "match"){
                        val intent = Intent(context, MatchActivity::class.java)
                        intent.putExtra("target",target)
                        startActivity(intent)
                    }
                }
            }.start()
        } catch (ignored: IllegalStateException) {

        }
    }
}