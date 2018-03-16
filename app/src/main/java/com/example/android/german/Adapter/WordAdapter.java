package com.example.android.german.Adapter;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.german.Data.Word;
import com.example.android.german.Fragments.WordsFragment;
import com.example.android.german.R;

import java.util.List;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private WordsFragment fragment;
    private int fragmentType;

    public WordAdapter(Fragment context, List<Word> words, int fragmentType) {
        super(context.getActivity(), 0, words);
        fragment = (WordsFragment) context;
        this.fragmentType = fragmentType;
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

        View view = listItemView.findViewById(R.id.expandable_view);
        if (fragmentType == WordsFragment.NOUNS) {
            if (fragment.selectedNoun == position)
                view.setVisibility(View.VISIBLE);
            else
                view.setVisibility(View.GONE);
        }
        return listItemView;
    }
}
