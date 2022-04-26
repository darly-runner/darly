package com.ssafy.darly.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
<<<<<<< HEAD
    override fun onCreate() {
=======
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
>>>>>>> 4c71ca21b94d25216676b343d90a6f9513ea0330
        super.onCreate()
        KakaoSdk.init(this, "3a20416392ebf79f9356a5ebfd94116a")
    }
}