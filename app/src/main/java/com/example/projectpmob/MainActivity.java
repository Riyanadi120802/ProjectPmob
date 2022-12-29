package com.example.projectpmob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card1 = (CardView) findViewById(R.id.search_button);
        card2 = (CardView) findViewById(R.id.show_button);
        card3 = (CardView) findViewById(R.id.tambah_button);
        card4 = (CardView) findViewById(R.id.help_button);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.search_button:
                i = new Intent(this, SearchLocation.class);
                startActivity(i);
                break;

            case R.id.tambah_button:
                i = new Intent(this, Buat_data.class);
                startActivity(i);
                break;

            case R.id.show_button:
                i = new Intent(this, ShowActivity.class);
                startActivity(i);
                break;

            case R.id.help_button:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                break;
        }
    }
}
