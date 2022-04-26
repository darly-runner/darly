package com.ssafy.darly.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
        KakaoSdk.init(this, "3a20416392ebf79f9356a5ebfd94116a")
    }
}