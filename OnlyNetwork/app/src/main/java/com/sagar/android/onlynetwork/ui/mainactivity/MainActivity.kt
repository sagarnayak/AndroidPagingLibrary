package com.sagar.android.onlynetwork.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.sagar.android.onlynetwork.R
import com.sagar.android.onlynetwork.databinding.ActivityMainBinding
import com.sagar.android.onlynetwork.model.News
import com.sagar.android.onlynetwork.ui.mainactivity.adapter.NewsAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val viewModelProvider: MainActivityViewModelProvider by instance()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        viewModel = ViewModelProvider(this, viewModelProvider).get(MainActivityViewModel::class.java)

        setUpList()
        listenForData()
        setUpSwipeRefreshLayout()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refreshList() }
    }

    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun listenForData() {
        viewModel.newsPagedList.observe(
            this,
            Observer<PagedList<News>> { t ->
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.submitList(t)
            }
        )
    }
}
