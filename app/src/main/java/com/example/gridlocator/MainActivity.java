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


        long sumaDistancias=0;

        long de0a50=0;
        long de50a100=0;
        long de100a150=0;
        long de150a200=0;
        long mas200=0;

        long veces=1000000;

        for (int i=0;i<=veces;i++) {

            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            grid = CalcularGrid.grid(latitudInicial, longitudInicial);

            longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
            latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);
            distancia = CalcularDistanciaEntreCoordenadas.distancia(latitudInicial, longitudInicial, latitudFinal, longitudFinal);
            sumaDistancias=sumaDistancias+((long)distancia);

            if (distancia<=50){
                de0a50++;
            }
            if (distancia>50 && distancia<=100){
                de50a100++;
            }
            if (distancia>100 && distancia<=150){
                de100a150++;
            }
            if (distancia>150 && distancia<=200){
                de150a200++;
            }
            if (distancia>200){
                mas200++;
            }


        }
        Log.d("prueba", "media de error: "+(long) (sumaDistancias/veces));
        Log.d("prueba", "distancias menores de 50m:    "+de0a50);
        Log.d("prueba", "distancias entre 50m y 100m:  "+de50a100);
        Log.d("prueba", "distancias entre 100m y 150m: "+de100a150);
        Log.d("prueba", "distancias entre 150m y 200m: "+de150a200);
        Log.d("prueba", "distancias de mas de 200m:    "+mas200);



        /*
        Log.d("prueba", "Coordenadas iniciales: " + latitudInicial + ", " + longitudInicial);
        Log.d("prueba", "Coordenadas finales  : " + latitudFinal + ", " + longitudFinal);
        Log.d("prueba", "distancia: " + distancia);
        Log.d("prueba", "Grid coordenadas: : " + grid);
        */



    }


}