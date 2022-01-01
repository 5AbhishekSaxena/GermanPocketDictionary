package com.abhishek.germanPocketDictionary.activity.feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.utilities.Constants;
import com.abhishek.germanPocketDictionary.utilities.HideErrorLine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class FeedBackActivity extends AppCompatActivity {

    private String username;
    private String feedback;
    private String additionalInformation;

    private TextInputLayout usernameLayout;
    private TextInputLayout feedbackLayout;

    private TextInputEditText usernameEditText;
    private TextInputEditText feedbackEditText;
    private TextInputEditText additionalInformationEditText;

    private FloatingActionButton sendMailFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.feedback_label));
        }

        usernameLayout = findViewById(R.id.user_name_layout);
        feedbackLayout = findViewById(R.id.feedback_layout);

        usernameEditText = findViewById(R.id.user_name_edit_text);
        feedbackEditText = findViewById(R.id.feedback_edit_text);
        additionalInformationEditText = findViewById(R.id.additional_information_edit_text);

        sendMailFloatingActionButton = findViewById(R.id.send_mail_fab);

        usernameEditText.addTextChangedListener(new HideErrorLine(usernameLayout));
        feedbackEditText.addTextChangedListener(new HideErrorLine(feedbackLayout));

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(Constants.API_KEYS.USERNAME);
            feedback = savedInstanceState.getString(Constants.API_KEYS.FEEDBACK);
            if (savedInstanceState.getString(Constants.API_KEYS.ADDITIONAL_INFORMATION) != null)
                additionalInformation = savedInstanceState.getString(Constants.API_KEYS.ADDITIONAL_INFORMATION);
        }

        sendMailFloatingActionButton.setOnClickListener((view -> {
            saveFeedback();
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFeedback() {

        if (usernameEditText.getText() != null)
            username = usernameEditText.getText().toString().trim();
        else
            username = null;
        if (feedbackEditText.getText() != null)
            feedback = feedbackEditText.getText().toString().trim();
        else
            feedback = null;

        if (additionalInformationEditText.getText() != null)
            additionalInformation = additionalInformationEditText.getText().toString().trim();
        else
            additionalInformation = null;

        if (username != null && !username.isEmpty()) {
            if (feedback != null && !feedback.isEmpty()) {
                sendMail(username, composeMessage(username, feedback, additionalInformation));
                finish();
            } else
                feedbackLayout.setError(getString(R.string.error_feedback_empty));
        } else
            usernameLayout.setError(getString(R.string.error_user_name_empty));
    }

    public void sendMail(String userName, String message) {
        String addresses = Constants.DEV_MAIL;
        String[] receiverEMailAddress = new String[]{addresses};
        String subject = "Feedback - " + userName;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, receiverEMailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else
            Toast.makeText(this, "Failed to send the mail, please install an email application or send using a browser", Toast.LENGTH_LONG).show();
    }

    private String composeMessage(String username, String feedback, String additionalInformation) {
        StringBuilder sb = new StringBuilder();
        sb.append("Greetings,\n\n")
                .append("I would like to send a feedback after using your application.")
                .append("\n\nFeedback:\n")
                .append(feedback);

        if (additionalInformation != null && !additionalInformation.isEmpty())
            sb.append("\n\nAdditional Information: ").append(additionalInformation);

        sb.append("\n\nBest Regards\n")
                .append(username);

        return sb.toString();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.API_KEYS.USERNAME, username);
        outState.putString(Constants.API_KEYS.FEEDBACK, feedback);
        if (additionalInformation != null)
            outState.putString(Constants.API_KEYS.ADDITIONAL_INFORMATION, additionalInformation);
    }
}

