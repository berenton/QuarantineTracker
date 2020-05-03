package com.example.quarantinetracker;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.quarantinetracker.ui.DatabaseHelper;
import com.example.quarantinetracker.ui.slideshow.SlideshowViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class Raportointi extends Fragment {

    DatabaseHelper myDb;

    Button[] selectionButtons;
    Button titleButton;
    Button placeButton;
    Button peopleButton;
    Button assessmentButton;
    Button miscButton;

    char state;

    int year;
    int month;
    int day;
    int hour;
    int minute;
    String assessment;
    String person;
    String people[];
    String location;
    String title;
    String misc;

    TextInputEditText newOption;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myDb = new DatabaseHelper(getContext());
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.raportointi, container, false);

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

        titleButton = root.findViewById(R.id.Title);
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleButtonPressed();
            }
        });
        placeButton = root.findViewById(R.id.Place);
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeButtonPressed();
            }
        });
        assessmentButton = root.findViewById(R.id.Assessment);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessmentButtonPressed();
            }
        });
        peopleButton = root.findViewById(R.id.People);
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleButtonPressed();
            }
        });
        miscButton = root.findViewById(R.id.Misc);
        miscButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miscButtonPressed();
            }
        });

        Button addOptionButton = root.findViewById(R.id.addOption);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOptionButtonPressed(v);
            }
        });
        newOption = root.findViewById(R.id.newOption);

        final Button selection1 = root.findViewById(R.id.selection1);
        selection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection1.getText());
            }
        });
        final Button selection2 = root.findViewById(R.id.selection2);
        selection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection2.getText());
            }
        });
        final Button selection3 = root.findViewById(R.id.selection3);
        selection3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection3.getText());
            }
        });
        final Button selection4 = root.findViewById(R.id.selection4);
        selection4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection4.getText());
            }
        });
        final Button selection5 = root.findViewById(R.id.selection5);
        selection5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection5.getText());
            }
        });
        final Button selection6 = root.findViewById(R.id.selection6);
        selection6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection6.getText());
            }
        });
        final Button selection7 = root.findViewById(R.id.selection7);
        selection7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selection7.getText());
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
        myDb.insertReport(2020,4,29,10,55,title,location,assessment,person,misc);
        Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
        state = 's';
    }
    public void showData(View v){
        Cursor res = myDb.getRaportData();
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

    public void getPeople(View v){
        Cursor res = myDb.getPeopleData();
        String people[] = new String[0];
        for(int i = 0; res.moveToNext(); i++){
            people[i] = res.getString(1);
        }

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void titleButtonPressed(){
        state = 't';
        Toast.makeText(getContext(), "Title Selected", Toast.LENGTH_LONG).show();
    }
    public void placeButtonPressed(){
        state = 'l';
        Toast.makeText(getContext(), "Location Selected", Toast.LENGTH_LONG).show();
    }
    public void assessmentButtonPressed(){
        state = 'a';
        Toast.makeText(getContext(), "Assessment Selected", Toast.LENGTH_LONG).show();
    }
    public void peopleButtonPressed(){
        state = 'p';
    }
    public void miscButtonPressed(){
        state = 'm';
    }

    public void selectionButtonPressed(CharSequence content){
        switch (state){
            case 't':
                Toast.makeText(getContext(), "Title Inserted", Toast.LENGTH_LONG).show();
                break;
            case 'l':
                Toast.makeText(getContext(), "Place Inserted", Toast.LENGTH_LONG).show();
                break;
            case 'a':
                Toast.makeText(getContext(), "Assessment Inserted", Toast.LENGTH_LONG).show();
                break;
            case 'p':
                Toast.makeText(getContext(), "People Inserted", Toast.LENGTH_LONG).show();
                break;
            case 'm':
                Toast.makeText(getContext(), "Misc Inserted", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void addOptionButtonPressed(View v){
        switch (state) {
            case 't':
                title = newOption.getText().toString();
                titleButton.setText(title);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'l':
                location = newOption.getText().toString();
                placeButton.setText(location);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'a':
                assessment = newOption.getText().toString();
                assessmentButton.setText(assessment);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'p':
                person = newOption.getText().toString();
                myDb.insertPerson(person);
                peopleButton.setText("Met with: \n" + person);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'm':
                misc = newOption.getText().toString();
                miscButton.setText(misc);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;

        }
        newOption.setText("");
    }


    public void chooseContent(View v){


    }
}
