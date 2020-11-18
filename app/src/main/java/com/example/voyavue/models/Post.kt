package com.example.voyavue.models

data class Post(
        val userName: String,
        val img: String,
        val imgTitle: String,
        val imgLike: Int,
        val imgDesc: String,
        val imgTag: String,
        val location: String,
        val bestTimeToVisit: String,
        val expenseToConsider: String,
        val isVerified: Boolean,
        val isPrivate: Boolean
) {
}