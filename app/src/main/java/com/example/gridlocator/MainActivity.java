package com.example.gridlocator;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Brujula brujula;
    private GPS gps;
    private String gradosBrujula;
    private final String TAG = "Log.GridLocator";

    private Handler handlerGPS, handlerGrados;
    private boolean isAppInForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        brujula = new Brujula(this);
        gps = new GPS(this);

        handlerGPS = new Handler(Looper.getMainLooper());
        handlerGrados = new Handler(Looper.getMainLooper());


        if (!brujula.brujulaPresente()) {
            Log.d(TAG, "ERROR, este terminal no tiene la brujula disponible.");
        }


        double longitudInicial, latitudInicial, precision, latitudGridLocator, longuitudGridLocator;
        double latitudObservador, longuitudObservador, azimut, rumboFinal, declinacion;
        int distancia;

        String gridLocator;

    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (brujula.brujulaPresente()) {
        brujula.start();
        startUpdatingDegrees();
        //}
        isAppInForeground = true;
        gps.startListening();
        startUpdatingLocation();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //if (brujula.brujulaPresente()) {
        brujula.stop();
        stopUpdatingDegrees();
        // }
        isAppInForeground = false;
        gps.stopListening();
        stopUpdatingLocation();

    }

    private void startUpdatingLocation() {
        handlerGPS.post(updateLocationRunnable);
    }

    private void stopUpdatingLocation() {
        handlerGPS.removeCallbacks(updateLocationRunnable);
    }


    private void startUpdatingDegrees() {
        handlerGrados.post(updateDegreesRunnable);
    }

    private void stopUpdatingDegrees() {
        handlerGrados.removeCallbacks(updateDegreesRunnable);
    }

    private final Runnable updateLocationRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                Location location = gps.getCurrentLocation();
                if (location != null) {
                    Log.d(TAG, "Coordenadas GPS: " + location.getLatitude() + ", " + location.getLongitude());
                } else {
                    Log.d(TAG, "Coordenadas GPS no disponibles.");
                }
            }
            handlerGPS.postDelayed(this, 1000); // Actualizar cada 5000ms (5 segundos)
        }
    };

    private final Runnable updateDegreesRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                if (brujula.brujulaPresente()) {
                    gradosBrujula = String.format("%.2f°", brujula.getGrados());
                    Log.d(TAG, "Brujula: " + gradosBrujula);
                } else {
                    Log.d(TAG, "Este dispositivo no tiene brújula.");
                }
            }
            handlerGrados.postDelayed(this, 500); // Actualizar cada 5000ms (5 segundos)
        }
    };
}
/*
private void test{
        double sumaDistancias = 0;
        double menor1 = 0;
        double de1a2 = 0;
        double de2a3 = 0;
        double de3a4 = 0;
        double de4a5 = 0;
        double mayor5 = 0;

        GridLocator miGridLocator = new GridLocator();

        int veces = 1;
        int erroresDeGrid = 0;

        for (int i = 1; i <= veces; i++) {
            latitudObservador = GenerarCoordenadasAleatorias.latitudAleatoria();
            longuitudObservador = GenerarCoordenadasAleatorias.longitudAleatoria();
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            latitudObservador = 28.370448199705887;
            longuitudObservador = -16.84997473238369;
            latitudInicial = 28.274177430463826;
            longitudInicial = -16.629580431519543;

            miGridLocator.setLatitudLonguitud(latitudInicial, longitudInicial);

            gridLocator = miGridLocator.getGridLocator();

            if (!miGridLocator.gridValido(gridLocator)) {
                erroresDeGrid++;
            }

            miGridLocator.setGridLocator(gridLocator);

            latitudGridLocator = miGridLocator.getLatitud();
            longuitudGridLocator = miGridLocator.getLonguitud();

            precision = GeoUtilidades.calcularDistancia(latitudInicial, longitudInicial, latitudGridLocator, longuitudGridLocator);

            azimut = GeoUtilidades.calcularAzimut(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);

            distancia = (int) GeoUtilidades.calcularDistancia(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);
            declinacion = GeoUtilidades.calcularDeclinacionMagnetica(latitudObservador, longuitudObservador);
            rumboFinal = azimut + declinacion;

            Log.d(TAG, "Coordenadas del observador: " + latitudObservador + " " + longuitudObservador);
            Log.d(TAG, "Coordenadas iniciales: " + latitudInicial + " " + longitudInicial);
            Log.d(TAG, "Coordenadas obtenidas: " + latitudGridLocator + " " + longuitudGridLocator);
            Log.d(TAG, "Grid Locator: " + gridLocator);
            Log.d(TAG, "Distancia: " + distancia);
            Log.d(TAG, "Declinacion: " + declinacion);
            Log.d(TAG, "Azimut: " + azimut);
            Log.d(TAG, "Azimut final: " + rumboFinal);


            Log.d(TAG, "------------------------------------------");

            sumaDistancias = sumaDistancias + precision;
            if (precision < 1) {
                menor1++;
            } else {
                if (precision >= 1 && precision < 2) {
                    de1a2++;
                } else {
                    if (precision >= 2 && precision < 3) {
                        de2a3++;
                    } else {
                        if (precision >= 3 && precision < 4) {
                            de3a4++;
                        } else {
                            if (precision >= 4 && precision < 5) {
                                de4a5++;
                            } else {
                                mayor5++;
                            }
                        }
                    }
                }
            }

        }
        Log.d(TAG, "Media de error: " + (sumaDistancias / veces));
        Log.d(TAG, "Distancias menores de 1m:   " + menor1);
        Log.d(TAG, "Distancias entre 1m y 2m:  " + de1a2);
        Log.d(TAG, "Distancias entre 2m y 3m: " + de2a3);
        Log.d(TAG, "Distancias entre 3m y 4m: " + de3a4);
        Log.d(TAG, "Distancias entre 4m y 5m: " + de4a5);
        Log.d(TAG, "Distancias de mas de 5m:   " + mayor5);
        Log.d(TAG, "Errores de Grid: " + erroresDeGrid);
        Log.d(TAG, "Brujula: " + gradosBrujula);
    }*/
