package com.sagar.android.onlynetwork.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagar.android.onlynetwork.repository.Repository

class MainActivityViewModelProvider(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainActivityViewModel(repository) as T
    }
}