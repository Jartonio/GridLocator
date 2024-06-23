package com.example.gridlocator;

public class CalcularGrid {

    public static String grid(double latitudInicial, double longitudInicial) {



        //Este metodo calcula el Grid Locator de Maidenhead a partir de una latitud y una longuitud.
        //Entrega un Grid en formato: AA00AA00AA (18x10x24x10x24).

        double longitud = longitudInicial + 180;
        double latitud = latitudInicial + 90;

        double grados,totalCuadros,ultimo,diferenciaCuadros;
        int cuadrosAnteriores, numeroGrid,divisiones;

        char[] grid = new char[10];

        // longitud cuadricula 1
        grados = 20;
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
        grados = 0.0833333333;
        divisiones = 24;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[4] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 4
        grados = 0.0083333333;
        divisiones = 10;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[6] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //longitud cuadricula 5
        grados = 0.0003472222;
        divisiones = 24;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[8] = (char) ('A' + numeroGrid);
        //ultimo = totalCuadros;  --  Activar si se va a 6 cuadriculas.


        /*longitud cuadricula 6
        grados = 0.0000347221;
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
        grados = 0.0416666666;
        divisiones = 24;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[5] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;


        //latitud cuadricula 4
        grados = 0.0041666666;
        divisiones = 10;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[7] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;


        //latitud cuadricula 5
        grados = 0.0001736111;
        divisiones = 24;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[9] = (char) ('A' + numeroGrid);
        //ultimo = totalCuadros; --  Activar si se va a 6 cuadriculas.

        /*longitud cuadricula 6
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
