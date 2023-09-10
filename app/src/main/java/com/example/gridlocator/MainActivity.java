package com.example.gridlocator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double longitudInicial, latitudInicial, longitudFinal, latitudFinal, distancia;
        String grid;


        latitudInicial=GenerarCoordenadasAleatorias.latitudAleatoria();
        longitudInicial= GenerarCoordenadasAleatorias.longitudAleatoria();

        grid = CalcularGrid.grid(latitudInicial, longitudInicial);

        longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
        latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);
        distancia=CalcularDistanciaEntreCoordenadas.distancia(latitudInicial,longitudInicial,latitudFinal,longitudFinal);


        Log.d("prueba", "Coordenadas iniciales: " + latitudInicial+", "+ longitudInicial);
        Log.d("prueba", "Coordenadas finales  : " + latitudFinal+", "+ longitudInicial);
        Log.d("prueba", "distancia: "+ distancia);
        Log.d("prueba", "Grid coordenadas: : "+ grid);


        grid=CalcularGridCuadriculas.grid(latitudInicial,longitudInicial);

        longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
        latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);
        distancia=CalcularDistanciaEntreCoordenadas.distancia(latitudInicial,longitudInicial,latitudFinal,longitudFinal);

        Log.d("prueba", "Coordenadas iniciales: " + latitudInicial+", "+ longitudInicial);
        Log.d("prueba", "Coordenadas finales  : " + latitudFinal+", "+ longitudInicial);
        Log.d("prueba", "distancia: "+ distancia);
        Log.d("prueba", "Grid coordenadas: : "+ grid);


        //Log.d("prueba", "Grid:  "+grid);


    }


}