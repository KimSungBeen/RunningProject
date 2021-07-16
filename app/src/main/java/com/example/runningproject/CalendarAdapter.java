package com.example.runningproject;

import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningproject.databinding.ItemCalendarListBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder> {
    private static final String TAG = CalendarAdapter.class.getSimpleName();
    ArrayList<Integer> dataList;

    interface ItemClick {
        void onClick(View view, int position);
    }

    ItemClick itemClick = null;

    GlabsCalendar glabsCalendar;

    LinearLayout calendarLayout;
    Date date;

    public CalendarAdapter(LinearLayout calendarLayout, Date date) {
        this.calendarLayout = calendarLayout;
        this.date = date;
        glabsCalendar = new GlabsCalendar(date);
        glabsCalendar.initBaseCalendar();
        dataList = glabsCalendar.dateList;
    }

    @NonNull
    @Override
    public CalendarItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_list, parent, false);
        ItemCalendarListBinding binding = ItemCalendarListBinding.bind(view);

        return new CalendarItemHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CalendarItemHolder holder, int position) {
        holder.bind(glabsCalendar, date, dataList, dataList.get(position), position);

        if (itemClick != null) {

            //클릭리스너 셋팅
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CalendarItemHolder extends RecyclerView.ViewHolder {
        private final ItemCalendarListBinding binding;

        public CalendarItemHolder(ItemCalendarListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        void bind(GlabsCalendar glabsCalendar, Date date, ArrayList<Integer> dataList, int data, int position) {
            int firstDateIndex = glabsCalendar.prevTail;
            int lastDateIndex = dataList.size() - glabsCalendar.nextHead - 1;

            // 날짜 표시
            binding.itemCalendarDateText.setText(String.valueOf(data));
            Log.d(TAG, "bind: " + data
                    + " position: " + position);

            // 오늘 날짜 처리
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.KOREA);
            String dateString = dateFormat.format(date);
            int dateInt = Integer.parseInt(dateString);

            Calendar cal = Calendar.getInstance();
            Date currentTime = new Date();
            currentTime.setTime(cal.getTimeInMillis());
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);

            String monthString = monthFormat.format(date);
            String currentMonthString = monthFormat.format(currentTime);

            int monthInt = Integer.parseInt(monthString);
            int currentMonthInt = Integer.parseInt(currentMonthString);

            Log.d(TAG, "bind: " + "monthInt: " + monthInt + " currentMonthInt: " + currentMonthInt);

            if (dataList.get(position) == dateInt && monthInt == currentMonthInt) {
                binding.itemCalendarDateText.setTypeface(binding.itemCalendarDateText.getTypeface(), Typeface.BOLD);
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                binding.itemCalendarDateText.setTextAppearance(R.style.LightColorTextViewStyle);
                binding.itemCalendarDateText.setBackground(null);
            }

            if ((lastDateIndex <= 34) && (position > 34)) {
                //마지막 ROW
                binding.itemCalendarDateText.setText("");
            }

        }
    }
}
