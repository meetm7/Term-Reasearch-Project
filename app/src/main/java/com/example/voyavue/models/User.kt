package com.example.voyavue.models

import java.util.*

data class User(
        val firstName:String,
        val lastName: String,
        val userName: String,
        val email: String,
        val contactNumber: String,
        val dob: Date,
        val sex:String,
        val bio:String
)