package com.example.gridlocator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("prueba", "" + calcularGrid(43.27233, -4.99110));

    }

    public double calcularLongitud(String grid) {

        char[] miGrid = grid.toCharArray();
        double lo0, lo2, lo4, lo6, lo8;


        double longitud, latitud;

        lo0 = (miGrid[0] - 'A') * 20;
        lo2 = (miGrid[2] - '0') * 2;
        lo4 = (miGrid[4] - 'A') * 0.083333333333;
        lo6 = (miGrid[6] - '0') * 0.008333333333;
        lo8 = (miGrid[8] - 'A') * 0.000347222222;
        longitud = lo0 + lo2 + lo4 + lo6 + lo8 - 180;


        Log.d("prueba", "Longitud calculada: " + longitud);
        return longitud;

    }

    public double calcularLatitud(String grid) {

        char[] miGrid = grid.toCharArray();
        double la1, la3, la5, la7, la9;
        double latitud;

        la1 = (miGrid[1] - 'A') * 10;
        la3 = (miGrid[3] - '0') * 1;
        la5 = (miGrid[5] - 'A') * 0.041666666666;
        la7 = (miGrid[7] - '0') * 0.004166666666;
        la9 = (miGrid[9] - 'A') * 0.000173611111;
        latitud = la1 + la3 + la5 + la7 + la9 - 90;
        Log.d("prueba", "Latitud calculada: " + latitud);

        return (latitud);
    }

    public String calcularGrid(double latitudInicial, double longitudInicial) {

        char[] grid = new char[10];

        double longitud = longitudInicial + 180;
        double latitud = latitudInicial + 90;

        double grados = 0;
        int divisiones = 0;
        double totalCuadros = 0;
        double ultimo = 0;
        double diferenciaCuadros = 0;
        int cuadrosAnteriores;
        int numeroGrid = 0;


        // longitud cuadricula 1
        grados = 20;
        divisiones = 18;
        totalCuadros = longitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[0] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 2
        grados = 2;
        divisiones = 10;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[2] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 3
        grados = 0.083333;
        divisiones = 24;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[4] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 4
        grados = 0.0083333;
        divisiones = 10;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[6] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 5
        grados = 0.000347221;
        divisiones = 24;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[8] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 6
       /* grados = 0.0000347221;
        divisiones = 10;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int)(ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int)diferenciaCuadros;
        grid[10] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;
        */

        // latitud cadricula 1
        grados = 10;
        divisiones = 18;
        totalCuadros = latitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[1] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 2
        grados = 1;
        divisiones = 10;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[3] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //latitud cuadricula 3
        grados = 0.0416665;
        divisiones = 24;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[5] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;


        //latitud cuadricula 4
        grados = 0.00416665;
        divisiones = 10;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[7] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //latitud cuadricula 5
        grados = 0.00017361;
        divisiones = 24;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[9] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 6
        /*
        grados = 0.000017361;
        divisiones = 10;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int)(ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int)diferenciaCuadros;
        grid[11] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;
        */
        return String.valueOf(grid);

    }


}