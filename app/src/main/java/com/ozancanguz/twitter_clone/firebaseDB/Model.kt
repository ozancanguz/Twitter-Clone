package com.ozancanguz.twitter_clone.firebaseDB

data class User(
    val email: String? = "",
    val username: String? = "",
    val imageUrl: String? = "",
    val followHashtags: ArrayList<String>? = arrayListOf(),
    val followUsers: ArrayList<String>? = arrayListOf()
)