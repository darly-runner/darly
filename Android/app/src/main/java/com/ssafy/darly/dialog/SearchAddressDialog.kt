package com.ssafy.darly.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.address.AddressListAdapter
import com.ssafy.darly.databinding.DialogSearchAddressBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.AddressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAddressDialog : DialogFragment() {
    private lateinit var binding: DialogSearchAddressBinding
    private val model: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomFullDialog)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_search_address, container, false)
        activity?.let {
            binding.lifecycleOwner = this
        }
        binding.viewModel = model

        return binding.root
    }

    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(addressName: String, addressId: Long)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var addressListFinishAdapter = AddressListAdapter()
        addressListFinishAdapter.setOnClickedListener(object : AddressListAdapter.ButtonClickListener {
            override fun onClicked(addressName: String, addressId: Long) {
                onClickedListener.onClicked(addressName, addressId)
                dismiss()
            }
        })

        binding.locationList.adapter = addressListFinishAdapter
        binding.locationList.layoutManager = LinearLayoutManager(context)

        binding.searchCrewLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.trim()?.length == 0) {
                    model.addresses.value = listOf()
                    addressListFinishAdapter.notifyDataSetChanged()
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        val response = DarlyService.getDarlyService().searchAddresses(p0.toString())
                        model.addresses.value = response.body()?.addresses ?: listOf()
                        addressListFinishAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.closeDialog.setOnClickListener {
            dismiss()
        }
    }
}