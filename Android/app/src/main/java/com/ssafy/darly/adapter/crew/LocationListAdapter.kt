package com.ssafy.darly.adapter.crew

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.CreateCrewActivity
import com.ssafy.darly.model.MyAddress

class LocationListAdapter(
    val locationList: List<MyAddress>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressName: TextView
        val addressId: TextView
        init {
            addressName = itemView.findViewById(R.id.crewLocation)
            addressId = itemView.findViewById(R.id.crewLocationId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.crew_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addressName.text = locationList.get(position).addressName
//        holder.addressId.text = locationList.get(position).addressId.toString()
        val selectedId = locationList.get(position).addressId
//        Log.d("address location check", holder.addressName.text.toString())

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, CreateCrewActivity::class.java)
            intent.putExtra("addressName", holder.addressName.text.toString())
            intent.putExtra("addressId", selectedId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }
}