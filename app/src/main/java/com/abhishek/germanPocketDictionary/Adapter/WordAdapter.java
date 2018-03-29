package com.abhishek.germanPocketDictionary.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.Activity.SearchResultActivity;
import com.abhishek.germanPocketDictionary.Data.Word;
import com.abhishek.germanPocketDictionary.Fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.R;

import java.util.List;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private WordsFragment fragment;
    private int mFragmentType;
    private boolean isActivity = false;
    private Word currentWord;
    private View listItemView;
    private View expandableView;
    private TextView partizipLabelTextView;
    private TextView partizipTextView;
    private TextView helpingVerbTextLabel;
    private TextView helpingVerbTextView;
    private boolean EMPTY = true;
    private boolean searchState = EMPTY;
    private TextView pluralTextView;
    private TextView oppositeTextView;
    private TextView oppositeLabelTextView;
    private TextView rootWordLabelTextView;
    private TextView rootWord;

    public WordAdapter(Fragment context, List<Word> words, int fragmentType) {
        super(context.getActivity(), 0, words);
        fragment = (WordsFragment) context;
        mFragmentType = fragmentType;
    }

    public WordAdapter(Activity context, List<Word> words, boolean searchState) {
        super(context, 0, words);
        isActivity = true;
        this.searchState = searchState;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        currentWord = getItem(position);

        //Locate the TextView in the list_item
        TextView germanTextView = listItemView.findViewById(R.id.german_text_view);

        //Get the German Translation from the Word Class and update the list_item
        germanTextView.setText(currentWord.getmGermanTranslation());

        //Locate the TextView in the list_item
        TextView englishTextView = listItemView.findViewById(R.id.english_text_view);

        //Get the English Translation from the Word Class and update the list_item
        englishTextView.setText(currentWord.getmEnglishTranslation());

        expandableView = listItemView.findViewById(R.id.expandable_view);
        pluralTextView = expandableView.findViewById(R.id.plural_text);
        rootWordLabelTextView = expandableView.findViewById(R.id.plural_text_label);
        rootWord = pluralTextView;
        partizipLabelTextView = expandableView.findViewById(R.id.opposite_text_label);
        partizipTextView = expandableView.findViewById(R.id.opposite_text);
        helpingVerbTextLabel = expandableView.findViewById(R.id.helping_text_label);
        helpingVerbTextView = expandableView.findViewById(R.id.helping_verb_text);
        oppositeLabelTextView = partizipLabelTextView;
        oppositeTextView = partizipTextView;

        if (!isActivity) {
            if (currentWord.hasPlural()) {
                viewPlural();
            }

            if (currentWord.hasOpposite()) {
                viewOpposite();
            }
            ImageView arrowImageView = listItemView.findViewById(R.id.arrow);

            if (mFragmentType == WordsFragment.NOUNS) {
                arrowImageView.setVisibility(View.VISIBLE);
                if (fragment.selectedNoun == position) {
                    expandableView.setVisibility(View.VISIBLE);
                    arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                } else {
                    expandableView.setVisibility(View.GONE);
                    arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                }
            }

            if (mFragmentType == WordsFragment.VERBS) {
                arrowImageView.setVisibility(View.VISIBLE);
                if (fragment.selectedVerb == position) {
                    arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                    expandableView.setVisibility(View.VISIBLE);
                    viewRootWord();

                    if (currentWord.hasPartizip()) {
                        viewPartizip();
                    }
                } else {
                    expandableView.setVisibility(View.GONE);
                    arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                }
            }

        } else {
            expandableView.setVisibility(View.GONE);

            if (expandableView != null) {
                if (SearchResultActivity.searchState != EMPTY) {
                    if (currentWord.getmCategory().contains("1")) {
                        expandableView.setVisibility(View.VISIBLE);


                        if (currentWord.hasPlural())
                            viewPlural();
                        if (currentWord.hasOpposite())
                            viewOpposite();
                    } else if (currentWord.getmCategory().contains("7")) {
                        expandableView.setVisibility(View.VISIBLE);
                        viewRootWord();
                        viewPartizip();
                    }
                }
            }
        }
        return listItemView;
    }

    private void viewPlural() {
        pluralTextView.setText(currentWord.getmGermanPlural());

        partizipLabelTextView.setVisibility(View.GONE);
        partizipTextView.setVisibility(View.GONE);
        helpingVerbTextLabel.setVisibility(View.GONE);
        helpingVerbTextView.setVisibility(View.GONE);


    }

    private void viewRootWord() {
        rootWordLabelTextView.setText(R.string.root_word_label);
        rootWord.setText(currentWord.getmVerbRootWord());

        partizipTextView.setVisibility(View.GONE);
        partizipLabelTextView.setVisibility(View.GONE);
    }


    private void viewPartizip() {
        if (currentWord.hasPartizip()) {
            partizipLabelTextView.setVisibility(View.VISIBLE);
            partizipLabelTextView.setText(R.string.partizip_label);
            partizipTextView.setVisibility(View.VISIBLE);
            partizipTextView.setText(currentWord.getmVerbPartizip());
            helpingVerbTextLabel.setVisibility(View.VISIBLE);
            helpingVerbTextView.setVisibility(View.VISIBLE);
            helpingVerbTextView.setText(currentWord.getmHelpingVerb());
        } else {
            partizipLabelTextView.setVisibility(View.GONE);
            partizipTextView.setVisibility(View.GONE);
            helpingVerbTextLabel.setVisibility(View.GONE);
            helpingVerbTextView.setVisibility(View.GONE);
        }
    }


    private void viewOpposite() {
        oppositeLabelTextView.setText(R.string.opposite_word_label);
        oppositeTextView.setText(currentWord.getmGermanOpposite());
        oppositeTextView.setVisibility(View.VISIBLE);
        oppositeLabelTextView.setVisibility(View.VISIBLE);
    }
}