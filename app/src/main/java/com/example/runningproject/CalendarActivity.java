package com.example.runningproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.runningproject.databinding.ActivityCalendarBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        binding.setLifecycleOwner(this);


        FirstFragmentStateAdapter firstFragmentStateAdapter = new FirstFragmentStateAdapter(this);
        binding.vpCalendar.setAdapter(firstFragmentStateAdapter);
        binding.vpCalendar.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.vpCalendar.setCurrentItem(firstFragmentStateAdapter.firstFragmentPosition, false);
    }

}