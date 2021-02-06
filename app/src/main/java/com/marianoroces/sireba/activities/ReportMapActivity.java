package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.marianoroces.sireba.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        refreshMap();

        if(getIntent().getStringExtra("startedFrom").equalsIgnoreCase("Create Report")) {
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    mMap.clear();
                    Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = new ArrayList<>();

                    try {
                        addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        Log.d("DEBUG", e.getMessage());
                    }
                    String addressAux = addresses.get(0).getAddressLine(0);
                    String realAddres = addressAux.substring(0, addressAux.indexOf(","));
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(realAddres)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportMapActivity.this);
                    builder.setMessage("Seleccionar ubicacion?")
                            .setTitle(realAddres)
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent mapLocation = new Intent();
                                    mapLocation.putExtra("address", realAddres);
                                    mapLocation.putExtra("mapLat", latLng.latitude);
                                    mapLocation.putExtra("mapLng", latLng.longitude);
                                    setResult(RESULT_OK, mapLocation);
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();
                }
            });
        } else {
            LatLng reportLocation = new LatLng(
                    getIntent().getDoubleExtra("lat", 00.000000000000000),
                    getIntent().getDoubleExtra("lng", 00.000000000000000)
            );

            Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = new ArrayList<>();

            try {
                addresses = geoCoder.getFromLocation(reportLocation.latitude, reportLocation.longitude, 1);
            } catch (IOException e) {
                Log.d("DEBUG", e.getMessage());
            }
            String addressAux = addresses.get(0).getAddressLine(0);
            String realAddres = addressAux.substring(0, addressAux.indexOf(","));

            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(reportLocation, 15));
            mMap.addMarker(new MarkerOptions()
                    .position(reportLocation)
                    .title(realAddres)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }

    private void refreshMap(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }

        if(getIntent().getStringExtra("startedFrom").equalsIgnoreCase("Create Report")) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                            }
                        }
                    });
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 9999:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    refreshMap();
                }
                return;
        }
    }
}