package com.example.android.german.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.german.Loader.GermanLoader;
import com.example.android.german.R;
import com.example.android.german.Data.Word;
import com.example.android.german.Adapter.WordAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class NumberFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Word>> {

    final static String LOG_TAG = NumberFragment.class.getName();

    //private static final String GERMAN_EXCELSHEET_URL =
    //      "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";

    private static final String GERMAN_EXCELSHEET_URL =
            "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";

    private static final int GERMAN_LOADER_ID = 1;

    private WordAdapter mAdapter;

    private View rootView;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.word_list, container, false);
        // Find a reference to the {@link ListView} in the layout
        ListView wordListView =  rootView.findViewById(R.id.list);

        mEmptyStateTextView =  rootView.findViewById(R.id.empty_view);
        wordListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new WordAdapter(getActivity(), new ArrayList<Word>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        wordListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo!=null && networkInfo.isConnectedOrConnecting() ) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(GERMAN_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
            loadingTextView.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();

        }
        return rootView;

    }


    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
        return new GermanLoader(getActivity(), GERMAN_EXCELSHEET_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> words) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        mAdapter.clear();

        if (words != null && !words.isEmpty()) {

            List<Word> wordsList = new ArrayList<>();

            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                Log.v(LOG_TAG, "Current Word Category: " + word.getmCategory());
                if (word.getmCategory() != null) {
                    if (word.getmCategory().contains("2"))
                        wordsList.add(word);
                    Log.v(LOG_TAG, "Item Added: " + word.getmEnglishTranslation());
                }
            }
            //words.add(new Word("Uhr", "Clock", 2));
            //words.add(new Word("Bleistift","Pencil", 1 ));

            //Sort ArrayList
            Collections.sort(wordsList, (s1, s2) ->
                    Integer.compare(s1.getmNumber(), s2.getmNumber()));
            mAdapter.addAll(wordsList);

            Log.v(LOG_TAG, "OnFinishedLoad status : " + words.size());
        }else{
            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_words);
            Toast.makeText(getActivity(), R.string.no_words, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        mAdapter.clear();
    }

}
