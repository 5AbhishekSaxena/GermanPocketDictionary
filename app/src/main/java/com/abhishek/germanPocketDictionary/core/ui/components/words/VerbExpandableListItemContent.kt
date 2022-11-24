package com.abhishek.germanPocketDictionary.core.ui.components.words

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme

@Composable
fun VerbExpandableListItemContent(partizip: String, helpingVerb: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LabelAndText(label = "Partizip II", text = partizip)
        LabelAndText(label = "Helping Verb", text = helpingVerb)
    }
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
private fun VerbExpandableListItemContentPreview() {

    GPDTheme {
        Surface {
            VerbExpandableListItemContent(
                partizip = "abgeflogen",
                helpingVerb = "sein"
            )
        }
    }
}