package com.example.quarantinetracker;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quarantinetracker.ui.DatabaseHelper;


/**
 * Class that runs calendar view and data for a certain date.
 * @author Mika Åberg
 * @version 5/2019
 */
public class calendar extends Fragment {

    CalendarView calendarView;
    TextView text;
    DatabaseHelper myDb;

    private String date;

    int month_;
    int year_;
    int day_;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getContext());
        final View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        /**
         * Shows the selected date.
         * Month starts from 0. Add +1 to get current month.
         */
        calendarView = root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                month_ = month;
                year_ = year;
                day_ = dayOfMonth;
                text.setText(date);
            }
        });

        /**
         * Called when current date button is pressed. Calls the showData() method.
         * @param v
         */
        text = root.findViewById(R.id.buttonData);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(v);
            }
        });
        return root;
    }

    /**
     * shows data from report
     * add a current date
     * @param v View
     * @return no data add from user
     */
    public void showData(View v){
        Cursor res = myDb.getReportData((month_+1),day_);
        if(res.getCount() == 0){
            showMessage("Error", "No data found.");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Id : "+res.getInt(0)+ "\n");
            buffer.append("Year : "+res.getInt(1)+ "\n");
            buffer.append("Month : "+res.getInt(2)+ "\n");
            buffer.append("Day : "+res.getInt(3)+ "\n");
            buffer.append("Hour : "+res.getInt(4)+ "\n");
            buffer.append("Minute : "+res.getInt(5)+ "\n");
            buffer.append("Title : "+res.getString(6)+ "\n");
            buffer.append("Location : "+res.getString(7)+ "\n");
            buffer.append("Assessment : "+res.getString(8)+ "\n");
            buffer.append("People : "+res.getString(9)+ "\n");
            buffer.append("Misc : "+res.getString(10)+ "\n\n");
        }
        showMessage("Data: ", buffer.toString());
    }

    /**
     * shows the pop-up dialog message
     * @param title
     * @param message
     */
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}



