package com.example.cutsomnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class GraphHelper extends SQLiteOpenHelper {
    public GraphHelper( Context context) {
        super(context, "Reports.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Reports");
    }
    public Cursor getgraph ()
    {Cursor cursor = null;
        try {


            SQLiteDatabase DB = this.getReadableDatabase();
            cursor = DB.rawQuery("Select * from Reports ", null);

        }
        catch (Exception e){
        }
        return cursor;
    }


}

