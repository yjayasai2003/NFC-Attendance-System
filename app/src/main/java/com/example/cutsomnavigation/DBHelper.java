package com.example.cutsomnavigation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
;
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "Attendance.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Attendance (regd TEXT primary key,status TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Attendance");
    }

    public Boolean insertfirstdata(String regd)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regd",regd);
        contentValues.put("status","A");
        long result=DB.insert("Attendance", "0", contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }
    public Boolean updateuserdata(String regd)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regd", regd);
        contentValues.put("status", "P");
        Cursor cursor = DB.rawQuery("Select * from Attendance where regd = ?", new String[]{regd});
        if (cursor.getCount() > 0) {
            long result = DB.update("Attendance", contentValues, "regd=?", new String[]{regd});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean updateback(String regd)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regd", regd);
        contentValues.put("status", "A");
        Cursor cursor = DB.rawQuery("Select * from Attendance where regd = ?", new String[]{regd});
        if (cursor.getCount() > 0) {
            long result = DB.update("Attendance", contentValues, "regd=?", new String[]{regd});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public Cursor getdata (int i)
    {
        Cursor cursor = null;
        if(i==1)
        {
            SQLiteDatabase DB = this.getWritableDatabase();
             cursor = DB.rawQuery("Select * from Attendance where status=\"P\" ", null);
           
        }
        if(i==0)
        {
        SQLiteDatabase DB = this.getWritableDatabase();
         cursor = DB.rawQuery("Select * from Attendance where status=\"A\"  ", null);
        
    }
        return cursor;
    }


}
