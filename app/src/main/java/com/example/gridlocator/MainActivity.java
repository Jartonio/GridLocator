package com.example.gridlocator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double longitudInicial, latitudInicial, longitudFinal, latitudFinal, distancia;

        String grid;

        double sumaDistancias = 0;
        double de0a10 = 0;
        double de10a20 = 0;
        double de20a30 = 0;
        double de30a40 = 0;
        double de40a50 = 0;
        double mas50 = 0;

        long veces = 1000;

        for (int i = 1; i <= veces; i++) {

            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            grid = CalcularGrid.grid(latitudInicial, longitudInicial);

            longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
            latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);

            distancia = CalcularDistanciaEntreCoordenadas.distancia(latitudInicial, longitudInicial, latitudFinal, longitudFinal);
            sumaDistancias = sumaDistancias + distancia;

            if (distancia <= 10) {
                de0a10++;
            }
            if (distancia > 10 && distancia <= 20) {
                de10a20++;
            }
            if (distancia > 20 && distancia <= 30) {
                de20a30++;
            }
            if (distancia > 30 && distancia <= 40) {
                de30a40++;
            }
            if (distancia > 40 && distancia <= 50) {
                de40a50++;
            }
            if (distancia > 50) {
                mas50++;
            }
        }
/*
            Log.d("prueba", "Coordenadas iniciales: " + latitudInicial + ", " + longitudInicial);
            Log.d("prueba", "Coordenadas finales  : " + latitudFinal + ", " + longitudFinal);
            Log.d("prueba", "distancia: " + distancia);
            Log.d("prueba", "Grid coordenadas: : " + grid);



*/
            Log.d("prueba", "Media de error: " + (sumaDistancias / veces));
            Log.d("prueba", "Distancias menores de 10m:   " + de0a10);
            Log.d("prueba", "Distancias entre 10m y 20m:  " + de10a20);
            Log.d("prueba", "Distancias entre 20m y 30m: " + de20a30);
            Log.d("prueba", "Distancias entre 30m y 40m: " + de30a40);
            Log.d("prueba", "Distancias entre 40m y 50m: " + de40a50);
            Log.d("prueba", "Distancias de mas de 50m:   " + mas50);

    }
}