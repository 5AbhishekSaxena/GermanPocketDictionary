package com.abhishek.germanPocketDictionary.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.core.ui.components.ErrorText
import com.abhishek.germanPocketDictionary.core.ui.components.words.UIMinWord
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import com.abhishek.germanPocketDictionary.ui.home.ui.WordList
import kotlinx.coroutines.delay

@Composable
fun SearchContent(
    viewState: SearchViewState,
    onQueryChange: (String) -> Unit,
    onNavigateIconClick: () -> Unit,
) {
    Scaffold(topBar = {
        SearchTopAppBar(onNavigateIconClick = onNavigateIconClick)
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            if (viewState is SearchViewState.Loaded.Searching) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            else Spacer(modifier = Modifier.height(4.dp))

            when (viewState) {
                is SearchViewState.Loading -> LoadingContent()
                is SearchViewState.Loaded -> LoadedContent(viewState, onQueryChange)
                is SearchViewState.Error -> ErrorContent(viewState)
                else -> Unit
            }
        }
    }
}

@Composable
private fun SearchTopAppBar(onNavigateIconClick: () -> Unit) {
    TopAppBar(title = {
        Text(text = "Search")
    }, navigationIcon = {
        IconButton(onClick = onNavigateIconClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
    })
}

@Composable
private fun LoadingContent() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    )
}

@Composable
private fun LoadedContent(
    viewState: SearchViewState.Loaded,
    onQueryChange: (String) -> Unit
) {
    val searchCardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
    )

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        delay(300L)
        focusRequester.requestFocus()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = searchCardColors,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(16.dp))

            BasicTextField(
                value = viewState.query,
                onValueChange = onQueryChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    },
                ),
                decorationBox = { innerTextField ->
                    if (viewState.query.isBlank()) Text(text = "Search Words..")

                    innerTextField()
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
            )
        }
    }

    if (viewState.words.isNotEmpty())
        WordList(words = viewState.words)
    else
        EmptyResultText()
}

@Composable
private fun EmptyResultText() {
    Text(
        text = stringResource(id = R.string.no_words),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    )
}

@Composable
private fun ErrorContent(viewState: SearchViewState.Error) {
    ErrorText(viewState.error)
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
private fun SearchContentPreview() {

    val words = (1..10).map {
        UIMinWord.Simple(
            germanTranslation = "Test", englishTranslation = "Test"
        )
    }

    val viewState = SearchViewState.Loaded.Idle("", words)

    GPDTheme {
        Surface {
            SearchContent(
                viewState = viewState,
                onQueryChange = {},
                onNavigateIconClick = {},
            )
        }
    }
}