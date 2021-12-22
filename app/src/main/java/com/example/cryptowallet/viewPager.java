package com.example.cryptowallet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPager extends FragmentPagerAdapter {

    // creation of constructor of viewPager class
    public viewPager(@NonNull FragmentManager fm, int behaviour) {
        super(fm, behaviour);
    }

    @NonNull

    @Override

    // create the getItem method of
    // FragmentPagerAdapter class
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}