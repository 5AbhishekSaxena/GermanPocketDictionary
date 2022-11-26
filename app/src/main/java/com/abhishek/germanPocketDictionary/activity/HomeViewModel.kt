package com.abhishek.germanPocketDictionary.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word
import kotlinx.coroutines.launch

class HomeViewModel(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _allWords = MutableLiveData<List<Word>>()
    val allWords: LiveData<List<Word>> = _allWords

    fun getWords() {
        viewModelScope.launch {
            _allWords.value = wordsRepository.getWords()
        }
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
