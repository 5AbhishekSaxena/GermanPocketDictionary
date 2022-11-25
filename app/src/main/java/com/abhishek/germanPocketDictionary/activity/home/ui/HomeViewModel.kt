package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Initial)
    val viewState = _viewState.asStateFlow()

    init {
        getWords()
    }

    private fun getWords() {
        viewModelScope.launch {
            val words = wordsRepository.getWordsByCategory(Constants.TABLES.ALL_WORDS)
                .map {
                    UIMinWord.Simple(it.germanTranslation, it.englishTranslation)
                }
                .toList()

            _viewState.update {
                it.copy(allWords = words)
            }
        }
    }

}