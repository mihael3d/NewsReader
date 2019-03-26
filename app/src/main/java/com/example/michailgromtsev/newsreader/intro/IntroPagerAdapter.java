package com.example.michailgromtsev.newsreader.intro;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance("paje # 1");
            case 1:
                return IntroFragment.newInstance("paje # 2");
            case 2:
                return  IntroFragment.newInstance("paje # 3");
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
