package com.abhishek.germanPocketDictionary.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word

class WordsViewModel(
    private val wordsRepository: WordsRepository,
    private val fragmentType: String,
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>(emptyList())
    val words: LiveData<List<Word>> = _words

    fun fetchWordsByCategory() {
        val words = wordsRepository.getWordsByCategory(fragmentType)
        _words.value = words
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val wordsRepository: WordsRepository,
        private val fragmentType: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WordsViewModel(wordsRepository, fragmentType) as T
        }
    }
}