package com.abhishek.germanPocketDictionary.activity.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.pager.pagerTabIndicatorOffset
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

enum class WordType(private val simplifiedName: String? = null) {
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
    onPageChange: (WordType) -> Unit,
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

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageChange(pages[page])
        }
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
                WordType.ALL_WORDS -> WordListPageContent(
                    state = viewState.allWords,
                    onLoaded = {
                        WordList(words = it)
                    },
                )
                WordType.NOUNS -> WordListPageContent(
                    state = viewState.nouns,
                    onLoaded = {
                        NounWordList(words = it)
                    },
                )
                WordType.VERBS -> WordListPageContent(
                    state = viewState.verbs,
                    onLoaded = {
                        VerbWordList(words = it)
                    },
                )
                WordType.NUMBERS -> WordListPageContent(
                    state = viewState.numbers,
                    onLoaded = {
                        WordList(words = it)
                    },
                )
                WordType.COLORS -> WordListPageContent(
                    state = viewState.colors,
                    onLoaded = {
                        WordList(words = it)
                    },
                )
                WordType.QUESTIONS -> WordListPageContent(
                    state = viewState.questions,
                    onLoaded = {
                        WordList(words = it)
                    },
                )
                WordType.OPPOSITE -> WordListPageContent(
                    state = viewState.opposites,
                    onLoaded = {
                        OppositesWordList(
                            words = it,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun <T> WordListPageContent(
    state: WordPageViewState<T>,
    onLoaded: @Composable (List<T>) -> Unit
) {
    when (state) {
        is WordPageViewState.Initial -> Unit
        is WordPageViewState.Error -> Unit
        is WordPageViewState.Loaded -> onLoaded(state.words)
        is WordPageViewState.Loading -> CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
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
