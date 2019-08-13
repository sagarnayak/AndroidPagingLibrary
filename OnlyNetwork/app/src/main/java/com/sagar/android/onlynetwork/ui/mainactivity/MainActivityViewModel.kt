package com.sagar.android.onlynetwork.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.model.News
import com.sagar.android.onlynetwork.repository.NewsDataSourceFactory
import com.sagar.android.onlynetwork.repository.Repository
import com.sagar.android.onlynetwork.util.Event

class MainActivityViewModel(val repository: Repository) : ViewModel() {

    var newsPagedList: LiveData<PagedList<News>>
    var newsPagedListDataSource: LiveData<PageKeyedDataSource<Int, News>>
    var newsDataSourceFactory: NewsDataSourceFactory = NewsDataSourceFactory(repository)
    val mediatorLiveDataHeadLineError: MediatorLiveData<Event<Boolean>> = MediatorLiveData()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(KeyWordsAndConstants.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        newsPagedListDataSource = newsDataSourceFactory.mutableLiveData

        newsPagedList = LivePagedListBuilder(newsDataSourceFactory, config).build()

        bindToRepo()
    }

    private fun bindToRepo() {
        mediatorLiveDataHeadLineError.addSource(
            repository.mutableLiveDataGetHeadlinesError
        ) { t -> mediatorLiveDataHeadLineError.postValue(t) }
    }

    fun refreshList() {
        newsDataSourceFactory.mutableLiveData.value?.invalidate()
    }
}