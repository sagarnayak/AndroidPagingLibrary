package com.sagar.android.androidpaginglibrary.ui.mainactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sagar.android.androidpaginglibrary.databinding.NewsListItemBinding
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity

class NewsAdapter : PagedListAdapter<NewsEntity, NewsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return true
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val newsListItemBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(newsListItemBinding.root) {

        fun bind(newsEntity: NewsEntity) {
            newsListItemBinding.title.text = newsEntity.title
        }
    }
}