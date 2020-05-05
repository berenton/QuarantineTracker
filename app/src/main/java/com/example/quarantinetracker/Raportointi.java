package com.example.quarantinetracker;

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

    private Button[] selectionButtons = new Button[8];
    private Button titleButton;
    private Button placeButton;
    private Button peopleButton;
    private Button assessmentButton;
    private Button miscButton;

    private char state;

    private String assessment;
    private String person;
    private String people[];
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
                addData(v);
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
        yearSelection = root.findViewById(R.id.year);
        yearSelection.setText(currentDate.get(Calendar.YEAR)+"");

        selectionButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[1].getText());
            }
        });
        selectionButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[2].getText());
            }
        });
        selectionButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[3].getText());
            }
        });
        selectionButtons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[4].getText());
            }
        });
        selectionButtons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[5].getText());
            }
        });
        selectionButtons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[6].getText());
            }
        });
        selectionButtons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionButtonPressed(selectionButtons[7].getText());
            }
        });
        return root;
    }

    /**
     * Inserts the data from the report into SQL.
     * @param v View
     */
    public void addData(View v){
        int month = Integer.parseInt(monthSelection.getText().toString());
        int day = Integer.parseInt(daySelection.getText().toString());
        int hour = Integer.parseInt(hourSelection.getText().toString());
        int minute = Integer.parseInt(minuteSelection.getText().toString());
        int year = Integer.parseInt(yearSelection.getText().toString());
        myDb.insertReport(year, month, day, hour, minute,title,location,assessment,person,misc);
        Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
        state = 's';
    }

    /**
     * Called when the title-button is pressed. Makes title selection active and provides suggestions from user's previous titles.
     */
    public void titleButtonPressed(){
        /**
         * moi
         */
        state = 't';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Title Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getTitleData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the location-button is pressed. Makes location selection active and provides suggestions from user's previous locations.
     */
    public void placeButtonPressed(){
        state = 'l';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Location Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getLocationData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the assessment-button is pressed. Makes assessment selection active.
     */
    public void assessmentButtonPressed(){
        state = 'a';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Assessment Selected", Toast.LENGTH_LONG).show();
    }

    /**
     * Called when the people-button is pressed. Makes people selection active and provides suggestions from user's previous people choices.
     */
    public void peopleButtonPressed(){
        state = 'p';
        clearSelectionButtons();
        Toast.makeText(getContext(), "People Selected", Toast.LENGTH_LONG).show();
        Cursor res = myDb.getPeopleData();
        updateSelectionButtons(res);
    }

    /**
     * Called when the misc-button is pressed. Makes misc selection active and provides suggestions from user's previous misc sections.
     */
    public void miscButtonPressed(){
        state = 'm';
        clearSelectionButtons();
        Toast.makeText(getContext(), "Misc Selected", Toast.LENGTH_LONG).show();
    }

    /**
     * Updates the suggestions according to the cursor it is passed as a parameter.
     * @param resource from the SQLite database. Parses into a list of strings.
     * @return boolean on whether or not all saved suggestions fit on the screen.
     */
    public boolean updateSelectionButtons(Cursor resource){
        String[] stringList = new String[21474];
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

    /**
     * Clears the suggestions from selection buttons.
     */
    public void clearSelectionButtons(){
        for(int i = 1; i<8; i++){
            selectionButtons[i].setText("");
        }
    }


    /**
     * Is called when a selecion button is pressed. Passes the content of the button to the active part of the report.
     * @param content of the pressed selection button.
     */
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

    /**
     * Is called then the "Add option" button is pressed. Passes the contents of the editable text field to the active part of the report and adds them to an appropriate database.
     * @param v View
     */
    public void addOptionButtonPressed(View v){
        switch (state) {
            case 't':
                title = Objects.requireNonNull(newOption.getText()).toString();
                titleButton.setText(title);
                myDb.insertTitle(title);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'l':
                location = Objects.requireNonNull(newOption.getText()).toString();
                placeButton.setText(location);
                myDb.insertLocation(location);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'a':
                assessment = Objects.requireNonNull(newOption.getText()).toString();
                assessmentButton.setText(assessment);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'p':
                person = Objects.requireNonNull(newOption.getText()).toString();
                myDb.insertPerson(person);
                peopleButton.setText("Met with: \n" + person);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;
            case 'm':
                misc = Objects.requireNonNull(newOption.getText()).toString();
                miscButton.setText(misc);
                Toast.makeText(getContext(), "Selection saved", Toast.LENGTH_LONG).show();
                break;

        }
        newOption.setText("");
    }

}
