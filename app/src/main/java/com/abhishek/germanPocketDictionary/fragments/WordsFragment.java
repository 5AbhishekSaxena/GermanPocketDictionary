package com.abhishek.germanPocketDictionary.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.activity.MainActivity;
import com.abhishek.germanPocketDictionary.adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.interfaces.OnWordClickListener;
import com.abhishek.germanPocketDictionary.utilities.Constants;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordsFragment extends Fragment implements OnWordClickListener {

    public int selectedNoun;
    public int selectedVerb;

    private RecyclerView.Adapter mAdapter;

    private String fragmentType;

    public static WordsFragment newInstance(String type) {
        WordsFragment wordFragment = new WordsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.API_KEYS.FRAGMENT_TYPE, type);
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
            fragmentType = getArguments().getString(Constants.API_KEYS.FRAGMENT_TYPE);
            if (fragmentType != null) {
                switch (fragmentType) {
                    case Constants.TABLES.ALL_WORDS:
                        mAdapter = new WordAdapter(this, getContext(), /*activityReference.getAllWordsList(),*/ Constants.TABLES.ALL_WORDS, this);
                        break;

                    case Constants.API_KEYS.CATEGORY_NOUNS:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getNounList(),  Constants.API_KEYS.CATEGORY_NOUNS, this);
                        selectedNoun = -1;
                        break;

                    case Constants.API_KEYS.CATEGORY_VERBS:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getVerbList(), Constants.API_KEYS.CATEGORY_VERBS, this);
                        selectedVerb = -1;
                        break;

                    case Constants.API_KEYS.CATEGORY_NUMBERS:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getNumberList(), Constants.API_KEYS.CATEGORY_NUMBERS, this);
                        break;

                    case Constants.API_KEYS.CATEGORY_COLORS:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getColorList(), Constants.API_KEYS.CATEGORY_COLORS, this);
                        break;

                    case Constants.API_KEYS.CATEGORY_QUESTIONS:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getQuestionList(), Constants.API_KEYS.CATEGORY_QUESTIONS, this);
                        break;

                    case Constants.API_KEYS.CATEGORY_OPPOSITE:
                        mAdapter = new WordAdapter(this, getContext(), activityReference.getOppositeList(), Constants.API_KEYS.CATEGORY_OPPOSITE, this);
                        break;
                }
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
            fragmentType = getArguments().getString(Constants.API_KEYS.FRAGMENT_TYPE);

            switch (fragmentType) {
                case Constants.API_KEYS.CATEGORY_NOUNS:
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
                case Constants.API_KEYS.CATEGORY_VERBS:
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
