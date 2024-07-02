package com.example.gridlocator;

import android.util.Log;

public class GridLocator {

    private double latitud, longuitud;
    private String gridLocator;

    public GridLocator() {
        this.latitud = 0.0;
        this.longuitud = 0.0;
        this.gridLocator = "JJ00AA00AA00";
    }

    public void setLatitudLonguitud(double miLatitud, double miLonguitud) {
        this.latitud = miLatitud;
        this.longuitud = miLonguitud;
        calcularGrid();
    }

    public void setGridLocator(String grid) {

        char[] miGrid = grid.toCharArray();

        this.gridLocator = grid;
        calcularLatitudGrid();
        calcularLongitudGrid();
    }

    public String getGridLocator() {
        return this.gridLocator;
    }

    public double getLatitud() {
        return this.latitud;
    }

    public double getLonguitud() {
        return this.longuitud;
    }

    public boolean coordenadasValidas(double miLatitud, double miLonguitud) {
        if (miLatitud >= -90.0 && miLatitud <= 90.0 && miLonguitud >= -180.0 && miLonguitud <= 180.0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean gridValido(String miGridLocator) {

        char[] miGrid = miGridLocator.toCharArray();

        if (miGrid[0] < 'A' || miGrid[0] > 'R' || miGrid[1] < 'A' || miGrid[1] > 'R' ||
                miGrid[2] < '0' || miGrid[2] > '9' || miGrid[3] < '0' || miGrid[3] > '9' ||
                miGrid[4] < 'A' || miGrid[4] > 'X' || miGrid[5] < 'A' || miGrid[5] > 'X' ||
                miGrid[6] < '0' || miGrid[6] > '9' || miGrid[7] < '0' || miGrid[7] > '9' ||
                miGrid[8] < 'A' || miGrid[8] > 'X' || miGrid[9] < 'A' || miGrid[9] > 'X' ||
                miGrid.length != 12) {
            return false;
        } else {
            return true;
        }

    }

    private void calcularGrid() {

        //Este metodo calcula el Grid Locator de Maidenhead a partir de una latitud y una longuitud.
        //Entrega un Grid en formato: AA00AA00AA00 (18x10x24x10x24x10).

        double miLongitud = longuitud + 180;
        double miLatitud = latitud + 90;

        double grados, totalCuadros, ultimo, diferenciaCuadros;
        int cuadrosAnteriores, numeroGrid, divisiones;

        char[] grid = new char[12];

        // longitud cuadricula 1
        divisiones = 18;
        grados = 360f / divisiones;
        totalCuadros = miLongitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[0] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 2
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLongitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[2] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 3
        divisiones = 24;
        grados = grados / divisiones;

        totalCuadros = miLongitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[4] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 4
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLongitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[6] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 5
        divisiones = 24;
        grados = grados / divisiones;
        totalCuadros = miLongitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[8] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 6
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLongitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[10] = (char) ('0' + numeroGrid);


        // latitud cadricula 1
        divisiones = 18;
        grados = 180f / divisiones;
        totalCuadros = miLatitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[1] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 2
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLatitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[3] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 3
        divisiones = 24;
        grados = grados / divisiones;
        totalCuadros = miLatitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[5] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 4
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLatitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[7] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 5
        divisiones = 24;
        grados = grados / divisiones;
        totalCuadros = miLatitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[9] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 6
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = miLatitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[11] = (char) ('0' + numeroGrid);
        this.gridLocator = String.valueOf(grid);
    }

    private void calcularLongitudGrid() {
        //Este metodo devuelve la longitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double miLongitud, lo0, lo2, lo4, lo6, lo8, lo10;

        lo0 = (miGrid[0] - 'A') * ((double) 360 / 18);
        lo2 = (miGrid[2] - '0') * ((double) 360 / 18 / 10);
        lo4 = (miGrid[4] - 'A') * ((double) 360 / 18 / 10 / 24);
        lo6 = (miGrid[6] - '0') * ((double) 360 / 18 / 10 / 24 / 10);
        lo8 = (miGrid[8] - 'A') * ((double) 360 / 18 / 10 / 24 / 10 / 24);
        lo10 = (miGrid[10] - '0') * ((double) 360 / 18 / 10 / 24 / 10 / 24 / 10);
        miLongitud = (lo0 + lo2 + lo4 + lo6 + lo8 + lo10) - 180;
        miLongitud = miLongitud + (((double) 360 / 18 / 10 / 24 / 10 / 24 / 10) / 2);//Se divide entre 2 para centrar a la cuadricula final
        this.longuitud = miLongitud;
    }

    private void calcularLatitudGrid() {
        //Este metodo devuelve la latitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double miLatitud, la1, la3, la5, la7, la9, la11;

        la1 = (miGrid[1] - 'A') * ((double) 180 / 18);
        la3 = (miGrid[3] - '0') * ((double) 180 / 18 / 10);
        la5 = (miGrid[5] - 'A') * ((double) 180 / 18 / 10 / 24);
        la7 = (miGrid[7] - '0') * ((double) 180 / 18 / 10 / 24 / 10);
        la9 = (miGrid[9] - 'A') * ((double) 180 / 18 / 10 / 24 / 10 / 24);
        la11 = (miGrid[11] - '0') * ((double) 180 / 18 / 10 / 24 / 10 / 24 / 10);
        miLatitud = (la1 + la3 + la5 + la7 + la9 + la11) - 90;
        miLatitud = miLatitud + (((double) 180 / 18 / 10 / 24 / 10 / 24 / 10) / 2);//Se divide entre 2 para centrar a la cuadricula final
        this.latitud = miLatitud;
    }
}
