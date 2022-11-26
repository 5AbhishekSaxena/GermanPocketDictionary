package com.abhishek.germanPocketDictionary.activity.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.home.ui.components.WordsPagerWithTabs
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme

@Composable
fun HomeContent(
    allWordsPageViewState: WordPageViewState<UIMinWord.Simple>,
    nounsPageViewState: WordPageViewState<UIMinWord.Noun>,
    verbsPageViewState: WordPageViewState<UIMinWord.Verb>,
    numbersPageViewState: WordPageViewState<UIMinWord.Simple>,
    colorsPageViewState: WordPageViewState<UIMinWord.Simple>,
    questionsPageViewState: WordPageViewState<UIMinWord.Simple>,
    oppositesPageViewState: WordPageViewState<UIMinWord.Opposites>,
    onPageChange: (WordType) -> Unit,
    onSearchClick: () -> Unit,
    onRateThisAppClick: () -> Unit,
) {

    var showOptionsMenu by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, actions = {
            IconButton(
                onClick = onSearchClick, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFededed)
                ), modifier = Modifier.clip(CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }

            IconButton(
                onClick = { showOptionsMenu = !showOptionsMenu },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFededed)
                ),
                modifier = Modifier.clip(CircleShape)
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }

            DropdownMenu(expanded = showOptionsMenu,
                onDismissRequest = { showOptionsMenu = false }) {

                DropdownMenuItem(onClick = onRateThisAppClick, text = {
                    Text(text = stringResource(id = R.string.rate_this_app))
                }, contentPadding = PaddingValues(horizontal = 24.dp), leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.StarRate, contentDescription = null
                    )
                })
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            WordsPagerWithTabs(
                allWordsPageViewState = allWordsPageViewState,
                nounsPageViewState = nounsPageViewState,
                verbsPageViewState = verbsPageViewState,
                numbersPageViewState = numbersPageViewState,
                colorsPageViewState = colorsPageViewState,
                questionsPageViewState = questionsPageViewState,
                oppositesPageViewState = oppositesPageViewState,
                onPageChange = onPageChange,
            )
        }
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
        "test", "test", "test"
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

    val allWordsPageViewState = WordPageViewState.Loaded(words)
    val nounsPageViewState = WordPageViewState.Loaded(nouns)
    val verbsPageViewState = WordPageViewState.Loaded(verbs)
    val numbersPageViewState = WordPageViewState.Loaded(words)
    val colorsPageViewState = WordPageViewState.Loaded(words)
    val questionsPageViewState = WordPageViewState.Loaded(words)
    val oppositesPageViewState = WordPageViewState.Loaded(opposites)

    GPDTheme {
        Surface {
            HomeContent(allWordsPageViewState = allWordsPageViewState,
                nounsPageViewState = nounsPageViewState,
                verbsPageViewState = verbsPageViewState,
                numbersPageViewState = numbersPageViewState,
                colorsPageViewState = colorsPageViewState,
                questionsPageViewState = questionsPageViewState,
                oppositesPageViewState = oppositesPageViewState,
                onPageChange = {},
                onSearchClick = {},
                onRateThisAppClick = {})
        }
    }
}
