package com.example.voyavue.models

data class User(
        val firstName:String,
        val lastName: String,
        val userName: String,
        val email: String,
        val contactNumber: String,
        val dob: String,
        val sex:String,
        val bio:String,
        val isAdmin: Boolean
)