package com.ssafy.darly.model

<<<<<<< HEAD
data class GoogleAccountResponse(
    var accessToken : String,
)
=======
import java.io.Serializable

data class GoogleAccountResponse(
    var message : String,
    var statusCode : Int,
    var accessToken : String,
):Serializable
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
