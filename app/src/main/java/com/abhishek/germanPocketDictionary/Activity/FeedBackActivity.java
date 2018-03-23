package com.abhishek.germanPocketDictionary.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek.germanPocketDictionary.R;



public class FeedBackActivity extends AppCompatActivity {

    private boolean FEEDBACK_FIELD_EMPTY = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:
                return true;

            case R.id.action_save:
                    saveFeedback();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFeedback() {

        EditText nameEditTextView = findViewById(R.id.edit_feedback_user_name);
        EditText feedbackEditTextView = findViewById(R.id.edit_feedback_feedback);
        EditText additionalInformationEditTextView = findViewById(R.id.edit_feedback_additional_information);

        String userName = "Anonymous";

        if (!nameEditTextView.getText().toString().isEmpty())
            userName = nameEditTextView.getText().toString().trim();

        String feedback = feedbackEditTextView.getText().toString().trim();

        //feedback = feedbackEditTextView.getText().toString().trim();
        if(feedback.length() > 0)
            setFEEDBACK_FIELD_EMPTY(false);
        else
            setFEEDBACK_FIELD_EMPTY(true);

        String additionalInformation;
        additionalInformation = additionalInformationEditTextView.getText().toString().trim();

        String feedbackSummary = "Feedback:\n" + feedback;

        if (additionalInformation.length() != 0)
            additionalInformation += "\n\nAdditional Information:\n" + additionalInformation;

        feedbackSummary += additionalInformation;
        if (!isFEEDBACK_FIELD_EMPTY()) {
            sendmail(userName, feedbackSummary);
            finish();
        } else{
            Toast.makeText(this, "Feedback Field cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendmail(String userName, String feedback) {
        String addresses = "5abhisheksaxena@gmail.com";
        String[] mail = new String[]{addresses};
        String subject = "Feedback - " + userName;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, feedback);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public boolean isFEEDBACK_FIELD_EMPTY() {
        return FEEDBACK_FIELD_EMPTY;
    }

    public void setFEEDBACK_FIELD_EMPTY(boolean FEEDBACK_FIELD_EMPTY) {
        this.FEEDBACK_FIELD_EMPTY = FEEDBACK_FIELD_EMPTY;
    }
}

