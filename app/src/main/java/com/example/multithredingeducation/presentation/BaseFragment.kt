package com.example.multithredingeducation.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

// Базовый фрагмент проекта. Все фрагменты в проекте должны наследоваться от него
// Используется абстрактный класс
abstract class BaseFragment: Fragment() {

    // Функция, позволяющая сделать назад по навигации
    // protected - доступна только для тех, кто расширил этот класс (унаследовался от него)
    // open - доступен для переопоределения (override)
    protected open fun back() {
        findNavController().popBackStack()
    }
}