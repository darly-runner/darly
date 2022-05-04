package com.ssafy.darly.model.user

data class UserFeedGetRes(
    var size: Int,
    var totalPages: Int,
    var totalFeeds: Int,
    var currentPage: Int,
    var numberOfFeeds: Int,
    var feeds: List<String>,
)
