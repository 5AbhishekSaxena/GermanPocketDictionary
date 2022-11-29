package com.abhishek.germanPocketDictionary.core.ui.components.words

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OppositeWordListItem(
    word: UIMinWord.Opposites
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GermanWordAndTranslation(
            germanTranslation = word.germanTranslation,
            englishTranslation = word.englishTranslation,
            modifier = Modifier.weight(1f)
        )

        GermanWordAndTranslation(
            germanTranslation = word.oppositeGermanTranslation,
            englishTranslation = word.oppositeEnglishTranslation,
            modifier = Modifier.weight(1f)
        )
    }
}