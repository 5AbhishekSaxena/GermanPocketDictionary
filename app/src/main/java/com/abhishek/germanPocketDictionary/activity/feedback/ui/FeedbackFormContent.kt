package com.abhishek.germanPocketDictionary.activity.feedback.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.germanPocketDictionary.core.ui.components.GPDButton
import com.abhishek.germanPocketDictionary.core.ui.components.textfields.GPDOutlinedTextField
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme

@Composable
fun FeedbackFormContent(
    viewState: FeedbackViewState,
    onNameChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onAdditionalInformationChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GPDOutlinedTextField(
            value = viewState.feedbackForm.name,
            onValueChange = onNameChange,
            label = "Name",
            placeholder = "Ex. John Doe",
            error = viewState.feedbackForm.nameError,
            required = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        GPDOutlinedTextField(
            value = viewState.feedbackForm.body,
            onValueChange = onBodyChange,
            label = "Feedback",
            placeholder = "Please enter your feedback.",
            error = viewState.feedbackForm.bodyError,
            required = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        GPDOutlinedTextField(
            value = viewState.feedbackForm.additionalInformation,
            onValueChange = onAdditionalInformationChange,
            label = "Additional Information",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        GPDButton(
            text = "SUBMIT",
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
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
private fun FeedbackFormContentPreview() {

    val viewState = FeedbackViewState.Initial

    GPDTheme {
        Surface {
            FeedbackFormContent(
                viewState = viewState,
                onNameChange = {},
                onBodyChange = {},
                onAdditionalInformationChange = {},
                onSubmit = {},
            )
        }
    }
}