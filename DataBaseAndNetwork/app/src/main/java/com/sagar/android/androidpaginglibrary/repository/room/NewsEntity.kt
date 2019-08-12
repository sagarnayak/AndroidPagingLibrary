package com.sagar.android.androidpaginglibrary.repository.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    @NonNull
    val title: String,
    val author: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)