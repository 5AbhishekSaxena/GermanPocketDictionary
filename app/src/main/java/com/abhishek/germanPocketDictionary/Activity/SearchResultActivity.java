package com.abhishek.germanPocketDictionary.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.Adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.Data.Word;
import com.abhishek.germanPocketDictionary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchResultActivity extends AppCompatActivity {

    List<Word> allWordsList;
    WordAdapter mAdapter;
    ListView listView;
    SearchView searchView;
    TextView mEmptyStateTextView;
    static boolean EMPTY = true;
    public static boolean searchState = EMPTY;

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


        mEmptyStateTextView = findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.no_words);

        mAdapter = new WordAdapter(this, allWordsList, searchState);
        listView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.search_menu);

        searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search German or English Word");
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    searchState = EMPTY;
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    searchState = !EMPTY;
                }

                ArrayList<Word> filteredList = new ArrayList<>();

                if (newText.length() != 0) {
                    filteredList.clear();
                    filteredList = handleSearch(newText);
                    if (!filteredList.isEmpty()) {
                        mAdapter = new WordAdapter(SearchResultActivity.this, filteredList, searchState);
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(mAdapter);
                        mEmptyStateTextView.setVisibility(View.GONE);
                    } else {
                        mEmptyStateTextView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } else {
                    mAdapter = new WordAdapter(SearchResultActivity.this, allWordsList, searchState);
                    listView.setAdapter(mAdapter);
                    mEmptyStateTextView.setVisibility(View.GONE);
                }

                mAdapter.notifyDataSetChanged();
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


    public List<Word> getAllWordsList() {
        return allWordsList;
    }

    private ArrayList<Word> handleSearch(String query) {

        ArrayList<Word> filteredList = new ArrayList<>();
        Word currentWord;
        for (int i = 0; i < allWordsList.size(); i++) {
            currentWord = allWordsList.get(i);

            if (Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE |Pattern.UNICODE_CASE).matcher(currentWord.toString()).find()) {
                filteredList.add(currentWord);
            }
        }
        return filteredList;
    }
}