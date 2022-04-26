package com.ssafy.darly.model

import java.io.Serializable

data class GoogleAccountResponse(
    var message : String,
    var statusCode : Int,
    var accessToken : String,
):Serializable