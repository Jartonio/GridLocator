package com.example.gridlocator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class GPS {

    private final Runnable providerCheckRunnable;
    private Context context;
    private LocationManager locationManager;
    private double latitud;
    private double longitud;
    private double altitud;
    private double precision;
    private boolean isGPSEnabled;
    private boolean isPermissionDenied;
    private LocationListener locationListener;
    private long lastUpdateTime;
    private Handler handler;
    private boolean isSignalLost;
    public GPS(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.latitud = 0.0;
        this.longitud = 0.0;
        this.altitud = 0.0;
        this.precision = 0.0;
        this.isGPSEnabled = false;
        this.isPermissionDenied = false;

        // Configurar el LocationListener
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                altitud = location.getAltitude();
                precision = location.getAccuracy();
                lastUpdateTime = System.currentTimeMillis();
                Log.d("GPS2", "GPS2: " + latitud + ", " + longitud);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                isGPSEnabled = true;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                isGPSEnabled = false;
            }
        };
        lastUpdateTime = System.currentTimeMillis();
        checkPermissionsAndStartLocationUpdates();

        this.providerCheckRunnable = new Runnable() {
            @Override
            public void run() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    isSignalLost = true;
                    Toast.makeText(context, "Señal GPS perdida", Toast.LENGTH_SHORT).show();
                } else {
                    isSignalLost = false;
                }
                handler.postDelayed(this, 5000);
            }
        };
    }

    private void checkPermissionsAndStartLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Pedir permisos si no están concedidos
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            isPermissionDenied = true;
        } else {
            // Permisos concedidos, empezar a obtener la ubicación
            isPermissionDenied = false;
            startGettingLocation();
        }
    }

    private void startGettingLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Comprobar si el GPS está activado
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                isGPSEnabled = true;
                // Registrar el LocationListener para recibir actualizaciones de ubicación
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            } else {
                isGPSEnabled = false;
                Toast.makeText(context, "GPS no está activado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public double getPrecision() {
        return precision;
    }

    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    public boolean isPermissionDenied() {
        return isPermissionDenied;
    }

    public void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void startLocationUpdates() {
        if (locationManager != null) {
            startGettingLocation();
        }
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean getisSignalLost(){
        return isSignalLost;
    }

}