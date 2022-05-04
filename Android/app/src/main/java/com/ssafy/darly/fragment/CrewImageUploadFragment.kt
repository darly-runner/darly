package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.databinding.FragmentCrewImageUploadBinding

class CrewImageUploadFragment : Fragment() {
    private lateinit var binding: FragmentCrewImageUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_image_upload, container, false)
        activity?.let {
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }
        imgPickerLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                this.type = MediaStore.Images.Media.CONTENT_TYPE
            }
        )
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment CrewImageUploadFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CrewImageUploadFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}