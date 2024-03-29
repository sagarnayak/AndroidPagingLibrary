package com.sagar.android.androidpaginglibrary.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sagar.android.androidpaginglibrary.core.KeyWordsAndConstants
import com.sagar.android.androidpaginglibrary.repository.Repository
import com.sagar.android.androidpaginglibrary.repository.room.NewsBoundaryCallBack
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase
import com.sagar.android.androidpaginglibrary.util.Event

class MainActivityViewModel(
    val roomDataBase: RoomDataBase,
    val repository: Repository
) : ViewModel() {

    var newsData: LiveData<PagedList<NewsEntity>>
    private val dataSourceFactory: DataSource.Factory<Int, NewsEntity> = roomDataBase.getNewsDao().getAllNews()
    val mediatorLiveDataHeadLineError: MediatorLiveData<Event<String>> = MediatorLiveData()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(KeyWordsAndConstants.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        newsData = LivePagedListBuilder(
            dataSourceFactory,
            config
        )
            .setBoundaryCallback(
                NewsBoundaryCallBack(
                    repository, roomDataBase
                )
            )
            .build()

        bindToRepo()
    }

    private fun bindToRepo() {
        mediatorLiveDataHeadLineError.addSource(
            repository.mutableLiveDataGetHeadlinesError
        ) { t -> mediatorLiveDataHeadLineError.postValue(t) }
    }

    fun refreshData() {
        repository.deleteAllData()
    }
}