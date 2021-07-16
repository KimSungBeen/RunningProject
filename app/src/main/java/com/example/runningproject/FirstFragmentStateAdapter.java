package com.example.runningproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FirstFragmentStateAdapter extends FragmentStateAdapter {
    private final int pageCount = Integer.MAX_VALUE;
    final int firstFragmentPosition = pageCount / 2;

    public FirstFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        final CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.pageIndex = position;

        return calendarFragment;
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }
}
