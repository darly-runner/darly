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
import androidx.room.Room
import com.ssafy.darly.R
import com.ssafy.darly.activity.MainActivity
import com.ssafy.darly.dao.AppDatabase
import com.ssafy.darly.databinding.FragmentRecordBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)

        // 동기화 되지 않은 데이터 개수
        CoroutineScope(Dispatchers.IO).launch {
            getRecordSize()
        }

        // 기록 동기화 버튼 클릭시 Dialog
        binding.recordCount.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("기록을 동기화하시겠습니까?")
            builder.setIcon(R.mipmap.ic_launcher)

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if(GlobalApplication.network.getNetworkConnected()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            sendRecord()
                        }
                    }
                    // 네트워크 연걸이 불가능한경우 내장DB에 저장한다.
                    else{
                        Toast.makeText(context,"네트워크가 필요합니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            builder.setPositiveButton("기록 동기화", listener)
            builder.setNegativeButton("취소", null)
            builder.show()

        }
        return binding.root
    }

    private fun sendRecord(){
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                requireContext(),
                AppDatabase::class.java,
                "recordDB"
            ).build()
            Log.d("DB Test", "${db.recordDao().getAll()}")
            val list = db.recordDao().getAll()
            for (i in list) {
                val record = GlobalApplication.network.dtoToRecord(i)
                DarlyService.getDarlyService().postRecord(record)
            }
            db.recordDao().deleteAll()
            getRecordSize()
        }
    }

    private fun getRecordSize(){
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "recordDB"
        ).build()
        val list = db.recordDao().getAll()
        Log.d("DB Test", "${db.recordDao().getAll()}")
        CoroutineScope(Dispatchers.Main).launch {
            binding.recordCount.text = list.size.toString()
        }
    }
}