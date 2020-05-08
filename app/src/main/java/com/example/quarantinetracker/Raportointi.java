package com.example.quarantinetracker;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quarantinetracker.ui.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;


/**
 * Class that runs the report input view.
 * @author Berenton
 * @version 20200505
 */
public class Raportointi extends Fragment {

    private DatabaseHelper myDb;

    private
    Button[] selectionButtons = new Button[7];
    private Button titleButton;
    private Button placeButton;
    private Button peopleButton;
    private Button assessmentButton;
    private Button miscButton;

    private char state;

    private String assessment;
    private String person;
    private String location;
    private String title;
    private String misc;

    private TextInputEditText newOption;
    private EditText monthSelection;
    private EditText daySelection;
    private EditText hourSelection;
    private EditText minuteSelection;
    private EditText yearSelection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Calendar currentDate = Calendar.getInstance();

        myDb = new DatabaseHelper(getContext());
        final View root = inflater.inflate(R.layout.raportointi, container, false);

        Button buttonAdd = root.findViewById(R.id.buttonAddData);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
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
        selectionButtons[0] = root.findViewById(R.id.selection1);
        selectionButtons[1] = root.findViewById(R.id.selection2);
        selectionButtons[2] = root.findViewById(R.id.selection3);
        selectionButtons[3] = root.findViewById(R.id.selection4);
        selectionButtons[4] = root.findViewById(R.id.selection5);
        selectionButtons[5] = root.findViewById(R.id.selection6);
        selectionButtons[6] = root.findViewById(R.id.selection7);

        Button addOptionButton = root.findViewById(R.id.addOption);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOptionButtonPressed();
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
        yearSelection = root.findViewById(R.id.year);
        yearSelection.setText(currentDate.get(Calendar.YEAR)+"");


