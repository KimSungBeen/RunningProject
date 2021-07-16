package com.example.runningproject;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.runningproject.databinding.FragmentCalendarBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private static final String TAG = CalendarFragment.class.getSimpleName();
    private FragmentCalendarBinding binding;
    private Context mContext;
    int pageIndex = 0;
    CalendarFragment instance = null;
    Date currentDate;

    CalendarAdapter calendarAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CalendarActivity) {
            mContext = context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        initView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        //포맷 적용
        SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.calendar_year_month_format), Locale.KOREA);
        String dateTime = dateFormat.format(currentDate.getTime());
        ((CalendarActivity) requireActivity()).binding.calendarYearMonthText.setText(dateTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    private void initView() {
        pageIndex -= (Integer.MAX_VALUE / 2);
        Log.d(TAG, "Calender Index: " + pageIndex);

        //날짜 적용
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, pageIndex);
        currentDate = cal.getTime();
        Log.d(TAG, "currentDate: " + currentDate);

        binding.rvCalendarView.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        calendarAdapter = new CalendarAdapter(binding.calendarLayout, currentDate);
        binding.rvCalendarView.setAdapter(calendarAdapter);

        //날짜 클릭 리스너
        calendarAdapter.itemClick = new CalendarAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick: " + position);
            }
        };


        TopSheetBehavior topSheetBehavior = TopSheetBehavior.from(binding.calendarLayout);
        topSheetBehavior.setTopSheetCallback(new TopSheetBehavior.TopSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d(TAG, "onStateChanged: " + newState);

//                if (newState == TopSheetBehavior.STATE_COLLAPSED) {
//                    binding.rvCalendarView.setVisibility(View.INVISIBLE);
//                } else if (newState == TopSheetBehavior.STATE_DRAGGING) {
//                    binding.rvCalendarView.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide: " + slideOffset);

                if (slideOffset == 0) {
                    binding.rvCalendarView.setVisibility(View.INVISIBLE);
                } else {
                    binding.rvCalendarView.setVisibility(View.VISIBLE);
                }

                binding.rvCalendarView.setAlpha(slideOffset);
            }
        });

    }
}
