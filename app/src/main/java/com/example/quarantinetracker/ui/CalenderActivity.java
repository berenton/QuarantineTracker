package com.example.quarantinetracker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.quarantinetracker.MainActivity;
import com.example.quarantinetracker.R;

import static com.example.quarantinetracker.R.layout.calendar;


    /*    CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year +"/" + month + "/" + dayOfMonth;
                Intent intent = new Intent(CalenderActivity.this, MainActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}

     */
