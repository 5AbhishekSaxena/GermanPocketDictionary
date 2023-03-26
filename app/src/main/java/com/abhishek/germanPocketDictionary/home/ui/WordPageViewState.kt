package com.abhishek.germanPocketDictionary.home.ui

sealed class WordPageViewState<T> {
    class Initial<T> : WordPageViewState<T>()
    class Loading<T> : WordPageViewState<T>()
    data class Loaded<T>(val words: List<T>) : WordPageViewState<T>()
    data class Error<T>(val error: Exception) : WordPageViewState<T>()
}
