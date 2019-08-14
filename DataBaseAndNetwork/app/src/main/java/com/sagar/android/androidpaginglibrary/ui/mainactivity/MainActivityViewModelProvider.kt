package com.sagar.android.androidpaginglibrary.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagar.android.androidpaginglibrary.repository.Repository
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase
import com.sagar.android.logutilmaster.LogUtil

class MainActivityViewModelProvider(
    val roomDataBase: RoomDataBase,
    val repository: Repository,
    val logUtil: LogUtil
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainActivityViewModel(roomDataBase, repository, logUtil) as T
    }
}