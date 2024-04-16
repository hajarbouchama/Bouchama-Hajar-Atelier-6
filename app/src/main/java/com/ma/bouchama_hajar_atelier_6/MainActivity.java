package com.ma.bouchama_hajar_atelier_6;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private LocationManager locationManager;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Button btnGetLocation = findViewById(R.id.get_location_button);
        tvLocation = findViewById(R.id.location_text);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, get the location
            getLocation();
        } else {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getLocation() {
        // Check if GPS or Network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                return;
            }
            Location lastKnownLocation1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location lastKnownLocation2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            displayLocation(lastKnownLocation1,lastKnownLocation2);
        } else {
            // Provider is not enabled, show a message to the user
            Toast.makeText(this, "Location provider is not enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayLocation(Location location1,Location location2)
    {
        double latitude = location2.getLatitude();
        double longitude = location2.getLongitude();
        double altitude = location2.getAltitude();
            /*double latitude1 = location1.getLatitude();
            double longitude1 = location1.getLongitude();
            double altitude1 = location1.getAltitude();*/
        String locationText = "Votre location avec le reseau \nLatitude: " + latitude + "\nLongitude: " + longitude+ "\nAltitude: " + altitude;
        //String locationText1 = "Votre location avec le gps \nLatitude: " + latitude1 + "\nLongitude: " + longitude1+ "\nAltitude: " + altitude1;
        tvLocation.setText(locationText);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the location
                getLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}