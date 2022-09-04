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

        if (alertDialog == null)
            alertDialog = createAgreementDialog(agreement)

        alertDialog?.show()
    }

    private fun createAgreementDialog(agreement: String): AlertDialog {
        val alertDialogLayout = getAlertDialogLayout(
            agreement = agreement,
            onCheckedChangeListener = ::updateAgreementStatus,
        )

        @SuppressLint("InflateParams")
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
        val alertDialog = builder.create()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
        return alertDialog
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

    private fun getAlertDialogLayout(
        agreement: String,
        onCheckedChangeListener: (Boolean) -> Unit
    ): View {
        val alertDialogLayout: View =
            layoutInflater.inflate(R.layout.agreement_alertbox_layout, null)
        val agreementTextView = alertDialogLayout.findViewById<TextView>(R.id.agreement_details)
        agreementTextView.text = agreement
        val checkBox = alertDialogLayout.findViewById<CheckBox>(R.id.agreement_checkbox)
        checkBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            onCheckedChangeListener(isChecked)
        }

        return alertDialogLayout
    }
}