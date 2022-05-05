package com.ssafy.darly.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
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

//class SearchLocationFragment : Fragment() {
//    private lateinit var binding: FragmentSearchLocationBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_location, container, false)
//        activity?.let {
//            binding.lifecycleOwner = this
//        }
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//}

//class SearchLocationFragment : DialogFragment() {
////    private var _binding : DialogLayoutBinding? = null
//    private lateinit var binding: FragmentSearchLocationBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_location, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val windowManager =
////        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
////        val display = windowManager.defaultDisplay
////        val size = Point()
////        display.getSize(size)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//    }
//}


//class SearchLocationFragment(private val layoutResId: Int) : DialogFragment() {
//
//    lateinit var dialogView: View
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        dialogView = inflater.inflate(layoutResId, container, false)
//        return dialogView
//    }
//
//    override fun onResume() {
//        super.onResume()
//        // full Screen code
//        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//    }
//}

class SearchLocationFragment : DialogFragment() {
    private lateinit var binding: FragmentSearchLocationBinding
    private val model: CrewViewModel by viewModels()
    var crewLocation: String = ""

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_location, container, false)
        activity?.let {
            binding.lifecycleOwner = this
//            binding.viewModel = model
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchCrewLocation.doAfterTextChanged {
            crewLocation = it.toString()
        }

        binding.searchCrewLocation.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)) {
                CoroutineScope(Dispatchers.Main).launch {
                    val response = DarlyService.getDarlyService().searchAddress(address = crewLocation)
                    model.myAddress.value = response.body()?.addresses ?: listOf()

                    val adapter = LocationListAdapter(
                        model.myAddress.value!!,
                        LayoutInflater.from(context),
                    )
                    binding.locationList.adapter = adapter


//                    val locationList = model.MyAddress.value
                    Log.d("Search Location", "${response}")
                }
                true
            } else {
                false
            }
        }

        binding.closeDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }
}