package com.ssafy.darly.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.MypageFeedDialogBinding
import com.ssafy.darly.model.user.Feed
import com.ssafy.darly.viewmodel.MypageViewModel

class MyPageFeedDialog(private val context: AppCompatActivity) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
//    private lateinit var binding: MypageFeedDialogBinding
//    private var model: Feed by viewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.mypage_feed_dialog,
//            null,
//            false
//        )
//        model = ViewModelProvider(this).get(Feed::class.java)
//        binding.viewModel = model
//        return binding.root
//    }

    fun show(content: Feed) {
//        binding = MypageFeedDialogBinding.inflate(context.layoutInflater)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.mypage_feed_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
//        val leftBtn = dlg.findViewById<ImageButton>(R.id.left_button)
//        val rightBtn = dlg.findViewById<ImageButton>(R.id.right_button)
        val imageView = dlg.findViewById<ImageView>(R.id.imageView)
        imageView.clipToOutline = true
//        leftBtn.setOnClickListener {
//            Log.d("button", "left")
//            dlg.dismiss()
//        }
//        rightBtn.setOnClickListener {
//            Log.d("button", "left")
//            dlg.dismiss()
//        }
        if (!content.feedImage.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(content.feedImage)
                .into(imageView)
        }
//        Log.d("dialog-show", "${model}")
//        var list = if (binding.viewModel!!.userFeedList.value == null) listOf<String>() else binding.viewModel!!.userFeedList.value
//        val feedCnt = list?.size ?: 0
//        binding.viewModel.selectedFeed.value =
//            binding.viewModel.userFeedList.value?.get(content) ?: ""
//        val feedCnt = 10

//        binding.leftButton.setOnClickListener {
//            Log.d("button", "left")
////            if (content == 0)
////                dlg.dismiss()
////            else
//            dlg.dismiss()
//        }
//        binding.rightButton.setOnClickListener {
//            Log.d("button", "right")
////            if (content == feedCnt - 1)
////                dlg.dismiss()
////            else
//            dlg.dismiss()
//        }

        dlg.show()
    }
}

