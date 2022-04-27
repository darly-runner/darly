package com.ssafy.darly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.MyCrewDetails

class MyCrewListAdapter(
    val myCrewItemList: ArrayList<MyCrewDetails>,
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
    ): MyCrewListAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_mycrew, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCrewListAdapter.ViewHolder, position: Int) {
        holder.myCrewName.text = myCrewItemList.get(position).crewName
        glide.load((myCrewItemList.get(position).crewImage)).into(holder.myCrewImg)
    }

    override fun getItemCount(): Int = myCrewItemList.size
}