package com.example.android.german.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.german.Adapter.WordAdapter;
import com.example.android.german.Data.Word;
import com.example.android.german.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    List<Word> allWordsList;
    WordAdapter mAdapter;
    ListView listView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = findViewById(R.id.list);

        View progressBar = findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);

        TextView loadingTextView = findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);


        allWordsList = new ArrayList<>();
        allWordsList = (ArrayList<Word>) getIntent().getSerializableExtra("AllWordArrayList");

        mAdapter = new WordAdapter(this, allWordsList);
        listView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.search_menu);
        searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search German or English Word");
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        searchView.setLayoutParams(params);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                this.listView.setVisibility(View.GONE);
                return true;

            case R.id.search_menu:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }*/
}