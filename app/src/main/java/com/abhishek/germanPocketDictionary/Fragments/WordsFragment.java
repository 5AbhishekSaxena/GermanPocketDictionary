package com.abhishek.germanPocketDictionary.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.Activity.MainActivity;
import com.abhishek.germanPocketDictionary.Adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordsFragment extends Fragment implements WordAdapter.OnWordClickListener {

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

    private int fragmentType;

    public static WordsFragment newInstance(int type) {
        WordsFragment wordFragment = new WordsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentType", type);
        wordFragment.setArguments(bundle);
        return wordFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        // Find a reference to the {@link ListView} in the layout
        //ListView wordListView = rootView.findViewById(R.id.list);
        RecyclerView wordListViewR = rootView.findViewById(R.id.list);

        MainActivity activityReference = ((MainActivity) getActivity());

        // Create a new adapter that takes an empty list of words as input
        if (getArguments() != null && activityReference != null) {
            fragmentType = getArguments().getInt("fragmentType");
            switch (fragmentType) {
                case ALL_WORDS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getAllWordList(), ALL_WORDS, this);
                    break;

                case NOUNS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getNounList(), NOUNS, this);
                    selectedNoun = -1;

                    break;

                case VERBS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getVerbList(), VERBS, this);
                    selectedVerb = -1;

                    break;

                case NUMBERS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getNumberList(), NUMBERS, this);
                    break;

                case COLORS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getColorList(), COLORS, this);
                    break;

                case QUESTIONS:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getQuestionList(), QUESTIONS, this);
                    break;

                case OPPOSITE:
                    mAdapter = new WordAdapter(this, getContext(), activityReference.getOppositeList(), OPPOSITE, this);
                    break;

            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        wordListViewR.setLayoutManager(manager);
        wordListViewR.canScrollVertically(LinearLayout.VERTICAL);
        wordListViewR.setAdapter(mAdapter);


        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        return rootView;

    }

    @Override
    public void onWordClickListener(int position, View view) {
        Log.d(TAG, "onWordClickListener: clicked, position : " + position);

        if (getArguments() != null) {
            fragmentType = getArguments().getInt("fragmentType");

            switch (fragmentType) {
                case NOUNS:
                    if (getActivity() != null) {
                        //ViewGroup mainView = rootView.findViewById(R.id.main_view);
                        ImageView arrowImageView = view.findViewById(R.id.arrow);
                        TransitionManager.beginDelayedTransition((ViewGroup) view, new ChangeBounds());
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
                    break;
                case VERBS:
                    if (selectedVerb != position) {
                        view.findViewById(R.id.expandable_view).setVisibility(View.VISIBLE);
                        selectedVerb = position;
                    } else {
                        view.findViewById(R.id.expandable_view).setVisibility(View.GONE);
                        selectedVerb = -1;
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
