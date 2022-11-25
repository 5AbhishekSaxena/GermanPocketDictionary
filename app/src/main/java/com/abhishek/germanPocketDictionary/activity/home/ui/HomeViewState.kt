package com.abhishek.germanPocketDictionary.activity.home.ui

import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord

data class HomeViewState(
    val allWords: List<UIMinWord.Simple>,
    val nouns: List<UIMinWord.Noun>,
    val verbs: List<UIMinWord.Verb>,
    val numbers: List<UIMinWord.Simple>,
    val colors: List<UIMinWord.Simple>,
    val questions: List<UIMinWord.Simple>,
    val opposites: List<UIMinWord.Opposites>,
) {
    companion object {
        val Initial = HomeViewState(
            allWords = emptyList(),
            nouns = emptyList(),
            verbs = emptyList(),
            numbers = emptyList(),
            colors = emptyList(),
            questions = emptyList(),
            opposites = emptyList(),
        )
    }
}
