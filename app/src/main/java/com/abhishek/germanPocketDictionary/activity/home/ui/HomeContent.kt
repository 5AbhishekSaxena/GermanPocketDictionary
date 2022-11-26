package com.abhishek.germanPocketDictionary.activity.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhishek.germanPocketDictionary.activity.home.ui.components.WordsPagerWithTabs
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme

@Composable
fun HomeContent(
    viewState: HomeViewState,
    onPageChange: (WordType) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WordsPagerWithTabs(
            viewState = viewState,
            onPageChange = onPageChange,
        )
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
private fun HomeContentPreview() {

    val noun = UIMinWord.Noun(
        "test",
        "test",
        "test"
    )

    val verb = UIMinWord.Verb(
        "test",
        "test",
        "test",
        "test",
    )

    val opposite = UIMinWord.Opposites(
        "test",
        "test",
        "test",
        "test",
    )

    val word = UIMinWord.Simple("test", "test")

    val nouns = (1..10).map { noun }
    val verbs = (1..10).map { verb }
    val opposites = (1..10).map { opposite }

    val words = (1..10).map { word }

    val viewState = HomeViewState().apply {
        this.allWords = WordPageViewState.Loaded(words)
        this.nouns = WordPageViewState.Loaded(nouns)
        this.verbs = WordPageViewState.Loaded(verbs)
        this.numbers = WordPageViewState.Loaded(words)
        this.colors = WordPageViewState.Loaded(words)
        this.questions = WordPageViewState.Loaded(words)
        this.opposites = WordPageViewState.Loaded(opposites)
    }

    GPDTheme {
        Surface {
            HomeContent(
                viewState = viewState,
                onPageChange = {},
            )
        }
    }
}
