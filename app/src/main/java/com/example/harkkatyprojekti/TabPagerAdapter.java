package com.example.harkkatyprojekti;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabPagerAdapter extends FragmentStateAdapter {
    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentPerustiedot();
            case 1:
                return new FragmentVertailu();
            case 2:
                return new FragmentVisa();
            default:
                return new FragmentPerustiedot();


        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
