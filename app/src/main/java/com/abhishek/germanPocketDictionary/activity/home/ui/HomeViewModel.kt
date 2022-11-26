package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.data.WordsRepository
import com.abhishek.germanPocketDictionary.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    val viewState = HomeViewState()

    init {
        updateAllWords()
    }

    private fun updateAllWords() {
        viewModelScope.launch {
            viewState.allWords = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.TABLES.ALL_WORDS)
            viewState.allWords = WordPageViewState.Loaded(words)
        }
    }

    private fun updateNouns() {
        viewModelScope.launch {
            viewState.nouns = WordPageViewState.Loading()
            val words = wordsRepository
                .getWordsByCategory(Constants.API_KEYS.CATEGORY_NOUNS)
                .map {
                    UIMinWord.Noun(
                        germanTranslation = it.germanTranslation,
                        englishTranslation = it.englishTranslation,
                        plural = it.germanPlural
                    )
                }

            viewState.nouns = WordPageViewState.Loaded(words)
        }
    }

    private fun updateVerbs() {
        viewModelScope.launch {
            viewState.verbs = WordPageViewState.Loading()
            val words = wordsRepository
                .getWordsByCategory(Constants.API_KEYS.CATEGORY_VERBS)
                .map {
                    UIMinWord.Verb(
                        germanTranslation = it.germanTranslation,
                        englishTranslation = it.englishTranslation,
                        partizip = it.verbPartizip,
                        helpingVerb = it.helpingVerb,
                    )
                }

            viewState.verbs = WordPageViewState.Loaded(words)
        }
    }

    private fun updateNumbers() {
        viewModelScope.launch {
            viewState.numbers = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_NUMBERS)

            viewState.numbers = WordPageViewState.Loaded(words)
        }
    }

    private fun updateColors() {
        viewModelScope.launch {
            viewState.colors = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_COLORS)

            viewState.colors = WordPageViewState.Loaded(words)
        }
    }

    private fun updateQuestions() {
        viewModelScope.launch {
            viewState.questions = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_QUESTIONS)
            viewState.questions = WordPageViewState.Loaded(words)
        }
    }

    private fun updateOpposites() {
        viewModelScope.launch {
            viewState.opposites = WordPageViewState.Loading()
            val words = wordsRepository
                .getWordsByCategory(Constants.API_KEYS.CATEGORY_OPPOSITE)
                .map {
                    UIMinWord.Opposites(
                        germanTranslation = it.germanTranslation,
                        englishTranslation = it.englishTranslation,
                        oppositeGermanTranslation = it.germanOpposite,
                        oppositeEnglishTranslation = it.germanOppositeMeaning
                    )
                }

            viewState.opposites = WordPageViewState.Loaded(words)
        }
    }

    private suspend fun getSimpleWords(category: String): List<UIMinWord.Simple> {
        return wordsRepository.getWordsByCategory(category)
            .map {
                UIMinWord.Simple(
                    germanTranslation = it.germanTranslation,
                    englishTranslation = it.englishTranslation,
                )
            }
    }

    fun onPageChange(wordType: WordType) {
        val currentState = viewState
        when (wordType) {
            WordType.ALL_WORDS -> if (doesWordsNeedsToBeUpdated(currentState.allWords)) {
                updateAllWords()
            }
            WordType.NOUNS -> if (doesWordsNeedsToBeUpdated(currentState.nouns)) {
                updateNouns()
            }
            WordType.VERBS -> if (doesWordsNeedsToBeUpdated(currentState.verbs)) {
                updateVerbs()
            }
            WordType.NUMBERS -> if (doesWordsNeedsToBeUpdated(currentState.numbers)) {
                updateNumbers()
            }
            WordType.COLORS -> if (doesWordsNeedsToBeUpdated(currentState.colors)) {
                updateColors()
            }
            WordType.QUESTIONS -> if (doesWordsNeedsToBeUpdated(currentState.questions)) {
                updateQuestions()
            }
            WordType.OPPOSITE -> if (doesWordsNeedsToBeUpdated(currentState.opposites)) {
                updateOpposites()
            }
        }
    }

    private fun doesWordsNeedsToBeUpdated(state: WordPageViewState<*>): Boolean {
        return state !is WordPageViewState.Loaded<*> && state !is WordPageViewState.Error<*>
    }
}