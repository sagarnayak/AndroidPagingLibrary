package com.sagar.android.onlynetwork.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.model.News
import com.sagar.android.onlynetwork.repository.NewsDataSourceFactory
import com.sagar.android.onlynetwork.repository.Repository

class MainActivityViewModel(repository: Repository) : ViewModel() {

    var newsPagedList: LiveData<PagedList<News>>
    var newsPagedListDataSource: LiveData<PageKeyedDataSource<Int, News>>
    var newsDataSourceFactory: NewsDataSourceFactory = NewsDataSourceFactory(repository)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(KeyWordsAndConstants.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        newsPagedListDataSource = newsDataSourceFactory.mutableLiveData

        newsPagedList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun refreshList() {
        newsDataSourceFactory.mutableLiveData.value?.invalidate()
    }
}