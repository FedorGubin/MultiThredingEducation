package com.example.multithredingeducation.presentation.articleSearch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.multithredingeducation.databinding.ItemArticleBinding
import com.example.multithredingeducation.domain.entities.Article

// Самый простой адаптер. Надеюсь тут всё понятно
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val items: MutableList<Article> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Article>) {
        items.clear()
        items.addAll(newItems)
        // Оповещает, что ВСЕ данные в списке изменились
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class ArticleViewHolder(
        private val parentView: View,
        private val binding: ItemArticleBinding = ItemArticleBinding.inflate(LayoutInflater.from(parentView.context))
    ) : ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                articleText.text = item.snippet
                urlArticle.text = item.webLink
            }
        }
    }
}