package com.ssafy.darly.adapter.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.AddressListBinding
import com.ssafy.darly.model.address.Address

class AddressListAdapter(
) : RecyclerView.Adapter<AddressListAdapter.LocationListFinishHolder>() {
    var addressList = mutableListOf<Address>()
    private lateinit var onClickListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(addressName: String, addressId: Long)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListFinishHolder {
        return LocationListFinishHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocationListFinishHolder, position: Int) {
        val item = addressList[position]
        holder.binding.viewModel = item
        holder.binding.locationName.setOnClickListener {
            onClickListener.onClicked(addressId = item.addressId, addressName = item.addressName)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = addressList.size

    class LocationListFinishHolder private constructor(val binding: AddressListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): LocationListFinishHolder {
                val binding = AddressListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LocationListFinishHolder(binding)
            }
        }
    }
}