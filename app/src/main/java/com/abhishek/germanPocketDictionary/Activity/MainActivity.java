package com.abhishek.germanPocketDictionary.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.germanPocketDictionary.Adapter.CategoryPagerAdapter;
import com.abhishek.germanPocketDictionary.Data.Word;
import com.abhishek.germanPocketDictionary.Fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.QueryUtils.VolleyCalls;
import com.abhishek.germanPocketDictionary.R;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    public List<Word> allWordList, nounList, verbList, numberList, colorList, questionList, oppositeList;


    private static final String GERMAN_EXCELSHEET_URL =
          "https://spreadsheets.google.com/feeds/list/1hk9Y8QILoh-GzpcqfqMoodkrlLmG6CJ5xApvsKFDs_o/od6/public/values?alt=json";

    private DrawerLayout mDrawerLayout;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView loadingTextView;
    private ProgressBar mProgressBar;

    private boolean MENU_ITEM_HIDE = true;

    private AlertDialog alertDialog;

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupAgreementAlertDialog();
    }


    public void setupInterface() {

        nounList = new ArrayList<>();
        numberList = new ArrayList<>();
        colorList = new ArrayList<>();
        allWordList = new ArrayList<>();
        verbList = new ArrayList<>();
        questionList = new ArrayList<>();
        oppositeList = new ArrayList<>();


        mProgressBar = findViewById(R.id.loading_indicator);
        loadingTextView = findViewById(R.id.loading_text_view);

        String strTag = "general_req";
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }


        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            StringRequest strReq = new StringRequest(GERMAN_EXCELSHEET_URL, response ->
                    updateUI(extractFeatureFromJson(response)), error -> {
                        mProgressBar.setVisibility(View.GONE);
                        loadingTextView.setText(getString(R.string.error_loading_data));
                    });

            VolleyCalls.getInstance().addToRequestQueue(strReq, strTag);
        } else {
            mProgressBar.setVisibility(View.GONE);
            loadingTextView.setText(R.string.no_internet_connection);
        }

    }

    private void updateUI(List<Word> words) {
        mProgressBar.setVisibility(View.GONE);

        if (words != null && !words.isEmpty()) {
            loadingTextView.setVisibility(View.GONE);

            nounList.clear();
            numberList.clear();
            colorList.clear();
            allWordList.clear();
            verbList.clear();
            questionList.clear();
            oppositeList.clear();

            allWordList.addAll(words);

            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                if (word.getmCategory() != null) {
                    if (word.getmCategory().contains("1"))
                        nounList.add(word);

                    if (word.getmCategory().contains("2"))
                        numberList.add(word);

                    if (word.getmCategory().contains("3"))
                        colorList.add(word);

                    if (word.getmCategory().contains("7"))
                        verbList.add(word);

                    if (word.getmCategory().contains("8"))
                        questionList.add(word);

                    if (word.hasOpposite()) {
                        if (oppositeList.isEmpty())
                            oppositeList.add(word);
                        else {
                            if (!Pattern.compile(Pattern.quote(word.getmEnglishTranslation()), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(oppositeList.toString()).find())
                                oppositeList.add(word);

                        }
                    }
                }
            }
            //sort the AllWordsList
            Collections.sort(allWordList, (s1, s2) ->
                    s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));

            //sort the wordsList
            Collections.sort(nounList, (s1, s2) ->
                    s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));

            //Sort numberList
            Collections.sort(numberList, (s1, s2) ->
                    Integer.compare(s1.getmNumber(), s2.getmNumber()));

            //Sort verbList
            Collections.sort(verbList, (s1, s2) ->
                    s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));

            //Sort questionList
            Collections.sort(questionList, (s1, s2) ->
                    s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));

            //Sort oppositeList
            Collections.sort(oppositeList, (s1, s2) ->
                    s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));


            // Find the view pager that will allow the user to swipe between fragments
            viewPager = findViewById(R.id.viewpager);
            // Create an adapter that knows which fragment should be shown on each page
            CategoryPagerAdapter adapter = new CategoryPagerAdapter(this, getSupportFragmentManager());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = findViewById(R.id.sliding_tabs);
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(WordsFragment.NOUNS);

            MENU_ITEM_HIDE = false;
            invalidateOptionsMenu();
        } else {
            loadingTextView.setText(R.string.no_internet_connection);
        }
    }

    private void setupAgreementAlertDialog() {
        String agreement = null;
        try {
            agreement = fetchAgreementDetails();
        } catch (IOException e) {
            agreementLoadingFailed();
        }

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean agreed = sharedPreferences.getBoolean("agreed", false);
        if (agreement != null) {

            @SuppressLint("InflateParams") View alertDialogLayout = this.getLayoutInflater().inflate(R.layout.agreement_alertbox_layout, null);
            TextView agreementTextView = alertDialogLayout.findViewById(R.id.agreement_details);
            agreementTextView.setText(agreement);
            if (!agreed) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.agreement_title)
                        .setView(alertDialogLayout)
                        .setPositiveButton("I Agree", (dialog, which) -> {
                            Editor editor = sharedPreferences.edit();
                            editor.putBoolean("agreed", true);
                            editor.apply();
                            setupNavDrawer();
                            setupInterface();
                        })
                        .setNegativeButton("I Disagree", (dialog, which) -> {
                            Toast.makeText(MainActivity.this, "Application is Closing", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAndRemoveTask();
                            } else
                                finishAffinity();

                            Editor editor = sharedPreferences.edit();
                            editor.putBoolean("agreed", false);
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

    private void agreementLoadingFailed(){
        Toast.makeText(this, "Agreement failed to load, contact the developer", Toast.LENGTH_LONG).show();
        mProgressBar.setVisibility(View.GONE);
        loadingTextView.setText(R.string.agreement_failed_to_load);

    }

    private void updateAgreementStatus(Boolean status) {

        if (status) {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        } else
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    private String fetchAgreementDetails() throws IOException {

        StringBuilder agreement = new StringBuilder();
        InputStream inputStream = getResources().openRawResource(
                getResources().getIdentifier("com.abhishek.germanPocketDictionary:raw/terms_of_use_warranties_and_release_agreement", null, null));

        //noinspection ConstantConditions
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                agreement.append("\n");
                agreement.append(line);
                line = bufferedReader.readLine();
            }
        }
        return agreement.toString();
    }

    public List<Word> getAllWordList() {
        return allWordList;
    }

    public List<Word> getNounList() {
        return nounList;
    }

    public List<Word> getNumberList() {
        return numberList;
    }

    public List<Word> getColorList() {
        return colorList;
    }

    public List<Word> getVerbList() {
        return verbList;
    }

    public List<Word> getQuestionList() {
        return questionList;
    }

    public List<Word> getOppositeList() {
        return oppositeList;
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
                searchActivityIntent.putExtra("AllWordArrayList", (ArrayList<Word>) allWordList);
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
                        viewPager.setCurrentItem(WordsFragment.ALL_WORDS);
                        break;

                    case R.id.nav_nouns:
                        viewPager.setCurrentItem(WordsFragment.NOUNS);
                        break;

                    case R.id.nav_verbs:
                        viewPager.setCurrentItem(WordsFragment.VERBS);
                        break;

                    case R.id.nav_numbers:
                        viewPager.setCurrentItem(WordsFragment.NUMBERS);
                        break;
                    case R.id.nav_colors:
                        viewPager.setCurrentItem(WordsFragment.COLORS);
                        break;
                    case R.id.nav_questions:
                        viewPager.setCurrentItem(WordsFragment.QUESTIONS);
                        break;
                    case R.id.nav_opposites:
                        viewPager.setCurrentItem(WordsFragment.OPPOSITE);
                        break;
                }
            }, 250);
            mDrawerLayout.closeDrawer(Gravity.START);
            return false;
        });
    }

    public ArrayList<Word> extractFeatureFromJson(String germanJson) {

        if (TextUtils.isEmpty(germanJson)) {
            return null;
        }

        ArrayList<Word> words = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(germanJson);
            JSONObject germanFeed = baseJsonResponse.getJSONObject("feed");
            JSONArray germanArray = germanFeed.getJSONArray("entry");

            for (int i = 0; i < germanArray.length(); i++) {
                JSONObject currentGerman = germanArray.getJSONObject(i);

                //JSONObject contentGerman = currentGerman.getJSONObject("content");
                //String contentGermanData = contentGerman.getString("$t");

                JSONObject germanTranslation = currentGerman.getJSONObject("gsx$germantranslation");
                String germanTranslationValue = String.valueOf(germanTranslation.getString("$t"));

                JSONObject englishTranslation = currentGerman.getJSONObject("gsx$englishtranslation");
                String englishTranslationValue = String.valueOf(englishTranslation.getString("$t"));

                JSONObject germanPlural = currentGerman.getJSONObject("gsx$plural");
                String germanPluralValue = germanPlural.getString("$t");

                JSONObject number = currentGerman.getJSONObject("gsx$numbervalue");
                String numberValue = number.getString("$t");

                JSONObject category = currentGerman.getJSONObject("gsx$category");
                String categoryValue = category.getString("$t");

                JSONObject verbRootWord = currentGerman.getJSONObject("gsx$verbrootword");
                String verbRootWordValue = verbRootWord.getString("$t");

                JSONObject verbPartizipII = currentGerman.getJSONObject("gsx$partizipii");
                String verbPartizipIIValue = verbPartizipII.getString("$t");

                JSONObject helpingVerb = currentGerman.getJSONObject("gsx$helpingverb");
                String helpingVerbValue = helpingVerb.getString("$t");

                JSONObject opposite = currentGerman.getJSONObject("gsx$opposite");
                String oppositeValue = opposite.getString("$t");

                JSONObject oppositeMeaning = currentGerman.getJSONObject("gsx$oppositemeaning");
                String oppositeMeaningValue = oppositeMeaning.getString("$t");

                Word word = new Word(germanTranslationValue, englishTranslationValue, germanPluralValue,
                        categoryValue, numberValue, verbRootWordValue, verbPartizipIIValue, helpingVerbValue,
                        oppositeValue, oppositeMeaningValue);

                words.add(word);
            }

        } catch (JSONException e) {
            Log.e(MainActivity.class.getName(), "Problem parsing the germanPocketDictionary sheets JSON results", e);
        }

        return words;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START);
        else
            super.onBackPressed();
    }


}