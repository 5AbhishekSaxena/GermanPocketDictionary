package com.abhishek.germanPocketDictionary.adapter;

import android.content.Context;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abhishek.germanPocketDictionary.fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.R;

import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_COLORS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NOUNS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NUMBERS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_OPPOSITE;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_QUESTIONS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_VERBS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.TABLES.ALL_WORDS;
import static com.abhishek.germanPocketDictionary.utilities.Utils.getFragmentLocationFromCategory;

/**
 * Created by Abhishek Saxena on 12/10/2017.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;

    public CategoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return WordsFragment.newInstance(ALL_WORDS);
        else if (position == 1)
            return WordsFragment.newInstance(CATEGORY_NOUNS);
        else if (position == 2)
            return WordsFragment.newInstance(CATEGORY_VERBS);
        else if (position == 3 )
                return WordsFragment.newInstance(CATEGORY_NUMBERS);
        else if (position == 4)
            return WordsFragment.newInstance(CATEGORY_COLORS);
        else if (position == 5)
            return WordsFragment.newInstance(CATEGORY_QUESTIONS);
        else
            return WordsFragment.newInstance(CATEGORY_OPPOSITE);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == getFragmentLocationFromCategory(ALL_WORDS))
            return mContext.getString(R.string.category_all_words);
        else if (position == getFragmentLocationFromCategory(CATEGORY_NOUNS))
            return mContext.getString(R.string.category_nouns);
        else if (position == getFragmentLocationFromCategory(CATEGORY_VERBS))
            return mContext.getString(R.string.category_verbs);
        else if (position == getFragmentLocationFromCategory(CATEGORY_NUMBERS))
            return mContext.getString(R.string.category_numbers);
         else if (position == getFragmentLocationFromCategory(CATEGORY_COLORS))
             return mContext.getString(R.string.category_colors);
         else if (position == getFragmentLocationFromCategory(CATEGORY_QUESTIONS))
             return mContext.getString(R.string.category_questions);
         else
             return mContext.getString(R.string.category_opposite);

    }
}

