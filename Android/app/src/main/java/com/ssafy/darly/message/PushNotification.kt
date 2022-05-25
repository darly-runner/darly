package com.ssafy.darly.message

data class PushNotification(
    val data: NotificationData,
    val to: String
)