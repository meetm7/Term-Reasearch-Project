package com.example.voyavue.models

data class Post(
        val _id: String,
        val userName: String,
        val img: String,
        val imgTitle: String,
        val imgViews: Int,
        val imgDesc: String,
        val imgTag: String,
        val location: String,
        val bestTimeToVisit: String,
        val expenseToConsider: String,
        val isVerified: Boolean,
        val isPrivate: Boolean
) {
}