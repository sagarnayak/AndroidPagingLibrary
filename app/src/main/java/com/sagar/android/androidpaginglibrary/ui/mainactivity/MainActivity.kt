package com.sagar.android.androidpaginglibrary.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagar.android.androidpaginglibrary.R
import com.sagar.android.androidpaginglibrary.databinding.ActivityMainBinding
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity
import com.sagar.android.androidpaginglibrary.ui.mainactivity.adapter.NewsAdapter
import com.sagar.android.logutilmaster.LogUtil
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    private lateinit var binding: ActivityMainBinding
    private val viewModelProvider: MainActivityViewModelProvider by instance()
    private val logUtil: LogUtil by instance()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelProvider).get(MainActivityViewModel::class.java)

        adapter = NewsAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        observeForData()
    }

    private fun observeForData() {
        viewModel.newsData.observe(
            this,
            Observer<PagedList<NewsEntity>> { t ->
                logUtil.logV("got few data ${t?.size}")
                adapter.submitList(t)
            }
        )
    }
}
