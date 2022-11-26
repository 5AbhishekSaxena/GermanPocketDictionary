package com.abhishek.germanPocketDictionary.activity.search

import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord

sealed class SearchViewState {
    object Initial : SearchViewState()

    object Loading : SearchViewState()

    sealed class Loaded(
        open val query: String,
        open val words: List<UIMinWord.Simple>
    ) : SearchViewState() {
        data class Idle(
            override val query: String,
            override val words: List<UIMinWord.Simple>
        ) : Loaded(query, words)

        data class Searching(
            override val query: String,
            override val words: List<UIMinWord.Simple>
        ) : Loaded(query, words)

        data class EmptyResult(
            override val query: String,
        ) : Loaded(query, emptyList())
    }

    data class Error(val error: Exception) : SearchViewState()
}
