package com.ssafy.darly.adapter.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.RecordListBinding
import com.ssafy.darly.model.record.Record

class RecordListAdapter() : RecyclerView.Adapter<RecordListAdapter.RecordListHolder>() {
    var recordList = mutableListOf<Record>()
    private val defaultImage =
        "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/darly_logo_white.png"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListHolder {
        return RecordListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecordListHolder, position: Int) {
        if (recordList[position].recordImage == null)
            recordList[position].recordImage = defaultImage;
        recordList[position].recordCaloriesString = recordList[position].recordCalories.toString()
        recordList[position].recordDistanceString = recordList[position].recordDistance.toString()
        recordList[position].recordHeartString = if (recordList[position].recordHeart != 0) recordList[position].recordHeart.toString() else "--"
        val totalSecs = recordList[position].recordTime
        recordList[position].recordTimeString = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60)
        val paceAvg: Int = recordList[position].recordPace.toInt()
        recordList[position].recordPaceString = String.format("%01d'%02d''", paceAvg / 60, paceAvg % 60)

        holder.binding.constraint.setOnClickListener {

        }
        holder.binding.viewModel = recordList[position]

        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = recordList.size

    class RecordListHolder private constructor(val binding: RecordListBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecordListHolder {
                val binding = RecordListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RecordListHolder(binding)
            }
        }
    }

}