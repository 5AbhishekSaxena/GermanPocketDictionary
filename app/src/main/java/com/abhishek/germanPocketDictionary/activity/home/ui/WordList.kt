package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.words.NounExpandableWordListItemContent
import com.abhishek.germanPocketDictionary.core.ui.components.words.OppositeWordListItem
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.components.words.VerbExpandableListItemContent
import com.abhishek.germanPocketDictionary.core.ui.components.words.WordListItem

private val verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp)

@Composable
fun WordList(
    words: List<UIMinWord>
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(words) { word ->
            WordListItem(word = word)
        }
    }
}

@Composable
fun NounWordList(
    words: List<UIMinWord.Noun>
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(words) { word ->
            WordListItem(
                word = word,
                expandableContent = {
                    NounExpandableWordListItemContent(plural = word.plural)
                },
            )
        }
    }
}

@Composable
fun VerbWordList(
    words: List<UIMinWord.Verb>
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(words) { word ->
            WordListItem(
                word = word,
                expandableContent = {
                    VerbExpandableListItemContent(
                        partizip = word.partizip,
                        helpingVerb = word.helpingVerb,
                    )
                },
            )
        }
    }
}

@Composable
fun OppositesWordList(
    words: List<UIMinWord.Opposites>
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(words) { word ->
            OppositeWordListItem(
                word = word,
            )
        }
    }
}