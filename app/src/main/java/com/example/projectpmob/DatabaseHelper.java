package com.example.projectpmob;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foodfastdb.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE rm(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, rm text, testimoni text, rating integer)";
        Log.d("Data", "OnCreate: " + sql);
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO rm(nama, rm, testimoni, rating) VALUES( 'Wardana','Padang Murah', 'Enak Banget', 8)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void hapusDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS rm");
        onCreate(db);
    }
}
