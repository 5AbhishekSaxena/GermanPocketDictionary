package com.abhishek.germanPocketDictionary.activity.agreement

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.abhishek.germanPocketDictionary.R
import com.abhishek.germanPocketDictionary.activity.MainActivity
import java.io.IOException

class AgreementActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    private val viewModel: AgreementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)

        viewModel.migrateOldAgreementStatusKeyToNewOneIfPresent()

        val isAgreementAccepted = viewModel.checkIfAgreementIsAccepted()
        if (isAgreementAccepted)
            onAgreementAccepted()
        else
            setupAgreementAlertDialog()

        val showAgreementButton: Button = findViewById(R.id.show_agreement_button)
        showAgreementButton.setOnClickListener {
            setupAgreementAlertDialog()
        }
    }

    private fun setupAgreementAlertDialog() {
        val agreement: String
        try {
            agreement = viewModel.getAgreement()
        } catch (e: IOException) {
            agreementLoadingFailed()
            return
        }

        @SuppressLint("InflateParams") val alertDialogLayout: View =
            layoutInflater.inflate(R.layout.agreement_alertbox_layout, null)
        val agreementTextView = alertDialogLayout.findViewById<TextView>(R.id.agreement_details)
        agreementTextView.text = agreement
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.agreement_title)
            .setView(alertDialogLayout)
            .setPositiveButton(
                "I Agree"
            ) { _: DialogInterface?, _: Int ->
                viewModel.onAgreementAccepted()
                onAgreementAccepted()
            }
            .setNegativeButton(
                "I Disagree"
            ) { _: DialogInterface?, _: Int ->
                viewModel.onAgreementDenied()
            }
            .setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
        alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
        val checkBox = alertDialogLayout.findViewById<CheckBox>(R.id.agreement_checkbox)
        checkBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            updateAgreementStatus(isChecked)
        }
    }

    private fun onAgreementAccepted() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun agreementLoadingFailed() {
        Toast.makeText(this, "Agreement failed to load, contact the developer", Toast.LENGTH_LONG)
            .show()
    }

    private fun updateAgreementStatus(status: Boolean) {
        alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = status
    }
}