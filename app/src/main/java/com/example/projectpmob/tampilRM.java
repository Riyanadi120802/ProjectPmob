package com.example.projectpmob;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import com.example.projectpmob.R;

public class tampilRM extends AppCompatActivity {
    private String idRM;
    private String kirim;
    private Double latNya;
    private Double lonNya;
    private String KEY_NAME = "idRM";
    private String kunci_lat = "lat";
    private String kunci_lon = "Lon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_rm);
        pencarian rm = new pencarian();
        Button telepon = findViewById(R.id.btn_telepon);
        Button arah = findViewById(R.id.arahin);
        ImageView Gambar = findViewById(R.id.iv_Gambar);
        TextView Alamat = findViewById(R.id.tv_Alamat);
        TextView noTelp = findViewById(R.id.tv_NomorTelepon);
        TextView keterangan = findViewById(R.id.tv_Keterangan);
        Bundle extras = getIntent().getExtras();
        kirim = extras.getString(KEY_NAME);
        latNya = extras.getDouble(kunci_lat);
        lonNya = extras.getDouble(kunci_lon);

        if(kirim.equals("RM Pondok Ndeso")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());

            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));

                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya)
                        +" dan longitude " + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Rumah Makan Masakan Padang Tamanan")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());
            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Rumah Makan Yogi Rohim Hidayat")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());
            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Rumah Makan dan Sate Padang Batusangka")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());
            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Rumah Makan Padang Salero Kito")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());

            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();

            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Ayam Geprek 77 Mas Agus")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());

            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Bale Ayu Giwangan")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());
            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        if(kirim.equals("Rumah Makan Sabar Bundo")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());
            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });

        }
        if(kirim.equals("Rumah Makan & Seafood 99")){
            rm.setDataRM(kirim);
            setTitle(rm.getNamaRM().toString());
            Resources res = getResources();
            String mDrawableName = rm.getImageRM();
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Gambar.setImageResource(resID);
            Alamat.setText(rm.getAlamatRM());
            noTelp.setText(rm.getNoTelp());

            if((latNya == 0.0 || latNya == null) && (lonNya == 0.0 || lonNya == null) ){
                keterangan.setText(rm.getKetRM());
            }else{
                Double tujuanLat = (rm.getlatTujuan()*(3.14/180));
                Double tujuanLon = (rm.getlonTujuan()*(3.14/180));
                Double a = 2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((latNya*(3.14/180)-tujuanLat)/2),2))+Math.cos(latNya*(3.14/180))
                        *Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-lonNya*(3.14/180))/2),2))));
                keterangan.setText("Dari hasil perhitungan jarak pada posisi kamu di titik latitude " + new DecimalFormat("##.####").format(latNya) +" dan longitude "
                        + new DecimalFormat("##.####").format(lonNya) +" ke " + rm.getNamaRM() + " didapatkan perkiraan jarak " + new DecimalFormat("##.##").format(a) + " KM.");
            }
            final String telpon = rm.getNoTelp();
            final Double lat = rm.getlatTujuan();
            final Double lon = rm.getlonTujuan();
            telepon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+telpon.toString()));
                    startActivity(callIntent);
                }
            });
            arah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tampilRM.this);
                        alertDialogBuilder.setMessage("GPS belum aktif. Silahkan aktifkan terlebih dahulu.")
                                .setCancelable(false)
                                .setPositiveButton("Sentuh disini untuk masuk ke pengaturan GPS",
                                        new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int id){
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }else {
                        if(isNetworkAvailable() == false){
                            Toast.makeText(tampilRM.this, "Tidak ada koneksi internet!!", Toast.LENGTH_SHORT).show();
                        }else{
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}