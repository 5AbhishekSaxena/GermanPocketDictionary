package com.example.android.germanPocketDictionary.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.germanPocketDictionary.Data.Word;
import com.example.android.germanPocketDictionary.Fragments.WordsFragment;
import com.example.android.germanPocketDictionary.R;

import java.util.List;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private WordsFragment fragment;
    private int mFragmentType;

    public WordAdapter(Fragment context, List<Word> words, int fragmentType) {
        super(context.getActivity(), 0, words);
        fragment = (WordsFragment) context;
        mFragmentType = fragmentType;
    }

    public WordAdapter(Activity context, List<Word> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        Word currentWord = getItem(position);


        //Log.v("WordAdapter", "Current Word: " + getItem(position));

        //Locate the TextView in the list_item
        TextView germanTextView = listItemView.findViewById(R.id.german_text_view);

        //Get the German Translation from the Word Class and update the list_item
        germanTextView.setText(currentWord.getmGermanTranslation());

        //Locate the TextView in the list_item
        TextView englishTextView = listItemView.findViewById(R.id.english_text_view);

        //Get the English Translation from the Word Class and update the list_item
        englishTextView.setText(currentWord.getmEnglishTranslation());

        if (currentWord.hasPlural()) {
            TextView pluralTextView = listItemView.findViewById(R.id.plural_text);
            pluralTextView.setText(currentWord.getmGermanPlural());
        }

        if (currentWord.hasOpposite()) {
            TextView oppositetextView = listItemView.findViewById(R.id.opposite_text);
            TextView oppositeLabelTextView = listItemView.findViewById(R.id.opposite_text_label);

            oppositetextView.setText(currentWord.getmGermanOpposite());
            oppositetextView.setVisibility(View.VISIBLE);
            oppositeLabelTextView.setVisibility(View.VISIBLE);

        }

        View view = listItemView.findViewById(R.id.expandable_view);
        ImageView arrowImageView = listItemView.findViewById(R.id.arrow);

        if (mFragmentType == WordsFragment.NOUNS) {
            arrowImageView.setVisibility(View.VISIBLE);
            if (fragment.selectedNoun == position) {
                view.setVisibility(View.VISIBLE);
                arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
            } else {
                view.setVisibility(View.GONE);
                arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
            }
        }

        if (mFragmentType == WordsFragment.VERBS) {
            arrowImageView.setVisibility(View.VISIBLE);
            if (fragment.selectedVerb == position) {
                arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                view.setVisibility(View.VISIBLE);
                TextView rootWordLabelTextView = view.findViewById(R.id.plural_text_label);
                rootWordLabelTextView.setText(R.string.root_word_label);
                TextView rootWord = view.findViewById(R.id.plural_text);
                rootWord.setText(currentWord.getmVerbRootWord());
            } else {
                view.setVisibility(View.GONE);
                arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
            }
        }
        return listItemView;
    }
}