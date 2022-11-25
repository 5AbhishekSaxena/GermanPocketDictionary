package com.abhishek.germanPocketDictionary.activity.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.words.NounExpandableWordListItemContent
import com.abhishek.germanPocketDictionary.core.ui.components.words.OppositeWordListItem
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.components.words.VerbExpandableListItemContent
import com.abhishek.germanPocketDictionary.core.ui.components.words.WordListItem

@Composable
fun WordList(
    words: List<UIMinWord>
) {
    WordsLazyColumn {
        items(words) { word ->
            WordListItem(word = word)
        }
    }
}

@Composable
fun NounWordList(
    words: List<UIMinWord.Noun>
) {
    WordsLazyColumn {
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
    WordsLazyColumn {
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
    WordsLazyColumn {
        items(words) { word ->
            OppositeWordListItem(
                word = word,
            )
        }
    }
}

@Composable
private fun WordsLazyColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}