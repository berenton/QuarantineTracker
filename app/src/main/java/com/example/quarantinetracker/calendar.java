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
import com.example.quarantinetracker.ui.slideshow.SlideshowViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link calendar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class calendar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CalendarView calendarView;
    TextView text;

    private String date;
    private SlideshowViewModel slideshowViewModel;
    DatabaseHelper myDb;
    int month_;
    int year_;
    int day_;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public calendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static calendar newInstance(String param1, String param2) {
        calendar fragment = new calendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getContext());
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year; // Tammikuu alkaa nollasta, joten pitää lisätä kuukauteen yksi
                month_ = month;
                year_ = year;
                day_ = dayOfMonth;
                text.setText(date);
            }
        });

        text = root.findViewById(R.id.buttonData);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(v);
            }
        });

        return root;
    }

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

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}



