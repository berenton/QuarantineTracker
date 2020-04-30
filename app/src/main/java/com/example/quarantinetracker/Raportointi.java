package com.example.quarantinetracker;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.quarantinetracker.R;
import com.example.quarantinetracker.ui.DatabaseHelper;
import com.example.quarantinetracker.ui.slideshow.SlideshowViewModel;

public class Raportointi extends Fragment {

    DatabaseHelper myDb;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myDb = new DatabaseHelper(getContext());
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.raportointi, container, false);

        Button buttonAdd = root.findViewById(R.id.buttonAddData);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(v);
            }
        });

        Button buttonShow = root.findViewById(R.id.buttonShowData);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(v);
            }
        });

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    public void addData(View v){
        myDb.insertData(2020,4,29,10,55,1,"Moi");
        Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
    }
    public void showData(View v){
        Cursor res = myDb.getAllData();
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
            buffer.append("Assessment : "+res.getInt(8)+ "\n");
            buffer.append("Misc : "+res.getString(0)+ "\n\n");
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
