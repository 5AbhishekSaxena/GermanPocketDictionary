package com.example.android.german.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.german.Fragments.AllWordsFragment;
import com.example.android.german.R;

/**
 * Created by Abhishek Saxena on 2/28/2018.
 */

public class AllWordsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new AllWordsFragment())
                .commit();
    }
}
