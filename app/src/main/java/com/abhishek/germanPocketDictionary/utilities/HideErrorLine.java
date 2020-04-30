package com.abhishek.germanPocketDictionary.utilities;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Abhishek Saxena
 * @since 21/4/20 2:37 PM
 */

public class HideErrorLine implements TextWatcher {

    private TextInputLayout textInputLayout;

    public HideErrorLine(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        textInputLayout.setErrorEnabled(false);
    }
}
