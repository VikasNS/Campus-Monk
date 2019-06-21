package com.campusmonk.vikas.msrit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import notes.Notes;

public class Main_pager_adaptor extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public Main_pager_adaptor(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new Search();
        }
        if (position == 1) {
            return new Notes();
        }
        return new Sis();
    }

    public int getCount() {
        return NUM_ITEMS;
    }
}
