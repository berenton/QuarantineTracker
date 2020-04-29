package com.example.quarantinetracker.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quarantinetracker.ui.gallery.GalleryFragment;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Raport.db";
    public static final String TABLE_NAME = "Raport_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Year";
    public static final String COL_3 = "Month";
    public static final String COL_4 = "Day";
    public static final String COL_5 = "Hour";
    public static final String COL_6 = "Minute";
    public static final String COL_7 = "Activity_ID";
    public static final String COL_8 = "Location_ID";
    public static final String COL_9 = "Assessment";
    public static final String COL_10 = "People";
    public static final String COL_11 = "Misc";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " INTEGER, "+COL_3+ " INTEGER,"+ COL_4+" INTEGER,"+COL_5+" INTEGER,"+COL_6+" INTEGER,"+COL_7+" INTEGER,"+COL_8+" INTEGER,"+COL_9+" INTEGER,"+COL_10+"INTEGER,"+COL_11+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int year, int month, int day, int hour, int minute, int assessment, String misc){
        int activity = 0;
        int location = 0;
        int people = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, year);
        contentValues.put(COL_3, month);
        contentValues.put(COL_4, day);
        contentValues.put(COL_5, hour);
        contentValues.put(COL_6, minute);
        contentValues.put(COL_7, activity);
        contentValues.put(COL_8, location);
        contentValues.put(COL_9, assessment);
        contentValues.put(COL_10, people);
        contentValues.put(COL_11, misc);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

}
