package com.example.voyavue.models

data class Post(
        val userName: String,
        val img: String,
        val imageTitle: String,
        val imageLike: Int,
        val imageDesc: String,
        val imgTags: String,
        val location: String,
        val bestTimeToVisit: String,
        val expenseToConsider: String,
        val isVerified: Boolean,
        val isPrivate: Boolean
) {
}