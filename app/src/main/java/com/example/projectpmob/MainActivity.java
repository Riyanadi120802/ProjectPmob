package com.example.projectpmob;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static ShowActivity main;
    Button search, show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
//        db.perbarui(db.getWritableDatabase());

        search = (Button) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent cari = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(cari);
                }
        });

        show = (Button) findViewById(R.id.ButtonShow);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(pindah);
            }
        });



    }
}