package com.example.quarantinetracker;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quarantinetracker.ui.DatabaseHelper;

/**
 * Class that runs the archive view.
 * @author Berenton
 * @version 20200505
 */
public class arkisto extends Fragment {

    private DatabaseHelper myDb;

    public arkisto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myDb = new DatabaseHelper(getContext());
        final View root = inflater.inflate(R.layout.fragment_arkisto, container, false);
        TextView textField = root.findViewById(R.id.textField);
        textField.setText(getReports());
        Button refreshButton = root.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReports();
            }
        });
        return root;
    }

    /**
     * Method that gets all reports from the main table of the database.
     * @return String containing the reports.
     */
    private String getReports(){
        Cursor res = myDb.getReportData();
        if(res.getCount() == 0){
            return("No reports found");
        }
        StringBuilder buffer = new StringBuilder();
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
        return(buffer.toString());
    }
}
