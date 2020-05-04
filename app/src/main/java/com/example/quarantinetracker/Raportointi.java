package com.example.quarantinetracker;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.quarantinetracker.ui.DatabaseHelper;
import com.example.quarantinetracker.ui.slideshow.SlideshowViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class Raportointi extends Fragment {

    DatabaseHelper myDb;

    Button selectionButtons[] = new Button[8];
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
    EditText monthSelection;
    EditText daySelection;
    EditText hourSelection;
    EditText minuteSelection;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Calendar currentDate = Calendar.getInstance();

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
        selectionButtons[1] = root.findViewById(R.id.selection1);
        selectionButtons[2] = root.findViewById(R.id.selection2);
        selectionButtons[3] = root.findViewById(R.id.selection3);
        selectionButtons[4] = root.findViewById(R.id.selection4);
        selectionButtons[5] = root.findViewById(R.id.selection5);
        selectionButtons[6] = root.findViewById(R.id.selection6);
        selectionButtons[7] = root.findViewById(R.id.selection7);

        Button addOptionButton = root.findViewById(R.id.addOption);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOptionButtonPressed(v);
            }
        });
        newOption = root.findViewById(R.id.newOption);

        monthSelection = root.findViewById(R.id.month);
        monthSelection.setText((currentDate.get(Calendar.MONTH)+1)+"");
        daySelection = root.findViewById(R.id.day);
        daySelection.setText(currentDate.get(Calendar.DAY_OF_MONTH)+"");
        hourSelection = root.findViewById(R.id.hour);
        hourSelection.setText(currentDate.get(Calendar.HOUR_OF_DAY)+"");
        minuteSelection = root.findViewById(R.id.minute);
        minuteSelection.setText(currentDate.get(Calendar.MINUTE)+"");

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
        month = Integer.parseInt(monthSelection.getText().toString());
        day = Integer.parseInt(daySelection.getText().toString());
        hour = Integer.parseInt(hourSelection.getText().toString());
        minute = Integer.parseInt(minuteSelection.getText().toString());
        myDb.insertReport(2020,month,day,hour,minute,title,location,assessment,person,misc);
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
        clearSelectionButtons();
        Toast.makeText(getContext(), "Title Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getTitleData();
        updateSelectionButtons(res);
    }
    public void placeButtonPressed(){
        state = 'l';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Location Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getLocationData();
        updateSelectionButtons(res);
    }
    public void assessmentButtonPressed(){
        state = 'a';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Assessment Selected", Toast.LENGTH_LONG).show();
    }
    public void peopleButtonPressed(){
        state = 'p';
        clearSelectionButtons();
        Toast.makeText(getContext(), "People Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getPeopleData();
        updateSelectionButtons(res);
    }
    public void miscButtonPressed(){
        state = 'm';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Misc Selected", Toast.LENGTH_LONG).show();
    }

    public boolean updateSelectionButtons(Cursor resource){
        String[] stringList = new String[7];
        for (int i = 0; resource.moveToNext(); i++){
            stringList[i] = resource.getString(1);
            if(i < 7){
                selectionButtons[(i+1)].setText(stringList[i]);
            }else {
                return false;
            }
        }
        return true;
    }

    public void clearSelectionButtons(){
        for(int i = 1; i<8; i++){
            selectionButtons[i].setText("");
        }
    }


    public void selectionButtonPressed(CharSequence content){
        switch (state){
            case 't':
                Toast.makeText(getContext(), "Title Inserted", Toast.LENGTH_LONG).show();
                titleButton.setText(content);
                title = content.toString();
                break;
            case 'l':
                Toast.makeText(getContext(), "Place Inserted", Toast.LENGTH_LONG).show();
                placeButton.setText(content);
                location = content.toString();
                break;
            case 'a':
                Toast.makeText(getContext(), "Assessment Inserted", Toast.LENGTH_LONG).show();
                assessment = content.toString();
                break;
            case 'p':
                Toast.makeText(getContext(), "People Inserted", Toast.LENGTH_LONG).show();
                peopleButton.setText(content);
                person = content.toString();
                break;
            case 'm':
                Toast.makeText(getContext(), "Misc Inserted", Toast.LENGTH_LONG).show();
                miscButton.setText(content);
                misc = content.toString();
                break;
        }
    }

    public void addOptionButtonPressed(View v){
        switch (state) {
            case 't':
                title = newOption.getText().toString();
                titleButton.setText(title);
                myDb.insertTitle(title);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'l':
                location = newOption.getText().toString();
                placeButton.setText(location);
                myDb.insertLocation(location);
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

}
