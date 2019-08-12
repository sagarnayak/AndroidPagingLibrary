package com.sagar.android.androidpaginglibrary.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase

class MainActivityViewModel(roomDataBase: RoomDataBase) : ViewModel() {

    var newsData: LiveData<PagedList<NewsEntity>>

    init {
        val factory: DataSource.Factory<Int, NewsEntity> = roomDataBase.getNewsDao().getAllNews()

        val pagedListBuilder: LiveData<PagedList<NewsEntity>> =
            LivePagedListBuilder(factory, 10).build()

        newsData = pagedListBuilder
    }
}