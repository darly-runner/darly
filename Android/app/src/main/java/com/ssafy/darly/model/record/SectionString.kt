package com.ssafy.darly.model.record

import java.io.Serializable


data class SectionString(
    val km: String,
    val pace: String,
    val calories: String,
    val paceInt: Int,
): Serializable