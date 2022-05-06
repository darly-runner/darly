package com.ssafy.darly.adapter.crew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.R
import com.ssafy.darly.model.MyAddress

class LocationListAdapter(
    val locationList: List<MyAddress>,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(addressName: String, addressId: Long, checkbox: ImageView)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressName: TextView
        val addressId: TextView
        val checkbox: ImageView

        init {
            addressName = itemView.findViewById(R.id.crewLocation)
            addressId = itemView.findViewById(R.id.crewLocationId)
            checkbox = itemView.findViewById(R.id.locationChecked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.crew_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addressName.text = locationList.get(position).addressName
        val selectedId = locationList.get(position).addressId

        holder.itemView.setOnClickListener {
            onClickedListener.onClicked(
                addressId = selectedId.toLong(),
                addressName = holder.addressName.text.toString(),
                checkbox = holder.checkbox
            )
            holder.checkbox.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }
}