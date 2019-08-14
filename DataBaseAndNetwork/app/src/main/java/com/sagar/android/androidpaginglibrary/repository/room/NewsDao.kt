package com.sagar.android.androidpaginglibrary.repository.room

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(news: NewsEntity)

    @Update
    fun updateNews(news: NewsEntity)

    @Query("SELECT * from news_table")
    fun getAllNews(): DataSource.Factory<Int, NewsEntity>

    @Query("SELECT COUNT(*) FROM news_table")
    fun countRecordsInNews(): Int

    @Query("DELETE FROM news_table")
    fun deleteAll()
}