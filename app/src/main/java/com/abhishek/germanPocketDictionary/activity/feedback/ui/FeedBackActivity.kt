package com.abhishek.germanPocketDictionary.activity.feedback.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.feedback.domain.DeveloperRepository
import com.abhishek.germanPocketDictionary.activity.feedback.domain.Email
import com.abhishek.germanPocketDictionary.activity.feedback.domain.ProdDeveloperRepository
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import com.abhishek.germanPocketDictionary.databinding.ActivityFeedBackBinding
import com.abhishek.germanPocketDictionary.utilities.Constants
import com.abhishek.germanPocketDictionary.utilities.EventObserver
import com.abhishek.germanPocketDictionary.utilities.HideErrorLine

class FeedBackActivity : ComponentActivity() {
    private var username: String? = null
    private var feedback: String? = null
    private var additionalInformation: String? = null

    private lateinit var binding: ActivityFeedBackBinding

    private val developerRepository: DeveloperRepository = ProdDeveloperRepository()

    @Suppress("UNCHECKED_CAST")
    private val viewModel: FeedbackViewModel by viewModels(factoryProducer = {
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedbackViewModel(developerRepository) as T
            }
        }
        viewModelFactory
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GPDTheme {
                Surface {
                    FeedbackFormScreen(viewModel = viewModel)
                }
            }
        }
    }

    private fun setContentAndInitializeDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_back)
    }

    private fun addTechChangeListenerToInputLayouts() {
        binding.usernameEditText.addTextChangedListener(HideErrorLine(binding.usernameLayout))
        binding.feedbackEditText.addTextChangedListener(HideErrorLine(binding.feedbackLayout))
    }

    private fun subscribeToObservers() {
        observeUsernameError()
        observeFeedbackError()
        observeSendEmailEvent()
    }

    private fun observeUsernameError() {
        viewModel.usernameError.observe(this) {
            it?.let {
                binding.usernameLayout.error = it
            }
        }
    }

    private fun observeFeedbackError() {
        viewModel.feedbackError.observe(this) {
            it?.let {
                binding.feedbackLayout.error = it
            }
        }
    }

    private fun observeSendEmailEvent() {
        viewModel.sendEmailEvent.observe(this, EventObserver {
            sendEmail(it)
            finish()
        })
    }

    private fun setOnClickListeners() {
        setSendMailFloatingActionButtonClickListener()
    }

    private fun setSendMailFloatingActionButtonClickListener() {
        binding.sendMailFloatingActionButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val feedback = binding.feedbackEditText.text.toString()
            val additionalInformation = binding.additionalInformationEditText.text?.toString()
//            viewModel.onSubmit(username, feedback, additionalInformation)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendEmail(email: Email) {
        val intent = buildEmailIntent(email)
        val intentWithChooser = Intent.createChooser(intent, "Sharing with")
        this.startActivity(intentWithChooser)
    }

    private fun buildEmailIntent(email: Email): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email.receiverEmailAddresses.toTypedArray())
        intent.putExtra(Intent.EXTRA_SUBJECT, email.subject)
        intent.putExtra(Intent.EXTRA_TEXT, email.message)
        return intent
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.API_KEYS.USERNAME, username)
        outState.putString(Constants.API_KEYS.FEEDBACK, feedback)
        if (additionalInformation != null) outState.putString(
            Constants.API_KEYS.ADDITIONAL_INFORMATION, additionalInformation
        )
    }
}