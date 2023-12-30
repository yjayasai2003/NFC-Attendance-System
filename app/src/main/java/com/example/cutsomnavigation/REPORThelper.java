package com.example.cutsomnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class REPORThelper extends SQLiteOpenHelper {
    public REPORThelper( Context context) {
        super(context, "Reports.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Reports (dateteach TEXT primary key,present TEXT,absent TEXT,date TEXT,attendancelist TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Reports");
    }

    public Boolean insertreport(String dateteacher,int presentc,int absentc,String dateS,String lists)
    {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dateteach",dateteacher);
        contentValues.put("present",""+presentc);
        contentValues.put("absent",""+absentc);
        contentValues.put("date",""+dateS);
        contentValues.put("attendancelist",lists);
        long result=DB.insert("Reports", "0", contentValues);
        if (result ==1)
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    public Cursor getreport (String dateS)
    {
        Cursor cursor = null;

            SQLiteDatabase DB = this.getWritableDatabase();
            cursor = DB.rawQuery("Select * from Reports where date=? ", new String[]{dateS});
        return cursor;
    }
    public boolean checkreport (String dateS)
    {
        Cursor cursor = null;
        SQLiteDatabase DB = this.getWritableDatabase();
        cursor = DB.rawQuery("Select * from Reports where dateteach=? ", new String[]{dateS});
        if(cursor.getCount()>0)
            return false;
            else
                return true;
    }


}
