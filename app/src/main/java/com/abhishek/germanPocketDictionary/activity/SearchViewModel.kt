package com.abhishek.germanPocketDictionary.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    fun filterWords(query: String) {
        _words.value = wordsRepository.filterWords(query)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val wordsRepository: WordsRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(wordsRepository) as T
        }
    }
}