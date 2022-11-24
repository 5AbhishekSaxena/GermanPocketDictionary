package com.abhishek.germanPocketDictionary.core.ui.components.words

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme

@Composable
fun WordListItem(
    word: UIMinWord,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            GermanWordAndTranslation(word = word)
        }
    }
}

@Composable
fun WordListItem(
    word: UIMinWord,
    expandableContent: @Composable () -> Unit,
) {

    var showExpandableContent by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            showExpandableContent = !showExpandableContent
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                GermanWordAndTranslation(word)

                AnimatedVisibility(visible = showExpandableContent) {
                    Column {
                        Spacer(modifier = Modifier.height(6.dp))
                        expandableContent()
                    }
                }
            }

            val icon =
                if (showExpandableContent) Icons.Default.ExpandLess
                else Icons.Default.ExpandMore

            Icon(imageVector = icon, contentDescription = null)
        }
    }
}

@Composable
private fun GermanWordAndTranslation(word: UIMinWord) {
    Text(
        text = word.germanTranslation,
        style = MaterialTheme.typography.titleMedium,
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
        text = word.englishTranslation,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember", "MagicNumber")
private fun WordListItemPreview() {

    val word = UIMinWord.Noun(
        germanTranslation = "das Abendessen",
        englishTranslation = "Dinner",
        plural = "die Abendessen"
    )

    GPDTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WordListItem(word)
                WordListItem(
                    word = word,
                    expandableContent = {
                        NounExpandableWordListItemContent(plural = word.plural)
                    },
                )
            }
        }
    }
}