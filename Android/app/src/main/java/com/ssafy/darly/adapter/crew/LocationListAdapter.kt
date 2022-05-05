package com.ssafy.darly.adapter.crew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.MyAddress

class LocationListAdapter(
    val locationList: List<MyAddress>,
    val inflater: LayoutInflater,
): RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val addressName: TextView
        init {
            addressName = itemView.findViewById(R.id.crewLocation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.crew_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addressName.text = locationList.get(position).addressName
    }

    override fun getItemCount(): Int {
        return locationList.size
    }
}