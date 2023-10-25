package com.example.multithredingeducation.presentation.topStories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.multithredingeducation.databinding.FragmentTopStoriesBinding
import com.example.multithredingeducation.presentation.BaseFragment

class TopStoriesFragment : BaseFragment() {
    private var _binding: FragmentTopStoriesBinding? = null
    private val binding: FragmentTopStoriesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopStoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topStoriesToolBar.setNavigationOnClickListener {
            back()
        }
    }
}