        for (Button button : selectionButtons) {
            final Button selectionButton = button;
            selectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectionButtonPressed(selectionButton.getText());
                }
            });
        }
        return root;
    }

    /**
     * Inserts the data from the report into SQL. Validates the user input date.
     */
    private void addData(){
        monthSelection.setTextColor(Color.BLACK);
        daySelection.setTextColor(Color.BLACK);
        yearSelection.setTextColor(Color.BLACK);
        hourSelection.setTextColor(Color.BLACK);
        minuteSelection.setTextColor(Color.BLACK);
        int month = Integer.parseInt(monthSelection.getText().toString());
        int day = Integer.parseInt(daySelection.getText().toString());
        int hour = Integer.parseInt(hourSelection.getText().toString());
        int minute = Integer.parseInt(minuteSelection.getText().toString());
        int year = Integer.parseInt(yearSelection.getText().toString());
        if(month > 0 && month < 13 && day > 0 && day < 32 && year > 1970 && year < 3000 && hour >= 0 && hour < 24 && minute >= 0 && minute < 60){
            if(myDb.insertReport(year, month, day, hour, minute, title, location, assessment, person, misc)) {
                Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Data Insertion Failed", Toast.LENGTH_LONG).show();
            }
            state = 's';
        } else {
            Toast.makeText(getContext(), "Invalid Date", Toast.LENGTH_LONG).show();
            if (month < 1 || month > 12){
                monthSelection.setTextColor(Color.RED);
            }
            if (day < 1 || day > 31){
                daySelection.setTextColor(Color.RED);
            }
            if (year < 1971 || year > 2999){
                yearSelection.setTextColor(Color.RED);
            }
            if (hour < 0 || hour >= 24){
                hourSelection.setTextColor(Color.RED);
            }
            if (minute < 0 || minute >= 60){
                minuteSelection.setTextColor(Color.RED);
            }
        }
    }

    /**
     * Called when the title-button is pressed. Makes title selection active and provides suggestions from user's previous titles.
     */
    private void titleButtonPressed(){
        state = 't';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Title Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getTitleData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the location-button is pressed. Makes location selection active and provides suggestions from user's previous locations.
     */
    private void placeButtonPressed(){
        state = 'l';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Location Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getLocationData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the assessment-button is pressed. Makes assessment selection active.
     */
    private void assessmentButtonPressed(){
        state = 'a';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Assessment Selected", Toast.LENGTH_LONG).show();
    }

    /**
     * Called when the people-button is pressed. Makes people selection active and provides suggestions from user's previous people choices.
     */
    private void peopleButtonPressed(){
        state = 'p';
        clearSelectionButtons();
        Toast.makeText(getContext(), "People Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getPeopleData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the misc-button is pressed. Makes misc selection active and provides suggestions from user's previous misc sections.
     */
    private void miscButtonPressed(){
        state = 'm';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Misc Selected", Toast.LENGTH_LONG).show();
    }

    /**
     * Updates the suggestions according to the cursor it is passed as a parameter.
     * Checks that the string from the Database actually contains something.
     * @param resource from the SQLite database. Parses into a list of strings.
     */
    private void updateSelectionButtons(Cursor resource){
        String[] stringList = new String[21474];
        for (int i = 0, j = 0; resource.moveToNext(); i++, j++) {
            stringList[j] = resource.getString(1);
            if (i < 7) {
                if (stringList[j] != null && stringList[j].length() != 0) {
                    selectionButtons[(i)].setText(stringList[j]);
                    updateXButton(selectionButtons[i],stringList[j], 80);
                } else {
                    i--;
                }
            }else {
                break;
            }
        }
    }

    /**
     * Clears the suggestions from selection buttons.
     */
    private void clearSelectionButtons(){
        for(int i = 0; i<7; i++){
            selectionButtons[i].setText("");
        }
    }

    /**
     * Is called when a selecion button is pressed. Passes the content of the button to the active part of the report.
     * @param content of the pressed selection button.
     */
    private void selectionButtonPressed(CharSequence content){
        switch (state){
            case 't':
                Toast.makeText(getContext(), "Title Inserted", Toast.LENGTH_LONG).show();
                title = content.toString();
                updateXButton(titleButton, title,15);
                break;
            case 'l':
                Toast.makeText(getContext(), "Place Inserted", Toast.LENGTH_LONG).show();
                location = content.toString();
                updateXButton(placeButton,location,15);
                break;
            case 'a':
                Toast.makeText(getContext(), "Assessment Inserted", Toast.LENGTH_LONG).show();
                assessment = content.toString();
                updateXButton(assessmentButton,assessment,15);
                break;
            case 'p':
                Toast.makeText(getContext(), "People Inserted", Toast.LENGTH_LONG).show();
                person = content.toString();
                updateXButton(peopleButton,person,15);
                break;
            case 'm':
                Toast.makeText(getContext(), "Misc Inserted", Toast.LENGTH_LONG).show();
                misc = content.toString();
                updateXButton(miscButton,misc,15);
                break;
        }
    }

    /**
     * Is called then the "Add option" button is pressed. Passes the contents of the editable text field to the active part of the report and adds them to an appropriate database.
     */
    private void addOptionButtonPressed(){
        switch (state) {
            case 't':
                title = Objects.requireNonNull(newOption.getText()).toString();
                if(myDb.insertTitle(title)){
                    updateXButton(titleButton,title, 15);
                    Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Data Insertion Failed", Toast.LENGTH_LONG).show();
                }
                break;
            case 'l':
                location = Objects.requireNonNull(newOption.getText()).toString();
                if(myDb.insertLocation(location)){
                    updateXButton(placeButton,location, 15);
                    Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Data Insertion Failed", Toast.LENGTH_LONG).show();
                }
                break;
            case 'a':
                assessment = Objects.requireNonNull(newOption.getText()).toString();
                updateXButton(assessmentButton,assessment,15);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'p':
                person = Objects.requireNonNull(newOption.getText()).toString();
                if(myDb.insertPerson(person)) {
                    updateXButton(peopleButton,person,15);
                    Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Data Insertion Failed", Toast.LENGTH_LONG).show();
                }
                break;
            case 'm':
                misc = Objects.requireNonNull(newOption.getText()).toString();
                updateXButton(miscButton,misc,15);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;

        }
        newOption.setText("");
    }

    /**
     * Checks the length of the given string and cuts it down if necessary to fit the button.
     * Inserts the string to the button.
     * @param destination Button in which you want to insert the string
     * @param content String you want to insert to the button
     */
    private void updateXButton(Button destination, String content, int maxLength){
        if (content.length() > maxLength){
            content = content.substring(0,maxLength)+"...";
        }
        destination.setText(content);
    }
}
