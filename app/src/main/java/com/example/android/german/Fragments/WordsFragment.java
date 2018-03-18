package com.example.android.german.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.german.Activity.MainActivity;
import com.example.android.german.Adapter.WordAdapter;
import com.example.android.german.R;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordsFragment extends Fragment {

    final static String LOG_TAG = WordsFragment.class.getName();

    //private static final String GERMAN_EXCELSHEET_URL =
    //      "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";

    private static final String GERMAN_EXCELSHEET_URL =
            "https://spreadsheets.google.com/feeds/list/1jZFNioSCd23081WAzWU5zl-rmJwczaGTUwlA_AXq9rs/od6/public/values?alt=json";
    private static final int GERMAN_LOADER_ID = 1;

    public static final int ALL_WORDS = 1;
    public static final int NOUNS = 2;
    public static final int VERBS = 3;
    public static final int NUMBERS = 4;
    public static final int COLORS = 5;
    public static final int QUESTIONS = 6;

    public int selectedNoun;
    public int selectedVerb;

    private WordAdapter mAdapter;

    private View rootView;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private MainActivity activityReference;
    private int fragmentType;

    public static WordsFragment newInstance(int type) {
        WordsFragment wordFragment = new WordsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentType", type);
        wordFragment.setArguments(bundle);
        return wordFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.word_list, container, false);
        // Find a reference to the {@link ListView} in the layout
        ListView wordListView = rootView.findViewById(R.id.list);

        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        wordListView.setEmptyView(mEmptyStateTextView);
        activityReference = ((MainActivity) getActivity());

        // Create a new adapter that takes an empty list of words as input
        if (getArguments() != null) {
            fragmentType = getArguments().getInt("fragmentType");
            switch (fragmentType) {
                case ALL_WORDS:
                    mAdapter = new WordAdapter(this, activityReference.getAllWordList(), ALL_WORDS);
                    break;

                case NOUNS:
                    mAdapter = new WordAdapter(this, activityReference.getNounList(), NOUNS);
                    selectedNoun = -1;
                    wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ViewGroup mainView = rootView.findViewById(R.id.main_view);
                            TransitionManager.beginDelayedTransition(mainView, new ChangeBounds());
                            if (selectedNoun != position) {
                                view.findViewById(R.id.expandable_view).setVisibility(View.VISIBLE);
                                selectedNoun = position;
                            } else {
                                view.findViewById(R.id.expandable_view).setVisibility(View.GONE);
                                selectedNoun = -1;
                            }

                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    break;

                case VERBS:
                    mAdapter = new WordAdapter(this, activityReference.getVerbList(), VERBS);
                    selectedVerb = -1;
                    wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ViewGroup mainView = rootView.findViewById(R.id.main_view);
                            TransitionManager.beginDelayedTransition(mainView, new ChangeBounds());
                            if (selectedVerb != position) {
                                view.findViewById(R.id.expandable_view).setVisibility(View.VISIBLE);
                                selectedVerb = position;
                            } else {
                                view.findViewById(R.id.expandable_view).setVisibility(View.GONE);
                                selectedVerb = -1;
                            }

                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    break;

                case NUMBERS:
                    mAdapter = new WordAdapter(this, activityReference.getNumberList(), NUMBERS);
                    break;

                case COLORS:
                    mAdapter = new WordAdapter(this, activityReference.getColorList(), COLORS);
                    break;

                case QUESTIONS:
                    mAdapter = new WordAdapter(this, activityReference.getQuestionList(), QUESTIONS);

            }
        }

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        wordListView.setAdapter(mAdapter);


        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        /*// Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
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

        }*/
        return rootView;

    }


    /*@Override
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
                    if (word.getmCategory().contains("1"))
                        wordsList.add(word);
                    Log.v(LOG_TAG, "Item Added: " + word.getmEnglishTranslation());
                }
            }
            //words.add(new Word("Uhr", "Clock", 2));
            //words.add(new Word("Bleistift","Pencil", 1 ));

            //sort the wordsList
            Collections.sort(wordsList, new Comparator<Word>() {
                @Override
                public int compare(Word o1, Word o2) {
                    return o1.getmGermanTranslationWithoutArticle().compareTo(o2.getmGermanTranslationWithoutArticle());
                }
            });

            mAdapter.addAll(wordsList);

            Log.v(LOG_TAG, "OnFinishedLoad status : " + words.size());
        } else {
            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_words);
            Toast.makeText(getActivity(), R.string.no_words, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        mAdapter.clear();
    }
*/
}
