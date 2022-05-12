package com.ssafy.darly.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ssafy.darly.R
import com.ssafy.darly.activity.MainActivity
import com.ssafy.darly.dao.AppDatabase
import com.ssafy.darly.databinding.FragmentPauseBinding
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.model.RecordRequestDto
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PauseFragment : Fragment() {
    private lateinit var binding: FragmentPauseBinding
    private lateinit var model: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPauseBinding.inflate(inflater, container, false)
        activity.let {
            model = ViewModelProvider(it!!, ViewModelProvider.NewInstanceFactory()).get(RunningViewModel::class.java)
        }
        binding.viewModel = model
        binding.lifecycleOwner = this

        binding.pauseButton.setOnClickListener {
            if(model.isPause.value == true) {
                // 다시 시작
                model.isPause.value = false
                binding.pauseButton.setImageResource(R.drawable.ic_pause)
            }else{
                // 일시 정지
                model.isPause.value = true
                binding.pauseButton.setImageResource(R.drawable.ic_play)
            }
        }

        binding.stopButton.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("기록을 저장하시겠습니까?")
            builder.setIcon(R.mipmap.ic_launcher)

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    CoroutineScope(Dispatchers.IO).launch {
                        // 네트워크 연결이 가능할때는 서버DB에 저장한다.
                        if(GlobalApplication.network.getNetworkConnected()) {
                            DarlyService.getDarlyService().postRecord(model.record())

                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                        }
                        // 네트워크 연걸이 불가능한경우 내장DB에 저장한다.
                        else{
                            saveLocalDb(model.record())

                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
            builder.setPositiveButton("기록 저장", listener)
            builder.setNegativeButton("취소", null)
            builder.show()
        }
        return binding.root
    }

    fun saveLocalDb(record : RecordRequest){
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "recordDB"
        ).build()

        // DB에 넣는다.
        db.recordDao().insertRecord(
            GlobalApplication.network.recordToDto(record)
        )
    }
}