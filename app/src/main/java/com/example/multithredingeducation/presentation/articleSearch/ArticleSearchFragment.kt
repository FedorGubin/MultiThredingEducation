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

// Экран с ArticleSearch api. Наследуется от базового фрагмента, созданного нами же
class ArticleSearchFragment : BaseFragment() {

    private var _binding: FragmentArticleSearchBinding? = null
    private val binding get() = _binding!!
    // by viewModels ходит в ViewModelStore для получения сслыки на вью модельку
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
        // У фрагментов в observe вместо this передаётся viewLifecycleOwner!! Остальное тоже самое
        viewModel.state.observe(viewLifecycleOwner) { setViewState(it) }

        binding.recyclerViewArticles.adapter = adapterArticles
        binding.chipNewSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.NEWEST) }
        binding.chipOldSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.OLDEST) }
        binding.chipRelevanceSelect.setOnClickListener { viewModel.setNewSort(ArticleSort.RELEVANCE) }
        // обрати внимаение, что тут есть специальные слушатель на нажатие на навигационную кнопку
        // Посмотри что это за штука на экране
        binding.toolbar.setNavigationOnClickListener { back() }
    }

    // Функция устанавливает состояние экрана
    private fun setViewState(state: ArticleSearchViewState) {
        // устанавливает текущую выбранную сортировку
        when (state.currentSort) {
            ArticleSort.NEWEST -> binding.chipNewSelect.isChecked = true
            ArticleSort.OLDEST -> binding.chipOldSelect.isChecked = true
            ArticleSort.RELEVANCE -> binding.chipRelevanceSelect.isChecked = true
        }
        // далее смотрим. Если идёт viewState - показывает, что идёт загрузка,
        // 1. то скрываем все элементы на экране, кроме прогресс бара
        // 2. если есть какой то текст (содержит текст ошибок), то скрывает всё, кроме этого текста
        // 3. иначе показываем контент (т.е. список статей)
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