package com.ssafy.darly.model.record

data class Record(
    val recordId: Long,
    val recordTitle: String,
    val recordDate: String,
    val recordDistance: Float,
    val recordTime: Int, //sec
    val recordPace: Float,
    val recordHeart: Int,
    val recordCalories: Int,
    var recordImage: String?,
    val coordinateLatitudes: MutableList<String>,
    val coordinateLongitudes: MutableList<String>,

    var recordDistanceString: String,
    var recordTimeString: String,
    var recordPaceString: String,
    var recordHeartString: String,
    var recordCaloriesString: String,
)