package com.ssafy.darly.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.LocationListAdapter
import com.ssafy.darly.databinding.FragmentSearchLocationBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchLocationFragment : DialogFragment() {
    private lateinit var binding: FragmentSearchLocationBinding
    private val model: CrewViewModel by viewModels()
    var crewLocation: String = ""
    private var prevCrewLocation: ImageView? = null

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_location, container, false)
        activity?.let {
            binding.lifecycleOwner = this
        }
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

        var addressNameData: String = ""
        var addressIdData: Long = 0

        binding.searchCrewLocation.doAfterTextChanged {
            crewLocation = it.toString()
        }

        binding.searchCrewLocation.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                CoroutineScope(Dispatchers.Main).launch {
                    val response =
                        DarlyService.getDarlyService().searchAddress(address = crewLocation)
                    model.myAddress.value = response.body()?.addresses ?: listOf()

                    val adapter = LocationListAdapter(
                        model.myAddress.value!!,
                        LayoutInflater.from(context),
                    )
                    binding.locationList.adapter = adapter
                    adapter.setOnClickedListener(object : LocationListAdapter.ButtonClickListener {
                        override fun onClicked(
                            addressName: String,
                            addressId: Long,
                            checkbox: ImageView
                        ) {
                            Log.d("nananana", addressName)
                            Log.d("idididid", addressId.toString())

                            addressNameData = addressName
                            addressIdData = addressId
                            prevCrewLocation?.visibility = View.INVISIBLE
                            prevCrewLocation = checkbox
                        }
                    })
                }
                true
            } else {
                false
            }
        }

        binding.completeLocation.setOnClickListener {
            onClickedListener.onClicked(addressId = addressIdData, addressName = addressNameData)
            dialog?.dismiss()
        }

        binding.closeDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }
}