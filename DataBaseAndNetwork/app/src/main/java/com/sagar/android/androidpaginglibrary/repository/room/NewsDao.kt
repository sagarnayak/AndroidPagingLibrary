package com.sagar.android.androidpaginglibrary.repository.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {

    @Insert
    fun addNews(news: NewsEntity)

    @Update
    fun updateNews(news: NewsEntity)

    @Query("SELECT * from news_table")
    fun getAllNews(): DataSource.Factory<Int, NewsEntity>
}