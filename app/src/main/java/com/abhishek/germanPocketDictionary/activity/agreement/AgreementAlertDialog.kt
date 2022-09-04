package com.abhishek.germanPocketDictionary.activity.agreement

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.abhishek.germanPocketDictionary.R

class AgreementAlertDialog private constructor(
    private val context: Context,
    private val agreement: String,
    private val onAgreeClickListener: () -> Unit,
    private val onDisagreeClickListener: () -> Unit,
    private val onCheckedChangeListener: ((Boolean) -> Unit)?,
) {
    private var alertDialog: AlertDialog? = null

    private var agreementReadAcknowledgementCheckBox: CheckBox? = null

    private fun build() {
        val alertDialogLayout = getAlertDialogLayout(
            agreement = agreement,
        )

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.agreement_title)
            .setView(alertDialogLayout)
            .setPositiveButton(
                "I Agree"
            ) { _: DialogInterface?, _: Int ->
                onAgreeClickListener()
            }
            .setNegativeButton(
                "I Disagree"
            ) { _: DialogInterface?, _: Int ->
                onDisagreeClickListener()
                resetAlertLayoutForm()
            }
            .setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.create()
        alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
    }

    fun show() {
        alertDialog?.show()
    }

    fun hide() {
        alertDialog?.hide()
    }

    private fun getAlertDialogLayout(
        agreement: String
    ): View {
        val alertDialogLayout: View =
            View.inflate(context, R.layout.agreement_alertbox_layout, null)

        val agreementTextView = alertDialogLayout.findViewById<TextView>(R.id.agreement_details)
        agreementTextView.text = agreement

        agreementReadAcknowledgementCheckBox =
            alertDialogLayout.findViewById(R.id.agreement_checkbox)
        configureAgreementReadAcknowledgementCheckBox()

        return alertDialogLayout
    }

    private fun configureAgreementReadAcknowledgementCheckBox() {
        agreementReadAcknowledgementCheckBox?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            onCheckedChangeListener?.invoke(isChecked)
            toggleAgreeButtonEnabled(isChecked)
        }
    }

    private fun toggleAgreeButtonEnabled(isEnabled: Boolean) {
        alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = isEnabled
    }

    private fun resetAlertLayoutForm() {
        agreementReadAcknowledgementCheckBox?.isChecked = false
    }

    companion object {
        fun create(
            context: Context,
            agreement: String,
            onAgreeClickListener: () -> Unit,
            onDisagreeClickListener: () -> Unit,
            onCheckedChangeListener: ((Boolean) -> Unit)? = null
        ): AgreementAlertDialog {
            val agreementAlertDialog = AgreementAlertDialog(
                context = context,
                agreement = agreement,
                onAgreeClickListener = onAgreeClickListener,
                onDisagreeClickListener = onDisagreeClickListener,
                onCheckedChangeListener = onCheckedChangeListener
            )
            agreementAlertDialog.build()

            return agreementAlertDialog
        }
    }
}