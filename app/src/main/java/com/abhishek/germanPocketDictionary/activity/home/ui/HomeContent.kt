package com.abhishek.germanPocketDictionary.activity.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.pager.pagerTabIndicatorOffset
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private enum class WordType(private val simplifiedName: String? = null) {
    ALL_WORDS("ALL WORDS"),
    NOUNS,
    VERBS,
    NUMBERS,
    COLORS,
    QUESTIONS,
    OPPOSITE;

    fun getSimplifiedName(): String {
        return simplifiedName ?: name
    }
}

@Composable
fun HomeContent(
    viewState: HomeViewState,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages by remember {
        val wordTypes = arrayOf(
            WordType.ALL_WORDS,
            WordType.NOUNS,
            WordType.VERBS,
            WordType.COLORS,
            WordType.QUESTIONS,
            WordType.OPPOSITE
        )

        mutableStateOf(wordTypes)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            edgePadding = 0.dp,
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.getSimplifiedName()) },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            when (pages[page]) {
                WordType.ALL_WORDS -> WordList(words = viewState.allWords)
                WordType.NOUNS -> NounWordList(words = viewState.nouns)
                WordType.VERBS -> VerbWordList(words = viewState.verbs)
                WordType.NUMBERS -> WordList(words = viewState.numbers)
                WordType.COLORS -> WordList(words = viewState.colors)
                WordType.QUESTIONS -> WordList(words = viewState.questions)
                WordType.OPPOSITE -> OppositesWordList(words = viewState.opposites)
            }
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

    val viewState = HomeViewState.Initial

    GPDTheme {
        Surface {
            HomeContent(viewState)
        }
    }
}
