package com.sagar.android.androidpaginglibrary.repository.room

import androidx.paging.PagedList
import com.sagar.android.logutilmaster.LogUtil

class NewsBoundaryCallBack(val logUtil: LogUtil) : PagedList.BoundaryCallback<NewsEntity>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: NewsEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        logUtil.logV("item at end loaded")
    }

    override fun onItemAtFrontLoaded(itemAtFront: NewsEntity) {
        super.onItemAtFrontLoaded(itemAtFront)
        logUtil.logV("item at front loaded")
    }
}