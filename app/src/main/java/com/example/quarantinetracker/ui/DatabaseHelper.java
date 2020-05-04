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
    public static final String DATABASE_NAME = "Raport.db";
    public static final String MAIN_TABLE_NAME = "Raport_table";
    public static final String MAIN_COL_1 = "ID";
    public static final String MAIN_COL_2 = "Year";
    public static final String MAIN_COL_3 = "Month";
    public static final String MAIN_COL_4 = "Day";
    public static final String MAIN_COL_5 = "Hour";
    public static final String MAIN_COL_6 = "Minute";
    public static final String MAIN_COL_7 = "Activity_ID";
    public static final String MAIN_COL_8 = "Location_ID";
    public static final String MAIN_COL_9 = "Assessment";
    public static final String MAIN_COL_10 = "People";
    public static final String MAIN_COL_11 = "Misc";


    public static final String PEOPLE_TABLE_NAME = "People_table";
    public static final String PEOPLE_COL_1 = "ID";
    public static final String PEOPLE_COL_2 = "Name";

    public static final String TITLE_TABLE_NAME = "Title_table";
    public static final String TITLE_COL_1 = "ID";
    public static final String TITLE_COL_2 = "Title";
    public static final String TITLE_COL_3 = "Times";
    public static final String TITLE_COL_4 = "AssessmentSum";

    public static final String LOCATION_TABLE_NAME = "Location_table";
    public static final String LOCATION_COL_1 = "ID";
    public static final String LOCATION_COL_2 = "Location";
    public static final String LOCATION_COL_3 = "Times";
    public static final String LOCATION_COL_4 = "AssessmentSum";


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
     * @param year INT
     * @param month INT
     * @param day INT
     * @param hour INT
     * @param minute INT
     * @param activity String
     * @param location String
     * @param assessment String
     * @param people String
     * @param misc String
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
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    /**
     * Inserts the given name into the person table.
     * @param name
     * @return boolean whether or not succeeded
     */
    public boolean insertPerson(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEOPLE_COL_2, name);
        long result = db.insert(PEOPLE_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    /**
     * Inserts the given activity into the activity table.
     * @param activity
     * @return boolean whether or not succeeded
     */
    public boolean insertTitle(String activity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COL_2, activity);
        long result = db.insert(TITLE_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    /**
     * Inserts the given location into the location table.
     * @param location
     * @return boolean whether or not succeeded
     */
    public boolean insertLocation(String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COL_2, location);
        long result = db.insert(LOCATION_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }


    /**
     * Returns all reports from the main table.
     * @return Cursor containing the data
     */
    public Cursor getReportData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ MAIN_TABLE_NAME,null);
        return res;
    }

    /**
     * Returns reports with the given date from the main table.
     * @param month 1-12
     * @param day
     * @return Cursor containing the data
     */
    public Cursor getReportData(int month, int day){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] arg = {month+"", day+""};
        Cursor res = db.rawQuery("select * from "+ MAIN_TABLE_NAME + " WHERE "+MAIN_COL_3+ "=? AND "+MAIN_COL_4+"=?;", arg);
        return res;
    }

    /**
     * Returns all location names from the location table.
     * @return Cursor containing the data
     */
    public Cursor getLocationData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ LOCATION_TABLE_NAME,null);
        return res;
    }

    /**
     * Returns all activity names from the activity table.
     * @return Cursor containing the data.
     */
    public Cursor getTitleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TITLE_TABLE_NAME,null);
        return res;
    }

    /**
     * Returns all names from the people table.
     * @return Cursor containing the data
     */
    public Cursor getPeopleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ PEOPLE_TABLE_NAME,null);
        return res;
    }

}
