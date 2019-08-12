package com.sagar.android.androidpaginglibrary.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase

class MainActivityViewModelProvider(val roomDataBase: RoomDataBase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainActivityViewModel(roomDataBase) as T
    }
}