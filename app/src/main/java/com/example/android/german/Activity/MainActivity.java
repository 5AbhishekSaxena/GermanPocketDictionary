package com.example.android.german.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.german.Adapter.CategoryPagerAdapter;
import com.example.android.german.Data.Word;
import com.example.android.german.Loader.GermanLoader;
import com.example.android.german.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    public List<Word> allWordList, nounList, verbList, numberList, colorList, questionList;


    final static String LOG_TAG = MainActivity.class.getName();

    private static final String GERMAN_EXCELSHEET_URL =
    "https://spreadsheets.google.com/feeds/list/1hk9Y8QILoh-GzpcqfqMoodkrlLmG6CJ5xApvsKFDs_o/od6/public/values?alt=json";

    //private static final String GERMAN_EXCELSHEET_URL =
      //      "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";

    private static final int GERMAN_LOADER_ID = 1;


    /**
     * TextView that is displayed when the list is empty
     */
    private TextView loadingTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        nounList = new ArrayList<>();
        numberList = new ArrayList<>();
        colorList = new ArrayList<>();
        allWordList = new ArrayList<>();
        verbList = new ArrayList<>();
        questionList = new ArrayList<>();
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
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //View loadingIndicator = findViewById(R.id.loading_indicator);
            //loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            //mEmptyStateTextView.setText(R.string.no_internet_connection);
            mProgressBar.setVisibility(View.GONE);
            loadingTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        return new GermanLoader(this, GERMAN_EXCELSHEET_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> words) {
        mProgressBar.setVisibility(View.GONE);

        if (words != null && !words.isEmpty()) {
            loadingTextView.setVisibility(View.GONE);
           /* //WordsFragment nounsFragment = (WordsFragment) getSupportFragmentManager().findFragmentByTag("nouns_fragment_tag");
            WordsFragment nounsFragment = (WordsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
            if(nounsFragment != null)
                nounsFragment.updateList(words);

            NumberFragment numberFragment = (NumberFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);;
            if(numberFragment != null)
                numberFragment.updateList(words);*/

            nounList.clear();
            numberList.clear();
            colorList.clear();
            allWordList.clear();
            verbList.clear();
            questionList.clear();
            allWordList.addAll(words);

            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                Log.v(LOG_TAG, "Current Word Category: " + word.getmCategory());
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

                    Log.v(LOG_TAG, "Item Added: " + word.getmEnglishTranslation());
                }
            }

            //sort the AllWOrdsList
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

            Log.v(LOG_TAG, "OnFinishedLoad status : " + words.size());
        } else {
            loadingTextView.setText(R.string.no_words);
            //Toast.makeText(this, R.string.no_words, Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            case R.id.search_menu:
                Intent searchActivityIntent = new Intent(this, SearchResultActivity.class);
                //Bundle arrayListBundle = new Bundle();
                //arrayListBundle.putSerializable("AllWordArrayList",(Serializable) allWordList);
                searchActivityIntent.putExtra("AllWordArrayList", (ArrayList<Word>) allWordList);
                startActivity(searchActivityIntent);
                break;

            case R.id.rate_this_app:
                //TODO:Make PlayStore intent
                Toast.makeText(this, "Opening PlayStore - Pending", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Intent feedbackActivityIntent = new Intent(this,FeedBackActivity.class);
                startActivity(feedbackActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}