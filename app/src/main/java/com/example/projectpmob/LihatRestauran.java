package com.example.projectpmob;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatRestauran extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button ton2;
    TextView text1, text2, text3, text4, text5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_restauran);
        dbHelper = new DatabaseHelper(this);
        text1 = (TextView) findViewById(R.id.textViewliat1);
        text2 = (TextView) findViewById(R.id.textViewliat2);
        text3 = (TextView) findViewById(R.id.textViewliat3);
        text4 = (TextView) findViewById(R.id.textViewliat4);
//        text5 = (TextView) findViewById(R.id.textViewliat5);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM restaurant WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
//            text5.setText(cursor.getString(4).toString());
        }

        ton2 = (Button) findViewById(R.id.button1);
        ton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }

}