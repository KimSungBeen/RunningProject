package com.example.runningproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GlabsCalendar {
    static final int DAYS_OF_WEEK = 7;
    static final int LOW_OF_CALENDAR = 6;

    final Calendar calendar = Calendar.getInstance();

    int prevTail = 0;
    int nextHead = 0;
    int currentMaxDate = 0;

    ArrayList<Integer> dateList = new ArrayList<>();

    public GlabsCalendar(Date date) {
        calendar.setTime(date);
    }

    public void initBaseCalendar() {
        makeMonthDate();
    }

    private void makeMonthDate() {
        dateList.clear();

        calendar.set(Calendar.DATE, 1);

        currentMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        prevTail = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        makePrevTail((Calendar) calendar.clone());
        makeCurrentMonth(calendar);

        nextHead = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevTail + currentMaxDate);
        makeNextHead();
    }

    private void makePrevTail(Calendar calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int maxOffsetDate = maxDate - prevTail;

        for (int i = 1; i <= prevTail; i++) {
            dateList.add(++maxOffsetDate);
        }
    }

    private void makeCurrentMonth(Calendar calendar) {
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DATE); i++) {
            dateList.add(i);
        }
    }

    private void makeNextHead() {
        int date = 1;

        for (int i = 1; i <= nextHead; i++) {
            dateList.add(date++);
        }
    }
}
