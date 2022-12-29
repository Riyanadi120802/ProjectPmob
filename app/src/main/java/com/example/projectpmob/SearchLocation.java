package com.example.projectpmob;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLocation extends AppCompatActivity {
    private static final String TAG = SearchLocation.class.getSimpleName();
    @BindView(R.id.tvRS)
    TextView txtLocationResult;
    @BindView(R.id.tv3)
    TextView txtJarak;
    private String mLastUpdateTime;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    ArrayList<Double> hasilhitung = new ArrayList<Double>();
    ArrayList<String> namaRM = new ArrayList<String>();
    pencarian rm = new pencarian();
    public Double asalLat;
    public Double asalLon;
    Double tujuanLat;
    Double tujuanLon;
    ArrayList<rmList> RMnya = new ArrayList<rmList>();
    String KEY_NAME = "idRM";
    String kirim;
    String kunci_lat = "lat";
    String kunci_lon = "Lon";
    Double lat;
    Double lon;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        setTitle("Pencarian Terdekat");
        ButterKnife.bind(this);
        init();
        restoreValuesFromBundle(savedInstanceState);
        startLocationButtonClick();
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI();
            }
        };
        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }
        updateLocationUI();
    }
    private void updateLocationUI() {

        if (mCurrentLocation != null) {
            asalLat = mCurrentLocation.getLatitude();
            asalLon = mCurrentLocation.getLongitude();
            lat = asalLat;
            lon = asalLon;
            for (int i=0;i<9; i++){
                rm.setDataRm(i+1);
                namaRM.add(rm.getNamaRM());
                tujuanLat = (rm.getlatTujuan()*(3.14/180));
                tujuanLon = (rm.getlonTujuan()*(3.14/180));
                hasilhitung.add(2*6371*Math.asin(Math.sqrt((Math.pow(Math.sin((asalLat*(3.14/180)-tujuanLat)/2),2))+Math.cos(asalLat*(3.14/180))*Math.cos(tujuanLat)*(Math.pow(Math.sin((tujuanLon-asalLon*(3.14/180))/2),2)))));
            }

            if(hasilhitung.size() == 9){
                for (int i=0;i<hasilhitung.size(); i++) {
                    Double a = hasilhitung.get(i);
                    RMnya.add(new rmList(namaRM.get(i), a ));
                }

                Collections.sort(RMnya, new Comparator<rmList>() {
                    @Override
                    public int compare(rmList o1, rmList o2) {
                        return o1.getJarakRM().compareTo(o2.getJarakRM());
                    }
                });
                txtLocationResult.setText(
                        RMnya.get(0).getnamaRM().toString() + " "
                );

                txtJarak.setText(
                        "diperkirakan " + new DecimalFormat("##.##").format(RMnya.get(0).getJarakRM()) + " KM dari posisi kamu."
                );

                rm.setAmbilLagi(RMnya.get(0).getnamaRM().toString());
                Button detailBtn = findViewById(R.id.detailButton);
                detailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kirim = rm.getNamaRM();

                        if (kirim.equals("RM Pondok Ndeso")){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Masakan Padang Tamanan"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Yogi Rohim Hidayat"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan dan Sate Padang Batusangka"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Padang Salero Kito"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Ayam Geprek 77 Mas Agus"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Bale Ayu Giwangan"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Sabar Bundo"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan & Seafood 99"){
                            Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                            intent.putExtra(KEY_NAME, kirim);
                            intent.putExtra(kunci_lat, lat);
                            intent.putExtra(kunci_lon, lon);
                            startActivity(intent);
                        }
                    }
                });

                rm.setAmbilLagi(RMnya.get(0).getnamaRM().toString());
                Button arahinBtn = findViewById(R.id.arahkanButton);
                arahinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kirim = rm.getNamaRM();
                        if (kirim == "RM Pondok Ndeso"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Masakan Padang Tamanan"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Yogi Rohim Hidayat"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan dan Sate Padang Batusangka"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Padang Salero Kito"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Ayam Geprek 77 Mas Agus"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Bale Ayu Giwangan"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan Sabar Bundo"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        if (kirim == "Rumah Makan & Seafood 99"){
                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + rm.getlatTujuan() + "," + rm.getlonTujuan() + "&travelmode=driving";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }
                });

                rm.setAmbilLagi(RMnya.get(0).getnamaRM().toString());
                Button hubungiBtn = findViewById(R.id.hubungiBtn);
                hubungiBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kirim = rm.getNamaRM();
                        if (kirim == "RM Pondok Ndeso"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan Masakan Padang Tamanan"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan Yogi Rohim Hidayat"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan dan Sate Padang Batusangka"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan Padang Salero Kito"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Ayam Geprek 77 Mas Agus"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Bale Ayu Giwangan"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan Sabar Bundo"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }

                        if (kirim == "Rumah Makan & Seafood 99"){
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+rm.getNoTelp().toString()));
                            startActivity(callIntent);
                        }
                    }
                });

                RMnya.remove(0);

            }
            rmListAdapter adapter = new rmListAdapter(this,RMnya);
            ListView listView = findViewById(R.id.listviewCari);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String kirim  = RMnya.get(position).getnamaRM();
                    if (kirim.equals("RM Pondok Ndeso")){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan Masakan Padang Tamanan"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan Yogi Rohim Hidayat"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan dan Sate Padang Batusangka"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan Padang Salero Kito"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Ayam Geprek 77 Mas Agus"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Bale Ayu Giwangan"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan Sabar Bundo"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }

                    if (kirim == "Rumah Makan & Seafood 99"){
                        Intent intent = new Intent(SearchLocation.this, tampilRM.class);
                        intent.putExtra(KEY_NAME, kirim);
                        intent.putExtra(kunci_lat, lat);
                        intent.putExtra(kunci_lon, lon);
                        startActivity(intent);
                    }
                }
            });
            stopLocationUpdates();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
    }
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(SearchLocation.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(SearchLocation.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                        updateLocationUI();
                    }
                });
    }
    public void startLocationButtonClick() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    public void stopLocationUpdates() {
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }
    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();
    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }
}
