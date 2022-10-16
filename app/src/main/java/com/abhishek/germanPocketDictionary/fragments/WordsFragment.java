package com.abhishek.germanPocketDictionary.fragments;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.germanPocketDictionary.R;
import com.abhishek.germanPocketDictionary.activity.MainActivity;
import com.abhishek.germanPocketDictionary.adapter.WordAdapter;
import com.abhishek.germanPocketDictionary.data.WordsRepository;
import com.abhishek.germanPocketDictionary.interfaces.OnWordClickListener;
import com.abhishek.germanPocketDictionary.utilities.Constants;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

@AndroidEntryPoint
public class WordsFragment extends Fragment implements OnWordClickListener {

    public int selectedNoun;
    public int selectedVerb;

    private WordsViewModel wordsViewModel;

    private WordAdapter mAdapter;

    private String fragmentType;

    @Inject
    WordsRepository wordsRepository;

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
        RecyclerView wordRecyclerView = rootView.findViewById(R.id.list);

        MainActivity activityReference = ((MainActivity) getActivity());

        // Create a new adapter that takes an empty list of words as input
        if (getArguments() != null && activityReference != null) {
            fragmentType = getArguments().getString(Constants.API_KEYS.FRAGMENT_TYPE);
            if (fragmentType != null) {

                mAdapter = createAdapter(this, getContext(), fragmentType, this);

                if (fragmentType.equals(Constants.API_KEYS.CATEGORY_NOUNS))
                    selectedNoun = -1;

                if (fragmentType.equals(Constants.API_KEYS.CATEGORY_VERBS))
                    selectedVerb = -1;
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        wordRecyclerView.setLayoutManager(manager);
        wordRecyclerView.canScrollVertically(LinearLayout.VERTICAL);
        wordRecyclerView.setAdapter(mAdapter);

        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView loadingTextView = rootView.findViewById(R.id.loading_text_view);
        loadingTextView.setVisibility(View.GONE);

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WordsViewModel.Factory wordsViewModelFactory = new WordsViewModel.Factory(wordsRepository, fragmentType);
        wordsViewModel = ViewModelProviders.of(this, wordsViewModelFactory).get(WordsViewModel.class);

        wordsViewModel.fetchWordsByCategory();
        observeToWords();
    }

    private void observeToWords() {
        wordsViewModel.getWords().observe(getViewLifecycleOwner(), words -> mAdapter.submitList(words));
    }

    private WordAdapter createAdapter(WordsFragment fragment,
                                      Context context,
                                      String category,
                                      OnWordClickListener onWordClickListener) {
        return new WordAdapter(fragment, context, category, onWordClickListener);
    }

    @Override
    public void onWordClickListener(int position, View view) {
        Log.d(TAG, "onWordClickListener: clicked, position : " + position);

        if (getArguments() != null) {
            fragmentType = getArguments().getString(Constants.API_KEYS.FRAGMENT_TYPE);

            if (fragmentType == null) return;

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
