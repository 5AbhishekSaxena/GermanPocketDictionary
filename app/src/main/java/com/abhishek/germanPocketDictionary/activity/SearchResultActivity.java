package com.abhishek.germanPocketDictionary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.model.Word;
import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchResultActivity extends AppCompatActivity {

    List<Word> allWordsList = null/*= (ArrayList<Word>) getIntent().getSerializableExtra("AllWordArrayList")*/;
    WordAdapter mAdapter;
    //ListView listView;
    RecyclerView recyclerListView;
    SearchView searchView;
    TextView mEmptyStateTextView;
    static boolean EMPTY = true;
    public static boolean searchState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerListView = findViewById(R.id.list);

        View progressBar = findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);

        TextView loadingTextView = findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        if (allWordsList == null)
            allWordsList = new ArrayList<>();

        allWordsList = (ArrayList<Word>) getIntent().getSerializableExtra(Constants.TABLES.WORDS);


        mEmptyStateTextView = findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.no_words);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerListView.setLayoutManager(manager);
        recyclerListView.canScrollVertically(LinearLayout.VERTICAL);
        recyclerListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mAdapter = new WordAdapter(this, allWordsList, null);
        recyclerListView.setAdapter(mAdapter);
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
                newText = newText.trim();
                if (newText.length() == 0) {
                    searchState = EMPTY;
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } else {
                    searchState = !EMPTY;
                }

                ArrayList<Word> filteredList = new ArrayList<>();

                if (newText.length() != 0) {
                    filteredList.clear();
                    filteredList = handleSearch(newText.trim());
                    if (!filteredList.isEmpty()) {
                        mAdapter = new WordAdapter(SearchResultActivity.this, filteredList, null);
                        recyclerListView.setVisibility(View.VISIBLE);
                        recyclerListView.setAdapter(mAdapter);
                        mEmptyStateTextView.setVisibility(View.GONE);
                    } else {
                        mEmptyStateTextView.setVisibility(View.VISIBLE);
                        recyclerListView.setVisibility(View.GONE);
                    }
                } else {
                    mAdapter = new WordAdapter(SearchResultActivity.this, allWordsList, null);
                    recyclerListView.setAdapter(mAdapter);
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
                NavUtils.navigateUpFromSameTask(this);
                return true;

           /* case R.id.search_menu:
                Intent searchIntent = new Intent(this, SearchResultActivity.class);
                startActivity(searchIntent);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Word> handleSearch(String query) {

        ArrayList<Word> filteredList = new ArrayList<>();
        Word currentWord;
        for (int i = 0; i < allWordsList.size(); i++) {
            currentWord = allWordsList.get(i);

            if (Pattern.compile(Pattern.quote(query),
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(currentWord.toString()).find()) {
                filteredList.add(currentWord);
            }
        }
        return filteredList;
    }
}