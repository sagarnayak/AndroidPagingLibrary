package com.sagar.android.onlynetwork.repository.datasource.news

import androidx.paging.PageKeyedDataSource
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.model.News
import com.sagar.android.onlynetwork.repository.Repository
import com.sagar.android.onlynetwork.util.PagingDirection

class NewsDataSource(
    private val repository: Repository
) : PageKeyedDataSource<Int, News>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        repository.getTopHeadLines(
            pageNumber = 1,
            callbackInitial = callback,
            pageSize = KeyWordsAndConstants.PAGE_SIZE
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        repository.getTopHeadLines(
            pageNumber = params.key,
            callback = callback,
            pageSize = KeyWordsAndConstants.PAGE_SIZE
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        repository.getTopHeadLines(
            pageNumber = params.key,
            callback = callback,
            pageSize = KeyWordsAndConstants.PAGE_SIZE,
            pagingDirection = PagingDirection.PREVIOUS
        )
    }
}