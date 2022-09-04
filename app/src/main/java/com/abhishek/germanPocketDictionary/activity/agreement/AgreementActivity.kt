package com.abhishek.germanPocketDictionary.activity.agreement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.MainActivity
import java.io.IOException

class AgreementActivity : AppCompatActivity() {
    private var agreementAlertDialog: AgreementAlertDialog? = null

    private val viewModel: AgreementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)

        viewModel.migrateOldAgreementStatusKeyToNewOneIfPresent()

        val isAgreementAccepted = viewModel.checkIfAgreementIsAccepted()
        if (isAgreementAccepted)
            onAgreementAccepted()
        else {
            showAgreementDialog()
        }

        val showAgreementButton: Button = findViewById(R.id.show_agreement_button)
        showAgreementButton.setOnClickListener {
            showAgreementDialog()
        }
    }

    private fun showAgreementDialog() {
        val agreement: String
        try {
            agreement = viewModel.getAgreement()
        } catch (e: IOException) {
            agreementLoadingFailed()
            return
        }

        if (agreementAlertDialog == null) {
            agreementAlertDialog = AgreementAlertDialog.create(
                context = this,
                agreement = agreement,
                onAgreeClickListener = {
                    viewModel.onAgreementAccepted()
                    onAgreementAccepted()
                },
                onDisagreeClickListener = {
                    viewModel.onAgreementDenied()
                }
            )
        }

        agreementAlertDialog?.show()
    }

    private fun onAgreementAccepted() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun agreementLoadingFailed() {
        Toast.makeText(this, "Agreement failed to load, contact the developer", Toast.LENGTH_LONG)
            .show()
    }
}