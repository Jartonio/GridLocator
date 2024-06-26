package com.example.gridlocator;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        double longitudInicial, latitudInicial, precision, latitudGridLocator, longuitudGridLocator;
        double latitudObservador, longuitudObservador, rumbo;
        int distancia;

        String gridLocator;

        String TAG = "Log.GridLocator";

        double sumaDistancias = 0;
        double menor1 = 0;
        double de1a2 = 0;
        double de2a3 = 0;
        double de3a4 = 0;
        double de4a5 = 0;
        double mayor5 = 0;

        //Guardia Cilvil Potes
        latitudObservador = 43.153267133218144;
        longuitudObservador = -4.622447443160071;

        GridLocator miGridLocator = new GridLocator();


        int veces = 1;
        int erroresDeGrid = 0;

        for (int i = 1; i <= veces; i++) {
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            latitudInicial = 42.63112275820696;
            longitudInicial = 0.6566023635214332;

            miGridLocator.setLatitudLonguitud(latitudInicial, longitudInicial);

            gridLocator = miGridLocator.getGridLocator();

            if (!miGridLocator.gridValido(gridLocator)){
                erroresDeGrid++;
            }

            miGridLocator.setGridLocator(gridLocator);

            latitudGridLocator = miGridLocator.getLatitud();
            longuitudGridLocator = miGridLocator.getLonguitud();

            precision = GeoUtilidades.calcularDistancia(latitudInicial, longitudInicial, latitudGridLocator, longuitudGridLocator);

            rumbo = GeoUtilidades.calcularRumbo(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);

            distancia = (int) GeoUtilidades.calcularDistancia(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);

            Log.d(TAG, "Coordenadas del observador: " + latitudObservador + ", " + longuitudObservador);
            Log.d(TAG, "Coordenadas iniciales: " + latitudInicial + ", " + longitudInicial);
            Log.d(TAG, "Coordenadas obtenidas: " + latitudGridLocator + ", " + longuitudGridLocator);
            Log.d(TAG, "Grid Locator: " + gridLocator);
            Log.d(TAG, "Distancia: " + distancia);
            Log.d(TAG, "Rumbo: " + rumbo);
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
        Log.d(TAG, "Errores de Grid: "+ erroresDeGrid);
    }
}