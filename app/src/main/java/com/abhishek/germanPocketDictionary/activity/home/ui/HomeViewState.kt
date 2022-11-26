package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord

class HomeViewState {
    var allWords by mutableStateOf<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    var nouns by mutableStateOf<WordPageViewState<UIMinWord.Noun>>(WordPageViewState.Initial())
    var verbs by mutableStateOf<WordPageViewState<UIMinWord.Verb>>(WordPageViewState.Initial())
    var numbers by mutableStateOf<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    var colors by mutableStateOf<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    var questions by mutableStateOf<WordPageViewState<UIMinWord.Simple>>(WordPageViewState.Initial())
    var opposites by mutableStateOf<WordPageViewState<UIMinWord.Opposites>>(WordPageViewState.Initial())
}

