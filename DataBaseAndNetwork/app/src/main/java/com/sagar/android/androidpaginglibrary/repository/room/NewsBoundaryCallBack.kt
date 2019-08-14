package com.sagar.android.androidpaginglibrary.repository.room

import androidx.paging.PagedList
import com.sagar.android.androidpaginglibrary.repository.Repository

class NewsBoundaryCallBack(val repository: Repository, val roomDatabase: RoomDataBase) :
    PagedList.BoundaryCallback<NewsEntity>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        repository.getTopHeadLines(
            isFirstPage = true
        )
    }

    override fun onItemAtEndLoaded(itemAtEnd: NewsEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        repository.getTopHeadLines()
    }

    override fun onItemAtFrontLoaded(itemAtFront: NewsEntity) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}