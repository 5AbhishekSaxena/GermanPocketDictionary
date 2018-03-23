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

public class SearchResultActivity extends AppCompatActivity {

    List<Word> allWordsList;
    WordAdapter mAdapter;
    ListView listView;
    SearchView searchView;
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
                  View view = findViewById(R.id.search_view);
                  InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                  inputManager.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
              }else {
                    searchState = !EMPTY;
                }
                mAdapter.getFilter().filter(newText);
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

}