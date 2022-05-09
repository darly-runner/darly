package com.ssafy.darly.util

import android.util.Log
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DistanceHelper(
    var bef_lat : Double,
    var bef_long : Double,
    var cur_lat : Double,
    var cur_long : Double
){
    fun getDistance() : Double{
        var theta = bef_long - cur_long

        var dist = sin(deg2rad(bef_lat)) * sin(deg2rad(cur_lat)) + cos(deg2rad(bef_lat)) *
                cos(deg2rad(cur_lat)) * cos(deg2rad(theta))

        dist = acos(dist)
        dist = rad2deg(dist)

        dist *= 60 * 1.1515
        dist *= 1.60934;    // 단위 mile 에서 km 변환.

        //dist = dist * 1000.0      // 단위  km 에서 m 로 변환

        Log.d("DistanceHelper", "$dist")

        return dist // 단위 m
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    fun deg2rad(deg : Double) : Double = deg * Math.PI / 180.0

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    fun rad2deg(rad : Double) : Double = rad * 180.0 / Math.PI
}