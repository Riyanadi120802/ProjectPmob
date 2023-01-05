package com.example.projectpmob;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Buat_data extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button btn1, btn2;
    EditText text1, text2, text3,text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_data);
        dbHelper = new DatabaseHelper(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO rm(nama, rm, testimoni, rating) VALUES ('" +
                        text1.getText().toString() +"','" +
                        text2.getText().toString() +"','" +
                        text3.getText().toString() +"','" +
                        text4.getText() +"')" );
                Toast.makeText(getApplicationContext(), "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
                ShowActivity.ma.RefreshList();
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
}