package com.example.multithredingeducation.presentation.articleSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.multithredingeducation.databinding.FragmentArticleSearchBinding
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.presentation.BaseFragment
import com.example.multithredingeducation.domain.screens.articleSearch.ArticleSearchViewModel
import com.example.multithredingeducation.domain.screens.articleSearch.entities.ArticleSearchViewState

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
        viewModel.state.observe(viewLifecycleOwner) { setViewState(it) }

        binding.recyclerViewArticles.adapter = adapterArticles
        binding.chipNewSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.NEWEST) }
        binding.chipOldSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.OLDEST) }
        binding.chipRelevanceSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.RELEVANCE) }
        binding.toolbar.setNavigationOnClickListener { back() }
    }

    private fun setViewState(state: ArticleSearchViewState) {
        when (state.currentSort) {
            ArticleSort.NEWEST -> binding.chipNewSelect.isChecked = true
            ArticleSort.OLDEST -> binding.chipOldSelect.isChecked = true
            ArticleSort.RELEVANCE -> binding.chipRelevanceSelect.isChecked = true
        }
        when {
            state.isLoading -> {
                binding.overloadingText.isVisible = false
                binding.progressBar.isVisible = true
                binding.copyRightText.isVisible = false
                binding.recyclerViewArticles.isVisible = false
            }
            state.overloadingText != null -> {
                binding.overloadingText.isVisible = true
                binding.progressBar.isVisible = false
                binding.copyRightText.isVisible = false
                binding.recyclerViewArticles.isVisible = false
                binding.overloadingText.text = state.overloadingText
            }
            else -> {
                binding.overloadingText.isVisible = false
                binding.progressBar.isVisible = false
                binding.copyRightText.isVisible = true
                binding.recyclerViewArticles.isVisible = true
                binding.copyRightText.text = state.copyRight
                adapterArticles.updateItems(state.articles)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}