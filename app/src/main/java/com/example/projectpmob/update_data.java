package com.example.projectpmob;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update_data extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        dbHelper = new DatabaseHelper(this);
        text1 = (EditText) findViewById(R.id.editTextup1);
        text2 = (EditText) findViewById(R.id.editTextup2);
        text3 = (EditText) findViewById(R.id.editTextup3);
        text4 = (EditText) findViewById(R.id.editTextup4);
        ton1 = (Button) findViewById(R.id.button1);
        ton2 = (Button) findViewById(R.id.button2);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM restaurant WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
        }

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE restaurant SET nama='"+
                        text2.getText().toString() +"', alamat='" +
                        text3.getText().toString()+"', rating='"+
                        text4.getText().toString() + "' WHERE id='" +
                        text1.getText().toString()+"'");
                Toast.makeText(getApplicationContext(), "Data berhasil di update", Toast.LENGTH_LONG).show();
                if (ShowActivity.ma != null) {
                    ShowActivity.ma.RefreshList();
                }
                finish();
            }
        });

        ton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}