package io.edgeperformance.edge.Models;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import io.edgeperformance.edge.HomeFragments.FragmentEveningRoutine;
import io.edgeperformance.edge.HomeFragments.FragmentMorningRoutine;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment[] fragment = {null};

        if (position == 0) {
            fragment[0] = new FragmentMorningRoutine();
        } else if (position == 1) {
            fragment[0] = new FragmentEveningRoutine();
        }
        return fragment[0];
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Morning Routine";
        }  else if (position == 1) {
            return "Evening Routine";
        }
        return null;
    }

}
