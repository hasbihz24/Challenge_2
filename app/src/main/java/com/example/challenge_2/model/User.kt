package com.example.challenge_2.model

import com.google.firebase.database.Exclude

data class User(
    val userId: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var phone: String = ""
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "userId" to userId,
            "username" to username,
            "password" to password,
            "email" to email,
            "phone" to phone,
        )
    }
}
