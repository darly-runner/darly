package com.ssafy.darly.model

import java.io.Serializable

data class AccountLoginRes(
    var message : String,
    var statusCode : Int,
    var accessToken : String,
):Serializable