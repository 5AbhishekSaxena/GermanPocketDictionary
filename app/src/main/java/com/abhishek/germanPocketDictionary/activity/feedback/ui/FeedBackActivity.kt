package com.abhishek.germanPocketDictionary.activity.feedback.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.feedback.domain.DeveloperRepository
import com.abhishek.germanPocketDictionary.activity.feedback.domain.Email
import com.abhishek.germanPocketDictionary.activity.feedback.domain.ProdDeveloperRepository
import com.abhishek.germanPocketDictionary.utilities.Constants
import com.abhishek.germanPocketDictionary.utilities.EventObserver
import com.abhishek.germanPocketDictionary.utilities.HideErrorLine
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FeedBackActivity : AppCompatActivity() {
    private var username: String? = null
    private var feedback: String? = null
    private var additionalInformation: String? = null

    private var usernameLayout: TextInputLayout? = null
    private var feedbackLayout: TextInputLayout? = null

    private var usernameEditText: TextInputEditText? = null
    private var feedbackEditText: TextInputEditText? = null

    private var additionalInformationEditText: TextInputEditText? = null
    private var sendMailFloatingActionButton: FloatingActionButton? = null

    private val developerRepository: DeveloperRepository = ProdDeveloperRepository()

    @Suppress("UNCHECKED_CAST")
    private val viewModel: FeedbackViewModel by viewModels(factoryProducer = {
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FeedbackViewModel(developerRepository) as T
            }
        }
        viewModelFactory
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.feedback_label)
        }

        bindViews()
        addTechChangeListenerToInputLayouts()

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(Constants.API_KEYS.USERNAME)
            feedback = savedInstanceState.getString(Constants.API_KEYS.FEEDBACK)
            if (savedInstanceState.getString(Constants.API_KEYS.ADDITIONAL_INFORMATION) != null) additionalInformation =
                savedInstanceState.getString(
                    Constants.API_KEYS.ADDITIONAL_INFORMATION
                )
        }
        sendMailFloatingActionButton?.setOnClickListener { _ ->
            val username = usernameEditText?.text?.toString()
            val feedback = feedbackEditText?.text?.toString()
            val additionalInformation = additionalInformationEditText?.text?.toString()
            viewModel.onSendFeedback(username, feedback, additionalInformation)
        }

        subscribeToObservers()
    }

    private fun bindViews() {
        usernameLayout = findViewById(R.id.user_name_layout)
        feedbackLayout = findViewById(R.id.feedback_layout)
        usernameEditText = findViewById(R.id.user_name_edit_text)
        feedbackEditText = findViewById(R.id.feedback_edit_text)
        additionalInformationEditText = findViewById(R.id.additional_information_edit_text)
        sendMailFloatingActionButton = findViewById(R.id.send_mail_fab)
    }

    private fun addTechChangeListenerToInputLayouts() {
        usernameEditText?.addTextChangedListener(HideErrorLine(usernameLayout))
        feedbackEditText?.addTextChangedListener(HideErrorLine(feedbackLayout))
    }

    private fun subscribeToObservers() {
        observeUsernameError()
        observeFeedbackError()
        observeSendEmailEvent()
    }

    private fun observeUsernameError() {
        viewModel.usernameError.observe(this) {
            it?.let {
                usernameLayout?.error = it
            }
        }
    }

    private fun observeFeedbackError() {
        viewModel.feedbackError.observe(this) {
            it?.let {
                feedbackLayout?.error = it
            }
        }
    }

    private fun observeSendEmailEvent() {
        viewModel.sendEmailEvent.observe(this, EventObserver {
            sendEmail(it)
            finish()
        })
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
            Constants.API_KEYS.ADDITIONAL_INFORMATION,
            additionalInformation
        )
    }
}