package com.chunxinweiyu.newlipreader.UI.leng.CommunityAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chunxinweiyu.newlipreader.UI.leng.CommunityPages.FollowFragment;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityPages.LatestFragment;
import com.chunxinweiyu.newlipreader.UI.leng.CommunityPages.RecommendFragment;

public class MyTabPageAdapter extends FragmentPagerAdapter {
    private final String[] mTitlees;

    public MyTabPageAdapter(FragmentManager fm, String[] titlees) {
        super(fm);
        this.mTitlees = titlees;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RecommendFragment();
        } else if (position == 1) {
            return new LatestFragment();
        }
        return new FollowFragment();
    }

    @Override
    public int getCount() {
        return mTitlees.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlees[position];
    }
}

