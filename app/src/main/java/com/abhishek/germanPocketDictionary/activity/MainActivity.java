package com.abhishek.germanPocketDictionary.activity;

import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_COLORS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NOUNS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NUMBERS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_OPPOSITE;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_QUESTIONS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_VERBS;
import static com.abhishek.germanPocketDictionary.utilities.Utils.getFragmentLocationFromCategory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.activity.feedback.ui.FeedBackActivity;
import com.abhishek.germanPocketDictionary.adapter.CategoryPagerAdapter;
import com.abhishek.germanPocketDictionary.data.WordsRepository;
import com.abhishek.germanPocketDictionary.model.Word;
import com.abhishek.germanPocketDictionary.utilities.Constants;
import com.abhishek.germanPocketDictionary.utilities.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public List<Word> allWordsList;

    private final String LOG_TAG = getClass().getName();

    private DrawerLayout mDrawerLayout;

    /**
     * TextView that is displayed when the list is empty
     */

    private TextView loadingTextView;
    private ProgressBar progressBar;

    private boolean MENU_ITEM_HIDE = true;

    private ViewPager viewPager;

    private Bundle savedInstanceState;

    @Inject
    WordsRepository wordsRepository;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupNavDrawer();

        allWordsList = new ArrayList<>();

        HomeViewModel.HomeViewModelFactory homeViewModelFactory = new HomeViewModel.HomeViewModelFactory(wordsRepository);
        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel.class);
        homeViewModel.getWords();

        homeViewModel.getAllWords().observe(this, words -> {
            allWordsList.clear();
            allWordsList.addAll(words);
            updateUI();
        });
    }

    private void setupNavDrawer() {
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null)
            setupDrawerContent(navigationView);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {
            new Handler().postDelayed(() -> {
                switch (item.getItemId()) {
                    case R.id.nav_all_words:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(Constants.TABLES.ALL_WORDS));
                        break;

                    case R.id.nav_nouns:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_NOUNS));
                        break;

                    case R.id.nav_verbs:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_VERBS));
                        break;

                    case R.id.nav_numbers:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_NUMBERS));
                        break;
                    case R.id.nav_colors:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_COLORS));
                        break;
                    case R.id.nav_questions:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_QUESTIONS));
                        break;
                    case R.id.nav_opposites:
                        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_OPPOSITE));
                        break;
                }
            }, 250);
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    private void updateUI() {
        hideProgressbarAndLoadingTextView();
        // Find the view pager that will allow the user to swipe between fragments
        viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(getFragmentLocationFromCategory(CATEGORY_NOUNS));
        MENU_ITEM_HIDE = false;
        invalidateOptionsMenu();
    }

    private void noInternetConnection() {
        hideProgressBarAndShowTextView(R.string.no_internet_connection);
    }

    private void showProgressBarAndLoadingTextView() {
        showProgressBar();
        showLoadingTextView(R.string.loading_text);

    }

    private void hideProgressBarAndShowTextView(@StringRes int stringRes) {
        hideProgressBar();
        showLoadingTextView(stringRes);
    }

    private void hideProgressbarAndLoadingTextView() {
        hideProgressBar();
        hideLoadingTextView();
    }

    private void hideLoadingTextView() {
        if (loadingTextView == null)
            loadingTextView = findViewById(R.id.loading_text_view);

        loadingTextView.setVisibility(View.GONE);
    }

    private void showLoadingTextView() {
        if (loadingTextView == null)
            loadingTextView = findViewById(R.id.loading_text_view);

        loadingTextView.setVisibility(View.VISIBLE);
    }

    private void showLoadingTextView(@StringRes int stringRes) {
        showLoadingTextView();
        loadingTextView.setText(stringRes);
    }

    private void hideProgressBar() {
        if (progressBar == null)
            progressBar = findViewById(R.id.loading_indicator);

        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        if (progressBar == null)
            progressBar = findViewById(R.id.loading_indicator);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);


        MenuItem searchItem = menu.findItem(R.id.search_menu);
        View navView = findViewById(R.id.nav_view);
        if (MENU_ITEM_HIDE) {
            searchItem.setVisible(false);//hide it
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            navView.setVisibility(View.GONE);
        } else {
            searchItem.setVisible(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            navView.setVisibility(View.VISIBLE);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.search_menu:
                Intent searchActivityIntent = new Intent(this, SearchResultActivity.class);
                searchActivityIntent.putExtra(Constants.TABLES.ALL_WORDS, (ArrayList<Word>) allWordsList);
                startActivity(searchActivityIntent);
                break;

            case R.id.menu_item_feedback:
                Intent feedbackActivityIntent = new Intent(this, FeedBackActivity.class);
                startActivity(feedbackActivityIntent);
                break;

            case R.id.menu_item_rate_this_app:
                Intent goToMarket = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                goToMarket.setData(Uri.parse("market://details?id=" + this.getPackageName()));
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                if (goToMarket.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(this, "Opening PlayStore", Toast.LENGTH_SHORT).show();
                    startActivity(goToMarket);
                } else
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(LOG_TAG, "onSaveInstanceState called");
        if (allWordsList != null) {
            Log.d(LOG_TAG, "saving all words list in outState");
            String json = Utils.getJsonFromList(allWordsList);
            outState.putString(Constants.TABLES.ALL_WORDS, json);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}