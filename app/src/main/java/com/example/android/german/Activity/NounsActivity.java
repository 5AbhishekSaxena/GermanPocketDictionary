package com.example.android.german.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.german.Fragments.NounsFragment;
import com.example.android.german.R;


/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class NounsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new NounsFragment())
                .commit();
    }
}
