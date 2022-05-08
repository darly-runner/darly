package com.ssafy.darly.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.darly.databinding.MypageUpdateListBinding
import com.ssafy.darly.model.address.Address

class MyPageListAdapter : RecyclerView.Adapter<MyPageListAdapter.MyPageListHolder>() {
    var addressList = mutableListOf<Address>()
    private lateinit var onClickListener: ButtonClickListener

    interface ButtonClickListener {
        fun deleteAddressAtPosition(addressList: List<Address>)
    }

    fun setButtonClickListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageListHolder {
        return MyPageListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyPageListHolder, position: Int) {
        holder.binding.viewModel = addressList[position]
        holder.binding.deleteBtn.setOnClickListener {
            deleteItem(holder)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = addressList.size

    fun deleteItem(holder: MyPageListHolder) {
        val newPosition: Int = holder.getAdapterPosition()
        addressList.removeAt(newPosition)
        onClickListener.deleteAddressAtPosition(addressList)
        notifyItemRemoved(newPosition)
        notifyItemRangeChanged(newPosition, addressList.size)
    }

    class MyPageListHolder private constructor(val binding: MypageUpdateListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): MyPageListHolder {
                val binding = MypageUpdateListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MyPageListHolder(binding)
            }
        }
    }
}