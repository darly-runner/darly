package com.ssafy.darly.model.record

import com.ssafy.darly.model.Section
import java.io.Serializable

data class RecordRequest(
    val matchId : Int?,      // 경쟁아니면 null로
    val recordDistance : Float,
    val recordPace : Int,
    val recordCalories : Int,
    val recordHeart : Int, //없으면 0으로 보내주세요
    val recordSpeed : Float,
    val recordTime : Int,   //sec
    val recordRank : Int?,   //경쟁아니면 null로 보내주세요
    var recordTitle : String?,  // 기록 제목(null로 보내면 현재 시간으로 제목을 지어준다.)
    var coordinateLatitudes : ArrayList<String>, //float말고 string으로 줄수있나요?
    var coordinateLongitudes : ArrayList<String>,
    var sections : ArrayList<Section>
) : Serializable