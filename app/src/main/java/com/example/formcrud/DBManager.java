package com.example.formcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(nim TEXT primary key, nama TEXT, jurusan TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertuserdata(String nim, String nama, String jurusan) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nim", nim);
        contentValues.put("nama", nama);
        contentValues.put("jurusan", jurusan);

        long result=DB.insert("Userdetails", null, contentValues);
        if(result==-1){
            Log.d("DBManager", "Gagal memasukkan data: " + nim + ", " + nama + ", " + jurusan);
            return false;
        }else{
            Log.d("DBManager", "Berhasil memasukkan data: " + nim + ", " + nama + ", " + jurusan);
            return true;
        }
    }

    public Boolean updateuserdata(String nim, String nama, String jurusan) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nama", nama);
        contentValues.put("jurusan", jurusan);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where nim = ?", new String[]{nim});

        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "nim=?", new String[]{nim});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata (String nim) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where nim = ?", new String[]{nim});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "nim=?", new String[]{nim});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }
}