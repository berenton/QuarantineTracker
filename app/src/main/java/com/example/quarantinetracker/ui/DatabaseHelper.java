package com.example.quarantinetracker.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Class that helps with the usage of the SQLite database.
 * @author Berenton
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Raport.db";
    private static final String MAIN_TABLE_NAME = "Raport_table";
    private static final String MAIN_COL_1 = "ID";
    private static final String MAIN_COL_2 = "Year";
    private static final String MAIN_COL_3 = "Month";
    private static final String MAIN_COL_4 = "Day";
    private static final String MAIN_COL_5 = "Hour";
    private static final String MAIN_COL_6 = "Minute";
    private static final String MAIN_COL_7 = "Activity_ID";
    private static final String MAIN_COL_8 = "Location_ID";
    private static final String MAIN_COL_9 = "Assessment";
    private static final String MAIN_COL_10 = "People";
    private static final String MAIN_COL_11 = "Misc";


    private static final String PEOPLE_TABLE_NAME = "People_table";
    private static final String PEOPLE_COL_1 = "ID";
    private static final String PEOPLE_COL_2 = "Name";

    private static final String TITLE_TABLE_NAME = "Title_table";
    private static final String TITLE_COL_1 = "ID";
    private static final String TITLE_COL_2 = "Title";
    private static final String TITLE_COL_3 = "Times";
    private static final String TITLE_COL_4 = "AssessmentSum";

    private static final String LOCATION_TABLE_NAME = "Location_table";
    private static final String LOCATION_COL_1 = "ID";
    private static final String LOCATION_COL_2 = "Location";
    private static final String LOCATION_COL_3 = "Times";
    private static final String LOCATION_COL_4 = "AssessmentSum";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MAIN_TABLE_NAME + " (" + MAIN_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MAIN_COL_2 + " INTEGER, "+ MAIN_COL_3 + " INTEGER,"+ MAIN_COL_4 +" INTEGER,"+ MAIN_COL_5 +" INTEGER,"+ MAIN_COL_6 +" INTEGER,"+ MAIN_COL_7 +" INTEGER,"+ MAIN_COL_8 +" INTEGER,"+ MAIN_COL_9 +" TEXT,"+ MAIN_COL_10 +" INTEGER,"+ MAIN_COL_11 +" TEXT)");
        db.execSQL("create table " + PEOPLE_TABLE_NAME + " (" + PEOPLE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +PEOPLE_COL_2 + " TEXT)");
        db.execSQL("create table " + TITLE_TABLE_NAME + " (" + TITLE_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_COL_2 + " TEXT, " + TITLE_COL_3 + " INTEGER," + TITLE_COL_4 + " INTEGER)");
        db.execSQL("create table " + LOCATION_TABLE_NAME + " (" + LOCATION_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_COL_2 + " TEXT, " + LOCATION_COL_3 + " INTEGER," + LOCATION_COL_4 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MAIN_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts the given parameters into the main SQL table as one row.
     * @param year Year of the report INT
     * @param month Month of the report INT
     * @param day Day of the report INT
     * @param hour Hour of the report INT
     * @param minute Minute of the report INT
     * @param activity Activity/Title field of the report String
     * @param location Location field of the report String
     * @param assessment Assessment field of the report String
     * @param people People field of the report String
     * @param misc Miscellaneous field of the report String
     * @return boolean on whether or not succeeded
     */
    public boolean insertReport(int year, int month, int day, int hour, int minute, String activity, String location, String assessment, String people, String misc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MAIN_COL_2, year);
        contentValues.put(MAIN_COL_3, month);
        contentValues.put(MAIN_COL_4, day);
        contentValues.put(MAIN_COL_5, hour);
        contentValues.put(MAIN_COL_6, minute);
        contentValues.put(MAIN_COL_7, activity);
        contentValues.put(MAIN_COL_8, location);
        contentValues.put(MAIN_COL_9, assessment);
        contentValues.put(MAIN_COL_10, people);
        contentValues.put(MAIN_COL_11, misc);
        long result = db.insert(MAIN_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Inserts the given name into the person table.
     * @param name Name to be inserted into person table.
     * @return boolean whether or not succeeded
     */
    public boolean insertPerson(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEOPLE_COL_2, name);
        long result = db.insert(PEOPLE_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Inserts the given activity into the activity table.
     * @param activity Activity to be inserted into activity table.
     * @return boolean whether or not succeeded
     */
    public boolean insertTitle(String activity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COL_2, activity);
        long result = db.insert(TITLE_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Inserts the given location into the location table.
     * @param location Location to be inserted into location table.
     * @return boolean whether or not succeeded
     */
    public boolean insertLocation(String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COL_2, location);
        long result = db.insert(LOCATION_TABLE_NAME, null, contentValues);
        return result != -1;
    }


    /**
     * Returns all reports from the main table sorted by the most recently added.
     * @return Cursor containing the data
     */
    public Cursor getReportData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+ MAIN_TABLE_NAME + " ORDER BY " + MAIN_COL_1 + " DESC",null);
    }

    /**
     * Returns reports with the given date from the main table sorted by the most recently added.
     * @param month Month of the searched date (1-12)
     * @param day Day of the searched date (1-31)
     * @return Cursor containing the data
     */
    public Cursor getReportData(int month, int day){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] arg = {month+"", day+""};
        return db.rawQuery("select * from "+ MAIN_TABLE_NAME + " WHERE "+MAIN_COL_3+ "=? AND "+MAIN_COL_4+"=? ORDER BY " + MAIN_COL_1 + " DESC", arg);
    }

    /**
     * Returns all location names from the location table sorted by the most recently added.
     * @return Cursor containing the data
     */
    public Cursor getLocationData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+ LOCATION_TABLE_NAME + " ORDER BY " + LOCATION_COL_1 + " DESC",null);
    }

    /**
     * Returns all activity names from the activity table sorted by the most recently added.
     * @return Cursor containing the data.
     */
    public Cursor getTitleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+ TITLE_TABLE_NAME + " ORDER BY " + TITLE_COL_1 + " DESC",null);
    }

    /**
     * Returns all names from the people table sorted by the most recently added.
     * @return Cursor containing the data
     */
    public Cursor getPeopleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+ PEOPLE_TABLE_NAME + " ORDER BY " + PEOPLE_COL_1 + " DESC",null);
    }

}
