package com.ssafy.darly.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.kakao.util.helper.Utility
import com.ssafy.darly.R
import com.ssafy.darly.adapter.MainViewPagerAdapter
import com.ssafy.darly.dao.AppDatabase
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.databinding.ActivityMainBinding
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.model.RecordRequestDto
import com.ssafy.darly.service.DarlyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { MainViewPagerAdapter(supportFragmentManager) }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = GlobalApplication.prefs.getString("token","noToken")
        Log.d("MainActivity", "$token")

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        checkPermission()
        binding.viewPager.adapter = MainActivity@adapter

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_white))
                binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_white))

                when(p0){
                    0 -> binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_color))
                    1 -> binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_color))
                }
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            if(!GlobalApplication.network.getNetworkConnected()){
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "recordDB"
                ).build()
                // 확인 메세지
                Log.d("DB Test", "${db.recordDao().getAll()}")
                val list = db.recordDao().getAll()
                for(i in list){
                    val record = GlobalApplication.network.dtoToRecord(i)
                    DarlyService.getDarlyService().postRecord(record)
                }
                db.recordDao().deleteAll()
            }
        }
    }

    fun checkPermission(){
        val multiplePermissionsCode = 100
        // 필요한 권한(퍼미션)들
        val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }
}