package com.example.projectpmob;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foodfast.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE restaurant(id integer PRIMARY KEY AUTOINCREMENT, nama text, alamat text, rating integer)";
        Log.d("Data", "OnCreate: " + sql);
        sqLiteDatabase.execSQL(sql);
//        sql = "INSERT INTO restaurant(id, nama, alamat, rating) VALUES(1, 'RM.Padang Jaya', 'JL.Bandung 12', 8)";
//        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void hapusDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS restaurant");
        onCreate(db);
    }
}