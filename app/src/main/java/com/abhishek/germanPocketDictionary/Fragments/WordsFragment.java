package com.abhishek.germanPocketDictionary.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.Activity.MainActivity;
import com.abhishek.germanPocketDictionary.Adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.R;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordsFragment extends Fragment {

    public static final int ALL_WORDS = 0;
    public static final int NOUNS = 1;
    public static final int VERBS = 2;
    public static final int NUMBERS = 3;
    public static final int COLORS = 4;
    public static final int QUESTIONS = 5;
    public static final int OPPOSITE = 6;

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
                            ImageView arrowImageView = view.findViewById(R.id.arrow);
                            TransitionManager.beginDelayedTransition(mainView, new ChangeBounds());
                            if (selectedNoun != position) {
                                view.findViewById(R.id.expandable_view).setVisibility(View.VISIBLE);

                                arrowImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_keyboard_arrow_up_black_18dp));
                                selectedNoun = position;
                            } else {
                                arrowImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_keyboard_arrow_up_black_18dp));
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
                    break;

                case OPPOSITE:
                    mAdapter = new WordAdapter(this,activityReference.getOppositeList(), OPPOSITE);
                    break;

            }
        }

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        wordListView.setAdapter(mAdapter);


        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        return rootView;

    }
}
