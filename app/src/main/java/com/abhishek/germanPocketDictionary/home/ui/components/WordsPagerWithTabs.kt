package com.abhishek.germanPocketDictionary.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.pager.pagerTabIndicatorOffset
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.home.ui.WordPageViewState
import com.abhishek.germanPocketDictionary.home.ui.WordType
import com.abhishek.germanPocketDictionary.ui.home.ui.NounWordList
import com.abhishek.germanPocketDictionary.ui.home.ui.OppositesWordList
import com.abhishek.germanPocketDictionary.ui.home.ui.VerbWordList
import com.abhishek.germanPocketDictionary.ui.home.ui.WordList
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun WordsPagerWithTabs(
    allWordsPageViewState: WordPageViewState<UIMinWord.Simple>,
    nounsPageViewState: WordPageViewState<UIMinWord.Noun>,
    verbsPageViewState: WordPageViewState<UIMinWord.Verb>,
    numbersPageViewState: WordPageViewState<UIMinWord.Simple>,
    colorsPageViewState: WordPageViewState<UIMinWord.Simple>,
    questionsPageViewState: WordPageViewState<UIMinWord.Simple>,
    oppositesPageViewState: WordPageViewState<UIMinWord.Opposites>,
    onPageChange: (WordType) -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages by remember {
        mutableStateOf(WordType.values())
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageChange(pages[page])
        }
    }
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
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
                state = allWordsPageViewState,
                onLoaded = {
                    WordList(words = it)
                },
            )
            WordType.NOUNS -> WordListPageContent(
                state = nounsPageViewState,
                onLoaded = {
                    NounWordList(words = it)
                },
            )
            WordType.VERBS -> WordListPageContent(
                state = verbsPageViewState,
                onLoaded = {
                    VerbWordList(words = it)
                },
            )
            WordType.NUMBERS -> WordListPageContent(
                state = numbersPageViewState,
                onLoaded = {
                    WordList(words = it)
                },
            )
            WordType.COLORS -> WordListPageContent(
                state = colorsPageViewState,
                onLoaded = {
                    WordList(words = it)
                },
            )
            WordType.QUESTIONS -> WordListPageContent(
                state = questionsPageViewState,
                onLoaded = {
                    WordList(words = it)
                },
            )
            WordType.OPPOSITE -> WordListPageContent(
                state = oppositesPageViewState,
                onLoaded = {
                    OppositesWordList(
                        words = it,
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
                    )
                },
            )
        }
    }
}

@Composable
private fun <T> WordListPageContent(
    state: WordPageViewState<T>,
    onLoaded: @Composable (List<T>) -> Unit,
) {
    when (state) {
        is WordPageViewState.Initial -> Unit
        is WordPageViewState.Error -> Unit
        is WordPageViewState.Loaded -> onLoaded(state.words)
        is WordPageViewState.Loading -> CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    }
}
