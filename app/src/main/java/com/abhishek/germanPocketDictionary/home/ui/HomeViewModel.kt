package com.abhishek.germanPocketDictionary.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.germanPocketDictionary.core.domain.repository.WordsRepository
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _allWordsPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    val allWordsPageViewState = _allWordsPageViewState.asStateFlow()

    private val _nounsPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Noun>>(WordPageViewState.Initial())
    val nounsPageViewState = _nounsPageViewState.asStateFlow()

    private val _verbsPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Verb>>(WordPageViewState.Initial())
    val verbsPageViewState = _verbsPageViewState.asStateFlow()

    private val _numbersPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    val numbersPageViewState = _numbersPageViewState.asStateFlow()

    private val _colorsPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    val colorsPageViewState = _colorsPageViewState.asStateFlow()

    private val _questionsPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    val questionsPageViewState = _questionsPageViewState.asStateFlow()

    private val _oppositesPageViewState =
        MutableStateFlow<WordPageViewState<UIMinWord.Opposites>>(WordPageViewState.Initial())
    val oppositesPageViewState = _oppositesPageViewState.asStateFlow()

    init {
        updateAllWords()
    }

    private fun updateAllWords() {
        viewModelScope.launch {
            _allWordsPageViewState.value = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.TABLES.ALL_WORDS)
            _allWordsPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateNouns() {
        viewModelScope.launch {
            _nounsPageViewState.value = WordPageViewState.Loading()
            val words = wordsRepository
                .getWordsByCategory(Constants.API_KEYS.CATEGORY_NOUNS)
                .map {
                    UIMinWord.Noun(
                        germanTranslation = it.germanTranslation,
                        englishTranslation = it.englishTranslation,
                        plural = it.germanPlural
                    )
                }

            _nounsPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateVerbs() {
        viewModelScope.launch {
            _verbsPageViewState.value = WordPageViewState.Loading()
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

            _verbsPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateNumbers() {
        viewModelScope.launch {
            _numbersPageViewState.value = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_NUMBERS)

            _numbersPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateColors() {
        viewModelScope.launch {
            _colorsPageViewState.value = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_COLORS)

            _colorsPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateQuestions() {
        viewModelScope.launch {
            _questionsPageViewState.value = WordPageViewState.Loading()
            val words = getSimpleWords(Constants.API_KEYS.CATEGORY_QUESTIONS)
            _questionsPageViewState.value = WordPageViewState.Loaded(words)
        }
    }

    private fun updateOpposites() {
        viewModelScope.launch {
            _oppositesPageViewState.value = WordPageViewState.Loading()
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

            _oppositesPageViewState.value = WordPageViewState.Loaded(words)
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
        when (wordType) {
            WordType.ALL_WORDS -> if (doesWordsNeedsToBeUpdated(_allWordsPageViewState.value)) {
                updateAllWords()
            }
            WordType.NOUNS -> if (doesWordsNeedsToBeUpdated(_nounsPageViewState.value)) {
                updateNouns()
            }
            WordType.VERBS -> if (doesWordsNeedsToBeUpdated(_verbsPageViewState.value)) {
                updateVerbs()
            }
            WordType.NUMBERS -> if (doesWordsNeedsToBeUpdated(_numbersPageViewState.value)) {
                updateNumbers()
            }
            WordType.COLORS -> if (doesWordsNeedsToBeUpdated(_colorsPageViewState.value)) {
                updateColors()
            }
            WordType.QUESTIONS -> if (doesWordsNeedsToBeUpdated(_questionsPageViewState.value)) {
                updateQuestions()
            }
            WordType.OPPOSITE -> if (doesWordsNeedsToBeUpdated(_oppositesPageViewState.value)) {
                updateOpposites()
            }
        }
    }

    private fun doesWordsNeedsToBeUpdated(state: WordPageViewState<*>): Boolean {
        return state !is WordPageViewState.Loaded<*> && state !is WordPageViewState.Error<*>
    }
}