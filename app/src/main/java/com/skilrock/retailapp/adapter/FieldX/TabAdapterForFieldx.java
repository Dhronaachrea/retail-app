package com.skilrock.retailapp.adapter.FieldX;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skilrock.retailapp.ui.fragments.fieldx.Delivery;
import com.skilrock.retailapp.ui.fragments.fieldx.PickupFragment;
import com.skilrock.retailapp.ui.fragments.fieldx.AllTaskFragment;

public class TabAdapterForFieldx extends FragmentStatePagerAdapter {
    private String mFragmentTitleList[];

    public TabAdapterForFieldx(FragmentManager fm, int numberOfTabs, String mFragmentTitleList[]) {
        super(fm);
        this.mFragmentTitleList = mFragmentTitleList;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AllTaskFragment();
            case 1:
                return new Delivery();
            case 2:
                return new PickupFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList[position].toUpperCase();
    }
}
