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

        longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();
        latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();

        grid = CalcularGrid.grid(latitudInicial, longitudInicial);

        longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
        latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);
        distancia=CalcularDistanciaEntreCoordenadas.distancia(latitudInicial,longitudInicial,latitudFinal,longitudFinal);

    }


}