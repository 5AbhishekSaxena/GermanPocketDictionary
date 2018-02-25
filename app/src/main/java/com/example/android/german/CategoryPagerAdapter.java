package com.example.android.german;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Abhishek Saxena on 12/10/2017.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;


    public CategoryPagerAdapter(Context context, FragmentManager fm){
        super (fm);
        mContext = context;
    }

    // private String tabTitles[] = new String[] {"Numbers","Family", "Colors","Phrases"};
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new NounsFragment();
        else
            return new NumberFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == 0) {
            return mContext.getString(R.string.category_nouns);
        } else
            return mContext.getString(R.string.category_numbers);

    }
}

