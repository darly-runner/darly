package com.ssafy.darly.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.MainViewPagerAdapter
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.databinding.ActivityMainBinding

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

//        CoroutineScope(Dispatchers.IO).launch {
//            if(!GlobalApplication.network.getNetworkConnected()){
//                val db = Room.databaseBuilder(
//                    applicationContext,
//                    AppDatabase::class.java,
//                    "recordDB"
//                ).build()
//                // ?????? ?????????
//                Log.d("DB Test", "${db.recordDao().getAll()}")
//                val list = db.recordDao().getAll()
//                for(i in list){
//                    val record = GlobalApplication.network.dtoToRecord(i)
//                    DarlyService.getDarlyService().postRecord(record)
//                }
//                db.recordDao().deleteAll()
//            }
//        }
    }

    fun checkPermission(){
        val multiplePermissionsCode = 100
        // ????????? ??????(?????????)???
        val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        //?????????????????? ?????? ???????????? ?????? ??????(?????????)??? ????????? ????????? ?????? ?????????
        var rejectedPermissionList = ArrayList<String>()

        //????????? ??????????????? ????????? ??????????????? ?????? ????????? ???????????? ??????
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //?????? ????????? ????????? rejectedPermissionList??? ??????
                rejectedPermissionList.add(permission)
            }
        }
        //????????? ???????????? ?????????...
        if(rejectedPermissionList.isNotEmpty()){
            //?????? ??????!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }
}