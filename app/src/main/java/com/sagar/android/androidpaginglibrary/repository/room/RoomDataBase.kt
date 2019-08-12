package com.sagar.android.androidpaginglibrary.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}