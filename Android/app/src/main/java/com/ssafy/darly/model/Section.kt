package com.ssafy.darly.model

import java.io.Serializable

data class Section(
    val km : Float,
    val pace : Int,
    val calories : Int
) : Serializable