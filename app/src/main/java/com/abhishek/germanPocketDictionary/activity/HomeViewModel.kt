package com.abhishek.germanPocketDictionary.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word

class HomeViewModel(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _allWords = MutableLiveData<List<Word>>()
    val allWords: LiveData<List<Word>> = _allWords

    fun getWords() {
        _allWords.value = wordsRepository.getWords()
    }

    @Suppress("UNCHECKED_CAST")
    class HomeViewModelFactory(
        private val wordsRepository: WordsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(wordsRepository) as T
        }
    }
}
