package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    SupportMapFragment smf=null;
    MapActivity activity;
    Button btn_co, btn_so2, btn_no2, btn_o3, btn_pm25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_);
        activity=this;
        smf=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        Button btn_co = findViewById(R.id.btn_co);
        Button btn_so2 = findViewById(R.id.btn_so2);
        Button btn_no2 = findViewById(R.id.btn_no2);
        Button btn_o3 = findViewById(R.id.btn_o3);

        Button btn_pm25 = findViewById(R.id.btn_pm25);



    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    smf.getMapAsync(activity);
//                    Log.d("getLocation~",currentLocation.getLatitude()+""+currentLocation.getLongitude());
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//            googleMap.addMarker(markerOptions);


        for(int idx=0; idx <3; idx++){

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(currentLocation.getLatitude()+idx, currentLocation.getLongitude())).title("마커");

            googleMap.addMarker(markerOptions);


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }



}