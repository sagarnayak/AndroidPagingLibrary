package com.sagar.android.androidpaginglibrary.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sagar.android.androidpaginglibrary.repository.room.NewsBoundaryCallBack
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase
import com.sagar.android.logutilmaster.LogUtil

class MainActivityViewModel(roomDataBase: RoomDataBase, logUtil: LogUtil) : ViewModel() {

    var newsData: LiveData<PagedList<NewsEntity>>

    init {
        val factory: DataSource.Factory<Int, NewsEntity> = roomDataBase.getNewsDao().getAllNews()

        val pagedListBuilder: LiveData<PagedList<NewsEntity>> =
            LivePagedListBuilder(factory, 10)
                .setBoundaryCallback(NewsBoundaryCallBack(logUtil))
                .build()

        newsData = pagedListBuilder
    }
}