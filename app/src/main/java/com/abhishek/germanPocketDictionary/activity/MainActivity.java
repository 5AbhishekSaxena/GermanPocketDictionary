package com.abhishek.germanPocketDictionary.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.adapter.CategoryPagerAdapter;
import com.abhishek.germanPocketDictionary.firebase.FirebaseHandler;
import com.abhishek.germanPocketDictionary.model.Word;
import com.abhishek.germanPocketDictionary.utilities.ConnectionUtils;
import com.abhishek.germanPocketDictionary.utilities.Constants;
import com.abhishek.germanPocketDictionary.utilities.SharedPreferenceManager;
import com.abhishek.germanPocketDictionary.utilities.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_COLORS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NOUNS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NUMBERS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_OPPOSITE;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_QUESTIONS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_VERBS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.PREF_AGREEMENT_KEY;
import static com.abhishek.germanPocketDictionary.utilities.Utils.getFragmentLocationFromCategory;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public List<Word> allWordsList;

    private final String LOG_TAG = getClass().getName();

    private DrawerLayout mDrawerLayout;

    /**
     * TextView that is displayed when the list is empty
     */

    private TextView loadingTextView;
    private ProgressBar progressBar;

    private boolean MENU_ITEM_HIDE = true;

    private AlertDialog alertDialog;

    private ViewPager viewPager;

    private Bundle savedInstanceState;

    private DatabaseReference wordsReference;
    private ValueEventListener wordsValueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupAgreementAlertDialog();
    }

    private void setupAgreementAlertDialog() {
        String agreement = null;
        try {
            agreement = fetchAgreementDetails();
        } catch (IOException e) {
            agreementLoadingFailed();
        }

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        boolean agreed = sharedPreferences.getBoolean(PREF_AGREEMENT_KEY, false);
        if (sharedPreferences.contains("agreed")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("agreed");
            editor.apply();
        }

        if (agreement != null) {
            @SuppressLint("InflateParams") View alertDialogLayout = getLayoutInflater().inflate(R.layout.agreement_alertbox_layout, null);
            TextView agreementTextView = alertDialogLayout.findViewById(R.id.agreement_details);
            agreementTextView.setText(agreement);
            if (!agreed) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.agreement_title)
                        .setView(alertDialogLayout)
                        .setPositiveButton("I Agree", (dialog, which) -> {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(PREF_AGREEMENT_KEY, true);
                            editor.apply();
                            setupNavDrawer();
                            setupInterface();
                        })
                        .setNegativeButton("I Disagree", (dialog, which) -> {
                            Toast.makeText(this, "Application is Closing", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAndRemoveTask();
                            } else
                                finishAffinity();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(PREF_AGREEMENT_KEY, false);
                            editor.apply();
                        })
                        .setCancelable(false);

                alertDialog = builder.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                CheckBox checkBox = alertDialogLayout.findViewById(R.id.agreement_checkbox);
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked)
                        updateAgreementStatus(true);
                    else
                        updateAgreementStatus(false);
                });
            } else {
                setupNavDrawer();
                setupInterface();
            }
        } else {
            agreementLoadingFailed();
        }
    }

    private String fetchAgreementDetails() throws IOException {

        StringBuilder agreement = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier("com.abhishek.germanPocketDictionary:raw/terms_of_use_warranties_and_release_agreement", null, null));

            //noinspection ConstantConditions
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    agreement.append("\n");
                    agreement.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return agreement.toString();
        } catch (Exception e) {
            throw new IOException();
        }
    }

    private void agreementLoadingFailed() {
        Toast.makeText(this, "Agreement failed to load, contact the developer", Toast.LENGTH_LONG).show();
        hideProgressBarAndShowTextView(R.string.agreement_failed_to_load);
    }

    public void updateAgreementStatus(Boolean status) {
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(status);
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

    public void setupInterface() {
        allWordsList = new ArrayList<>();

        attachValueEventListener();

        SharedPreferenceManager.getInstance(this).registerOnSharedPreferenceChangeListener(this);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.TABLES.ALL_WORDS)) {
            Log.d(LOG_TAG, "fetching json from savedInstanceBundle..");
            String json = savedInstanceState.getString(Constants.TABLES.ALL_WORDS);
            if (json != null && !json.equals("")) {
                allWordsList.clear();
                allWordsList.addAll(Utils.getWordListFromJson(json));
                Log.d(LOG_TAG, "successfully fetched data from savedInstanceBundle");
                updateUI();
            } else
                fetchWordsFromPreference();
        } else
            fetchWordsFromPreference();

    }

    private void fetchWordsFromPreference() {
        showProgressBarAndLoadingTextView();
        Log.d(LOG_TAG, "fetching words from the pref...");
        SharedPreferenceManager prefManager = SharedPreferenceManager.getInstance(this);
        if (prefManager.contains(Constants.TABLES.ALL_WORDS)) {
            List<Word> words = prefManager.getListFromPreference(Constants.TABLES.ALL_WORDS);
            if (words == null || words.isEmpty()) {
                Log.d(LOG_TAG, "fetching words from db - words in pref is empty/null");
                fetchWordsFromDb();
            } else {
                Log.d(LOG_TAG, "Successfully fetched words from the pref.");
                allWordsList.clear();
                allWordsList.addAll(words);
                updateUI();
            }
        } else {
            Log.d(LOG_TAG, "words not found in shared preferences");
            fetchWordsFromDb();
        }
    }

    private void fetchWordsFromDb() {
        if (allWordsList == null)
            allWordsList = new ArrayList<>();

        Log.d(LOG_TAG, "fetching words from the firebase...");
        ConnectionUtils connectionUtils = new ConnectionUtils(this);

        if (!connectionUtils.hasInternetAccess())
            noInternetConnection();

        if (wordsReference == null)
            wordsReference = FirebaseHandler.getInstance().getWordsReference();

        wordsReference.addListenerForSingleValueEvent(getWordsValueEventListener());
    }

    private void attachValueEventListener() {
        if (wordsReference == null)
            wordsReference = FirebaseHandler.getInstance().getWordsReference();

        wordsReference.addValueEventListener(getWordsValueEventListener());
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

    private ValueEventListener getWordsValueEventListener() {
        if (wordsValueEventListener == null) {
            wordsValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allWordsList.clear();
                    for (DataSnapshot wordsSnapshot : dataSnapshot.getChildren()) {
                        Word word = wordsSnapshot.getValue(Word.class);
                        if (word != null) {
                            allWordsList.add(word);
                        }
                    }

                    if (allWordsList != null && !allWordsList.isEmpty()) {
                        Log.d(LOG_TAG, "words successfully fetched from the db");
                        Log.d(LOG_TAG, "updating preferences....");
                        SharedPreferenceManager prefManager = SharedPreferenceManager
                                .getInstance(MainActivity.this);
                        prefManager.setList(Constants.TABLES.ALL_WORDS, allWordsList);
                        hideProgressbarAndLoadingTextView();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Failed to load data, please try again!", Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, "Firebase onCancelled - Failed to load data, please try again!");
                    hideProgressBarAndShowTextView(R.string.error_loading_data);
                }
            };
        }
        return wordsValueEventListener;
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(LOG_TAG, "preference updated");
        fetchWordsFromPreference();
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
        SharedPreferenceManager.getInstance(this).unregisterOnSharedPreferenceChangeListener(this);
        if (wordsReference != null && wordsValueEventListener != null) {
            wordsReference.removeEventListener(wordsValueEventListener);
        }
    }
}