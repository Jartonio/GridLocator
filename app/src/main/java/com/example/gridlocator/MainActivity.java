package com.example.gridlocator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double longitudInicial, latitudInicial, distancia;

        String grid, tag = "Log.GridLocator";

        double sumaDistancias = 0;
        double de0a5 = 0;
        double de5a10 = 0;
        double de10a20 = 0;
        double de20a30 = 0;
        double de30a40 = 0;
        double de40a50 = 0;
        double mas50 = 0;

        long veces = 10000;

        GridLocator miGridLocator = new GridLocator();

        for (int i = 1; i <= veces; i++) {
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            miGridLocator.setLatitudLonguitud(latitudInicial, longitudInicial);

            distancia = miGridLocator.getDesviacion();
            sumaDistancias = sumaDistancias + distancia;

            if (distancia <= 5) {
                de0a5++;
            } else {
                if (distancia > 5 && distancia <= 10) {
                    de5a10++;
                } else {
                    if (distancia > 10 && distancia <= 20) {
                        de10a20++;
                    } else {
                        if (distancia > 20 && distancia <= 30) {
                            de20a30++;
                        } else {
                            if (distancia > 30 && distancia <= 40) {
                                de30a40++;
                            } else {
                                if (distancia > 40 && distancia <= 50) {
                                    de40a50++;
                                } else {
                                    mas50++;
                                }
                            }
                        }
                    }
                }
            }
        }

        Log.d(tag, "Media de error: " + (sumaDistancias / veces));
        Log.d(tag, "Distancias menores de 5m:   " + de0a5);
        Log.d(tag, "Distancias entre 5m y 10m:  " + de5a10);
        Log.d(tag, "Distancias entre 10m y 20m: " + de10a20);
        Log.d(tag, "Distancias entre 20m y 30m: " + de20a30);
        Log.d(tag, "Distancias entre 30m y 40m: " + de30a40);
        Log.d(tag, "Distancias entre 40m y 50m: " + de40a50);
        Log.d(tag, "Distancias de mas de 50m:   " + mas50);
    }
}