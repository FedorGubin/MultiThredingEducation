package com.example.multithredingeducation.presentation.articleSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.multithredingeducation.databinding.FragmentArticleSearchBinding
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.presentation.BaseFragment
import com.example.multithredingeducation.domain.screens.articleSearch.ArticleSearchViewModel

class ArticleSearchFragment : BaseFragment() {

    private var _binding: FragmentArticleSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleSearchViewModel by viewModels()
    private val adapterArticles = ArticleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentSort.observe(viewLifecycleOwner) { sort ->
            when (sort) {
                ArticleSort.NEWEST -> binding.chipNewSelect.isChecked = true
                ArticleSort.OLDEST -> binding.chipOldSelect.isChecked = true
                ArticleSort.RELEVANCE -> binding.chipRelevanceSelect.isChecked = true
                null -> {
                    // ignore
                }
            }
        }

        viewModel.articlesInfo.observe(viewLifecycleOwner) { articlesInfo ->
            binding.copyRightText.text = articlesInfo.copyright
            adapterArticles.updateItems(articlesInfo.articles)
        }

        binding.recyclerViewArticles.adapter = adapterArticles
        binding.chipNewSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.NEWEST) }
        binding.chipOldSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.OLDEST) }
        binding.chipRelevanceSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.RELEVANCE) }
        binding.toolbar.setNavigationOnClickListener { back() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}