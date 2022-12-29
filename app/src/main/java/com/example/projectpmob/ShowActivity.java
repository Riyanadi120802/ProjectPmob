package com.example.projectpmob;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class ShowActivity extends AppCompatActivity {

    public static ShowActivity ma;
    String[] daftar;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DatabaseHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        dbcenter = new DatabaseHelper(this);
        RefreshList();
    }
    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM restaurant", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Lihat Restaurant", "Update Restauran", "Hapus Restauran"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), LihatRestauran.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), update_data.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM restaurant WHERE nama = '" + selection + "'");
                                Toast.makeText(ShowActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                RefreshList();
                                break;
                        }
                    }
                });

                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();

    }
}