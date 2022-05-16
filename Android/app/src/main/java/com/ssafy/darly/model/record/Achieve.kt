package com.ssafy.darly.model.record

import java.io.Serializable

data class Achieve(
    val badgeName: String,
    val badgeImage: String?,
    val badgeCondition: String,
): Serializable