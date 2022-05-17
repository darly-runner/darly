package com.ssafy.darly.adapter.crew

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.CrewDetailActivity
import com.ssafy.darly.model.MyCrewDetails

class MyCrewListAdapter(
    val myCrewItemList: List<MyCrewDetails>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<MyCrewListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val myCrewName: TextView
        val myCrewImg: ImageView

        init {
            myCrewName = itemView.findViewById(R.id.myCrewName)
            myCrewImg = itemView.findViewById(R.id.myCrewImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.crew_mycrew, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.myCrewName.text = myCrewItemList.get(position).crewName
        if (myCrewItemList.get(position).crewName.length > 6) {
            val crewNameText = myCrewItemList.get(position).crewName.slice(IntRange(0,4)) + "..."
            holder.myCrewName.text = crewNameText
        } else {
            holder.myCrewName.text = myCrewItemList.get(position).crewName
        }
        glide.load((myCrewItemList.get(position).crewImage)).circleCrop().into(holder.myCrewImg)
        val crewId = myCrewItemList.get(position).crewId

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, CrewDetailActivity::class.java)
            intent.putExtra("crewId", crewId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = myCrewItemList.size
}