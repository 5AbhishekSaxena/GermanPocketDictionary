package com.abhishek.germanPocketDictionary.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abhishek.germanPocketDictionary.Fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.R;

/**
 * Created by Abhishek Saxena on 12/10/2017.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;


    public CategoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return WordsFragment.newInstance(WordsFragment.ALL_WORDS);
        else if (position == 1)
            return WordsFragment.newInstance(WordsFragment.NOUNS);
        else if (position == 2)
            return WordsFragment.newInstance(WordsFragment.VERBS);
        else if (position == 3 )
                return WordsFragment.newInstance(WordsFragment.NUMBERS);
        else if (position == 4)
            return WordsFragment.newInstance(WordsFragment.COLORS);
        else if (position == 5)
            return WordsFragment.newInstance(WordsFragment.QUESTIONS);
        else
            return WordsFragment.newInstance(WordsFragment.OPPOSITE);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == 0)
            return mContext.getString(R.string.category_all_words);
        else if (position == 1)
            return mContext.getString(R.string.category_nouns);
        else if (position == 2)
            return mContext.getString(R.string.category_verbs);
         else if (position == 3)
            return mContext.getString(R.string.category_numbers);
         else if (position == 4)
             return mContext.getString(R.string.category_colors);
         else if (position == 5)
             return mContext.getString(R.string.category_questions);
         else
             return mContext.getString(R.string.category_opposite);

    }
}

