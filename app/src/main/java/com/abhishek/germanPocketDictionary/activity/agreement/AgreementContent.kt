package com.abhishek.germanPocketDictionary.activity.agreement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.core.ui.components.ErrorText

@Composable
fun AgreementContent(
    viewState: AgreementViewState,
    onShowAgreementButtonClick: () -> Unit,
    onDialogConfirmButtonClick: () -> Unit,
    onDialogDismissButtonClick: () -> Unit,
    onTermsAcceptedCheckedChange: (Boolean) -> Unit,
) {

    if (viewState is AgreementViewState.Loading) {
        LoadingContent()
    } else if (viewState is AgreementViewState.Error) {
        ErrorContent(viewState)
    } else if (viewState is AgreementViewState.Loaded) {
        LoadedContent(
            viewState = viewState,
            onShowAgreementButtonClick = onShowAgreementButtonClick,
            onDialogConfirmButtonClick = onDialogConfirmButtonClick,
            onDialogDismissButtonClick = onDialogDismissButtonClick,
            onTermsAcceptedCheckedChange = onTermsAcceptedCheckedChange
        )
    }
}

@Composable
private fun LoadingContent() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )

}

@Composable
private fun ErrorContent(viewState: AgreementViewState.Error) {
    ErrorText(exception = viewState.error)
}

@Composable
private fun LoadedContent(
    viewState: AgreementViewState.Loaded,
    onShowAgreementButtonClick: () -> Unit,
    onDialogConfirmButtonClick: () -> Unit,
    onDialogDismissButtonClick: () -> Unit,
    onTermsAcceptedCheckedChange: (Boolean) -> Unit
) {
    if (!viewState.status && !viewState.showDialog) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Please accept the terms and conditions of the application.",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Please accept the agreement in order to use the application."
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onShowAgreementButtonClick) {
                Text(text = "Show Agreement")
            }
        }
    }

    if (viewState.showDialog) {
        AgreementDialog(
            viewState = viewState,
            onDialogConfirmButtonClick = onDialogConfirmButtonClick,
            onDialogDismissButtonClick = onDialogDismissButtonClick,
            onTermsAcceptedCheckedChange = onTermsAcceptedCheckedChange
        )
    }
}

@Composable
private fun AgreementDialog(
    viewState: AgreementViewState.Loaded,
    onDialogConfirmButtonClick: () -> Unit,
    onDialogDismissButtonClick: () -> Unit,
    onTermsAcceptedCheckedChange: (Boolean) -> Unit
) {
    AlertDialog(onDismissRequest = { },
        title = { Text(text = stringResource(id = R.string.agreement_title)) },
        confirmButton = {
            TextButton(
                onClick = onDialogConfirmButtonClick,
                enabled = viewState.termsAccepted && viewState.inputEnabled,
            ) {
                Text(text = "I Agree")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDialogDismissButtonClick,
                enabled = viewState.inputEnabled,
            ) {
                Text(text = "I Disagree")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(text = viewState.agreement)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onTermsAcceptedCheckedChange(!viewState.termsAccepted)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = viewState.termsAccepted,
                        onCheckedChange = onTermsAcceptedCheckedChange,
                        enabled = viewState.inputEnabled,
                    )

                    Text(
                        text = stringResource(id = R.string.declaration),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        })
}