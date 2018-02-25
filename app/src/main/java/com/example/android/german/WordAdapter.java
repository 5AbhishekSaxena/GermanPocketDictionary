package com.example.android.german;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Activity context, List<Word> words){

        super(context,0, words);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

    View listItemView = convertView;

        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);

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
        return listItemView;
    }
}
