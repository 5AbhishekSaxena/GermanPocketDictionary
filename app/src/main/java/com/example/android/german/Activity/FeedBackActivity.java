package com.example.android.german.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.german.R;

public class FeedBackActivity extends AppCompatActivity {

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

            case R.id.action_save:
                saveFeedback();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFeedback() {

        EditText nameEditTextView = findViewById(R.id.edit_feedback_user_name);
        EditText feedbackEditTextView = findViewById(R.id.edit_feedback_feedback);
        EditText additionalInformationEditTextView = findViewById(R.id.edit_feedback_additional_information);

        String userName = nameEditTextView.getText().toString().trim();
        String feedback = feedbackEditTextView.getText().toString().trim();
        String additionalInformation = "";
        additionalInformation += additionalInformationEditTextView.getText().toString().trim();

        String feedbackSummary = "Feedback:\n" + feedback;

        if(!additionalInformation.equals(""))
            additionalInformation += "\n\nAdditional Information:\n" + additionalInformation;

        feedbackSummary +=additionalInformation;
        sendmail(userName, feedbackSummary);
    }

    public void sendmail(String userName, String feedback) {
        //Log.v("MainActivity","mail func worked");
        String adresses = "test@gmail.com";
        String[] mail = new String[]{adresses};
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
}
