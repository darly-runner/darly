package com.ssafy.darly.adapter

import android.util.Log
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
//    val myCrewItemList: List<MyCrewDetails>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<MyCrewListAdapter.ViewHolder>(){
    var myCrewItemList: List<MyCrewDetails>? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val myCrewName: TextView
        val myCrewImg: ImageView

        init {
            myCrewName = itemView.findViewById(R.id.myCrewName)
            myCrewImg = itemView.findViewById(R.id.myCrewImg)
        }

        fun bind(crew: MyCrewDetails) {
            myCrewName.text = crew.crewName
            glide.load((crew.crewImage)).circleCrop().into(myCrewImg)
        }
    }
    fun submitList(crewList: List<MyCrewDetails>) {
        myCrewItemList = crewList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCrewListAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_mycrew, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCrewListAdapter.ViewHolder, position: Int) {
//        holder.myCrewName.text = myCrewItemList.get(position).crewName
//        glide.load((myCrewItemList.get(position).crewImage)).into(holder.myCrewImg)
        myCrewItemList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = 2
}