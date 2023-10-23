package com.example.multithredingeducation.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment: Fragment() {
    protected open fun back() {
        findNavController().popBackStack()
    }
}