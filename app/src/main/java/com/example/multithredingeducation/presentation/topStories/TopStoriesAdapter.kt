package com.example.multithredingeducation.presentation.topStories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.multithredingeducation.databinding.ItemTopStoryBinding

class TopStoriesAdapter : Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {
    private val items: MutableList<String> = mutableListOf()

    class TopStoriesViewHolder(
        private val parentView: View,
        private val binding: ItemTopStoryBinding = ItemTopStoryBinding.inflate(
            LayoutInflater.from(
                parentView.context
            )
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                articleTitle.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}