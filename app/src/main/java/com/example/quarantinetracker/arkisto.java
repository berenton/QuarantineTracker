package com.example.quarantinetracker;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quarantinetracker.ui.DatabaseHelper;


public class arkisto extends Fragment {

    TextView textField;
    DatabaseHelper myDb;
    Button refreshButton;

    public arkisto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myDb = new DatabaseHelper(getContext());
        final View root = inflater.inflate(R.layout.fragment_arkisto, container, false);
        textField = root.findViewById(R.id.textField);
        textField.setText(getReports());
        refreshButton = root.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReports();
            }
        });
        return root;
    }

    public String getReports(){
        Cursor res = myDb.getRaportData();
        if(res.getCount() == 0){
            return("No reports found");
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
        return(buffer.toString());
    }
}
