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

        double longitudInicial,latitudInicial,longitudFinal,latitudFinal,distancia;
        String grid;

        TextView display=findViewById(R.id.Display);
        display.setText("Calculando....");

        long distancias=0;
        long veces=100000;
        int max050=0,max50100=0, max100150=0,max150200=0, max200=0;
        for (int c=1;c<veces;c++){
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();

            grid = CalcularGrid.grid(latitudInicial, longitudInicial);

            longitudFinal = CalcularCoordenadasDesdeGrid.longitud(grid);
            latitudFinal = CalcularCoordenadasDesdeGrid.latitud(grid);
            distancia = CalcularDistanciaEntreCoordenadas.distancia(latitudInicial, longitudInicial, latitudFinal, longitudFinal);
            distancias=distancias+(long)distancia;

            if (distancia<=50){
                max050++;
            }
            if (distancia > 50 && distancia <= 100) {
                max50100++;
            }
            if (distancia > 100 && distancia <= 150) {
                max100150++;
            }
            if (distancia > 150 && distancia <= 200) {
                max150200++;
            }

            if (distancia>200){
                max200++;
            }

        }
        display.setText("Calculo terminado.");
        Log.d("prueba", "Distancias entre 0 y 50: "+max050);
        Log.d("prueba", "Distancias entre 50 y 100: "+max50100);
        Log.d("prueba", "Distancias entre 100 y 150: "+max100150);
        Log.d("prueba", "Distancias entre 150 y 200: "+max150200);
        Log.d("prueba", "Distancias mas de 200: "+max200);




        //Log.d("prueba", ""+latitudInicial+", "+longitudInicial);
        //Log.d("prueba", ""+latitudFinal+", "+longitudFinal);
        //Log.d("prueba",""+ distancia);
        //Log.d("prueba", ""+(distancias/1000));











    }





}