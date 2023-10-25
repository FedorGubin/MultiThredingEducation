package com.example.multithredingeducation.presentation.listApis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.multithredingeducation.databinding.FragmentListApisBinding
import com.example.multithredingeducation.presentation.BaseFragment

// экран с списком кнопок для выбора апи. Используется байндинг. В фрагментах
// особенность - binding задаётся на этапе onCreateView жизненного цикла и на
// onDestroyView - _binding = null.
// Это важно!!
class ListApisFragment : BaseFragment() {

    private var _binding: FragmentListApisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApisBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnArticleSearch.setOnClickListener {
            val action = ListApisFragmentDirections.actionListApisFragmentToArticleSearchFragment()
            findNavController().navigate(action)
        }
        binding.btnTopStories.setOnClickListener {
            val action = ListApisFragmentDirections.actionListApisFragmentToTopStoriesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}