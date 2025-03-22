package com.viettel.mocha.rmlivestream.player.info_live;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.viettel.mocha.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

public class RMLivestreamDetailPagerAdapter extends FragmentStatePagerAdapter {
    static final String TAG = RMLivestreamDetailPagerAdapter.class.getSimpleName();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public RMLivestreamDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return mFragmentList.get(position);
        } catch (Exception e) {
            Log.e(TAG, e);
        }
        return null;
    }

    @Nullable
    @androidx.annotation.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFragment(int index) {
        if (mFragmentList.size() <= index) return;
        mFragmentList.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
