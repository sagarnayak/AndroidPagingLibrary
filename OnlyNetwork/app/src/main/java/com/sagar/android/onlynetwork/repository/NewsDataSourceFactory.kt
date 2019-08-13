package com.sagar.android.onlynetwork.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.sagar.android.onlynetwork.model.News

class NewsDataSourceFactory(
    private val repository: Repository
) : DataSource.Factory<Int, News>() {

    val mutableLiveData = MutableLiveData<PageKeyedDataSource<Int, News>>()

    override fun create(): DataSource<Int, News> {
        val dataSource = NewsDataSource(repository)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}
