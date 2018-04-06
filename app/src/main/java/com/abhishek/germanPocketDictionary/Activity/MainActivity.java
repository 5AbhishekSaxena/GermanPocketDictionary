package com.abhishek.germanPocketDictionary.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.germanPocketDictionary.Adapter.CategoryPagerAdapter;
import com.abhishek.germanPocketDictionary.Data.Word;
import com.abhishek.germanPocketDictionary.Loader.GermanLoader;
import com.abhishek.germanPocketDictionary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    public List<Word> allWordList, nounList, verbList, numberList, colorList, questionList, oppositeList;


    //private static final String GERMAN_EXCELSHEET_URL =
      //      "https://spreadsheets.google.com/feeds/list/1hk9Y8QILoh-GzpcqfqMoodkrlLmG6CJ5xApvsKFDs_o/od6/public/values?alt=json";
    private static final String GERMAN_EXCELSHEET_URL_TEST =
            "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";

    private static final int GERMAN_LOADER_ID = 1;


    /**
     * TextView that is displayed when the list is empty
     */
    private TextView loadingTextView;
    private ProgressBar mProgressBar;

    private boolean MENU_ITEM_HIDE = true;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private View alertDialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


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

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(GERMAN_LOADER_ID, null, MainActivity.this);
        } else {

            mProgressBar.setVisibility(View.GONE);
            loadingTextView.setText(R.string.no_internet_connection);
        }

    }

    private void setupAgreementAlertDialog() {
        String agreement = null;
        try {
            agreement = fetchAgreementDetails();
        } catch (IOException e) {
        }

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean agreed = sharedPreferences.getBoolean("agreed", false);
        if (agreement != null) {

            alertDialogLayout = this.getLayoutInflater().inflate(R.layout.agreement_alertbox_layout, null);
            TextView agreementTextView = alertDialogLayout.findViewById(R.id.agreement_details);
            agreementTextView.setText(agreement);
            if (!agreed) {
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.agreement_title)
                        .setView(alertDialogLayout)
                        .setPositiveButton("I Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Editor editor = sharedPreferences.edit();
                                editor.putBoolean("agreed", true);
                                editor.apply();
                                setupInterface();
                            }
                        })
                        .setNegativeButton("I Disagree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Application is Closing", Toast.LENGTH_SHORT).show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    finishAndRemoveTask();
                                } else
                                    finishAffinity();

                                Editor editor = sharedPreferences.edit();
                                editor.putBoolean("agreed", false);
                                editor.apply();
                            }
                        })
                        .setCancelable(false);

                alertDialog = builder.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                CheckBox checkBox = alertDialogLayout.findViewById(R.id.agreement_checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked)
                            updateAgreementStatus(true);
                        else
                            updateAgreementStatus(false);
                    }
                });
            }else
                setupInterface();
        }else {
            Toast.makeText(this, "Agreement failed to load, contact the developer", Toast.LENGTH_LONG).show();
           mProgressBar.setVisibility(View.GONE);
           loadingTextView.setText(R.string.agreement_failed_to_load);
        }

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

    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        return new GermanLoader(this, GERMAN_EXCELSHEET_URL_TEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> words) {
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
                                if (!Pattern.compile(Pattern.quote(word.getmEnglishTranslation()), Pattern.CASE_INSENSITIVE |Pattern.UNICODE_CASE).matcher(oppositeList.toString()).find())
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
            Collections.sort(oppositeList, (s1,s2) ->
                s1.getmGermanTranslationWithoutArticle().compareTo(s2.getmGermanTranslationWithoutArticle()));


            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = findViewById(R.id.viewpager);
            // Create an adapter that knows which fragment should be shown on each page
            CategoryPagerAdapter adapter = new CategoryPagerAdapter(this, getSupportFragmentManager());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = findViewById(R.id.sliding_tabs);
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(1);

            MENU_ITEM_HIDE = false;
            invalidateOptionsMenu();
        } else {
            loadingTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
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
        if (MENU_ITEM_HIDE)
            searchItem.setVisible(false);   //hide it
        else
            searchItem.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
                }else
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));

        }
        return super.onOptionsItemSelected(item);
    }

}