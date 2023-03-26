package com.abhishek.germanPocketDictionary.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.core.domain.repository.WordsRepository
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<SearchViewState>(SearchViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        getAllWords()
    }

    private fun getAllWords() {
        viewModelScope.launch {
            _viewState.value = SearchViewState.Loading
            val words = wordsRepository.getWords()
                .map {
                    UIMinWord.Simple(
                        germanTranslation = it.germanTranslation,
                        englishTranslation = it.englishTranslation,
                    )
                }
            _viewState.value = SearchViewState.Loaded.Idle("", words)
        }
    }

    fun filterWords(query: String) {
        _viewState.update {
            when (it) {
                is SearchViewState.Loaded -> SearchViewState.Loaded.Searching(
                    query = query,
                    words = it.words,
                )
                else -> SearchViewState.Loaded.Searching(query, emptyList())
            }
        }

        val words = wordsRepository.filterWords(query)
            .map {
                UIMinWord.Simple(
                    germanTranslation = it.germanTranslation,
                    englishTranslation = it.englishTranslation,
                )
            }
        _viewState.update {
            if (words.isEmpty()) {
                SearchViewState.Loaded.EmptyResult(query)
            } else {
                SearchViewState.Loaded.Idle(query = query, words = words)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val wordsRepository: WordsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(wordsRepository) as T
        }
    }
}
