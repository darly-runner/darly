package com.ssafy.darly.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val coordinateLatitudes : ArrayList<String>, //float말고 string으로 줄수있나요?
    val coordinateLongitudes : ArrayList<String>,
    val sections : ArrayList<Section>
) : Serializable

data class Section(
    val km : Float,
    val pace : Int,
    val calories : Int
) : Serializable

@Entity
data class RecordRequestDto(
    @PrimaryKey val recordId : Int?,
    @ColumnInfo val matchId : Int?,      // 경쟁아니면 null로
    @ColumnInfo val recordDistance : Float,
    @ColumnInfo val recordPace : Int,
    @ColumnInfo val recordCalories : Int,
    @ColumnInfo val recordHeart : Int, //없으면 0으로 보내주세요
    @ColumnInfo val recordSpeed : Float,
    @ColumnInfo val recordTime : Int,   //sec
    @ColumnInfo val recordRank : Int?,   //경쟁아니면 null로 보내주세요
    @ColumnInfo var recordTitle : String?,  // 기록 제목(null로 보내면 현재 시간으로 제목을 지어준다.)
    @ColumnInfo val coordinateLatitudes : List<String>, //float말고 string으로 줄수있나요?
    @ColumnInfo val coordinateLongitudes : List<String>,
    @ColumnInfo val sections : List<Section>
)