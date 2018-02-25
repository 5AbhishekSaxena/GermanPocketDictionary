package com.example.android.german;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Abhishek Saxena on 12/15/2017.
 */

public class NumberFragment extends Fragment {

    public NumberFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("eins", "One"));
        words.add(new Word("zwei", "Two"));

        WordAdapter wordAdapter = new WordAdapter(getActivity(),words);

        ListView listView =  rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);


        return rootView;
    }
}
