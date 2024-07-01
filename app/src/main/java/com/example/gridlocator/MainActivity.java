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

        String tag = "Log.GridLocator";

        double sumaDistancias = 0;
        double menor1 = 0;
        double de1a2 = 0;
        double de2a3 = 0;
        double de3a4 = 0;
        double de4a5 = 0;
        double mayor5 = 0;

        GridLocator miGridLocator = new GridLocator();


        int veces = 10;
        int error = 0;

        for (int i = 1; i <= veces; i++) {
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            //latitudInicial= 12;
            //longitudInicial= -12;

            miGridLocator.setLatitudLonguitud(latitudInicial, longitudInicial);


            Log.d(tag, "Coordenadas:      "+ latitudInicial+", "+longitudInicial);
            Log.d(tag, "Grid: "+ miGridLocator.getGridLocator());
            miGridLocator.setGridLocator(miGridLocator.getGridLocator());
            Log.d(tag, "Coordenadas Grid: "+ GeoUtilidades.formatearCoordenadas(miGridLocator.getLatitud(),miGridLocator.getLonguitud()));
            Log.d(tag, "------------------------------------------");

            distancia = GeoUtilidades.calculateDistancia(latitudInicial,longitudInicial,miGridLocator.getLatitud(),miGridLocator.getLonguitud());
            sumaDistancias = sumaDistancias + distancia;

            if (distancia < 1) {
                menor1++;
            } else {
                if (distancia > 1 && distancia <= 2) {
                    de1a2++;
                } else {
                    if (distancia > 2 && distancia <= 3) {
                        de2a3++;
                    } else {
                        if (distancia > 3 && distancia <= 4) {
                            de3a4++;
                        } else {
                            if (distancia > 4 && distancia <= 5) {
                                de4a5++;
                            } else {
                                mayor5++;
                            }
                        }
                    }
                }
            }


        }
        Log.d(tag, "Media de error: " + (sumaDistancias / veces));
        Log.d(tag, "Distancias menores de 1m:   " + menor1);
        Log.d(tag, "Distancias entre 1m y 2m:  " + de1a2);
        Log.d(tag, "Distancias entre 2m y 3m: " + de2a3);
        Log.d(tag, "Distancias entre 3m y 4m: " + de3a4);
        Log.d(tag, "Distancias entre 4m y 5m: " + de4a5);
        Log.d(tag, "Distancias de mas de 5m:   " + mayor5);
    }
}