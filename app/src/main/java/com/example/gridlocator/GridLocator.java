package com.example.gridlocator;

import java.math.BigDecimal;

public class GridLocator {

    private double latitud, longuitud, desviacion;
    private BigDecimal latitudGrid,longuitudGrid;
    private String gridLocator;

    public GridLocator() {
    }

    public String getGridLocator() {
        return gridLocator;
    }

    public void setLatitudLonguitud(double latitud, double longuitud) {
        this.latitud = latitud;
        this.longuitud = longuitud;
        gridLocator = calcularGrid(latitud, longuitud);
        desviacion = calcularMetrosDesviacion();
    }


    public double getDesviacion() {
        return desviacion;
    }
    public String getCoodenadasGrid(){
        return latitudGrid+", "+longuitudGrid;
    }

    private String calcularGrid(double latitudInicial, double longitudInicial) {

        //Este metodo calcula el Grid Locator de Maidenhead a partir de una latitud y una longuitud.
        //Entrega un Grid en formato: AA00AA00AA00 (18x10x24x10x24x10).

        double longitud = longitudInicial + 180;
        double latitud = latitudInicial + 90;

        double grados, totalCuadros, ultimo, diferenciaCuadros;
        int cuadrosAnteriores, numeroGrid, divisiones;

        char[] grid = new char[12];

        // longitud cuadricula 1
        divisiones=18;
        grados = 360f/divisiones;
        totalCuadros = longitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[0] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 2
        divisiones = 10;
        grados = grados/divisiones;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[2] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 3
        divisiones = 24;
        grados = grados/ divisiones;

        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[4] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 4
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[6] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 5
        divisiones = 24;
        grados = grados / divisiones;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[8] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //longitud cuadricula 6
        divisiones = 10;
        grados = grados / divisiones;
        totalCuadros = longitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[10] = (char) ('0' + numeroGrid);


        // latitud cadricula 1
        divisiones=18;
        grados = 180f/divisiones;
        totalCuadros = latitud / grados;
        numeroGrid = (int) totalCuadros;
        grid[1] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 2
        divisiones=10;
        grados = grados/divisiones;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[3] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 3
        divisiones=24;
        grados = grados/divisiones;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[5] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 4
        divisiones=10;
        grados = grados / divisiones;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[7] = (char) ('0' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 5
        divisiones=24;
        grados = grados / divisiones;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[9] = (char) ('A' + numeroGrid);
        ultimo = totalCuadros;

        //latitud cuadricula 6
        divisiones=10;
        grados = grados / divisiones;
        totalCuadros = latitud / grados;
        cuadrosAnteriores = ((int) (ultimo)) * divisiones;
        diferenciaCuadros = totalCuadros - cuadrosAnteriores;
        numeroGrid = (int) diferenciaCuadros;
        grid[11] = (char) ('0' + numeroGrid);

        return String.valueOf(grid);
    }


    private BigDecimal obtenerLongitudGrid() {

        //Este metodo devuelve la longitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double longitud, lo0, lo2, lo4, lo6, lo8,lo10;

        lo0 = (miGrid[0] - 'A') * ((double) 360 /18);
        lo2 = (miGrid[2] - '0') * ((double) 360 /18/10);
        lo4 = (miGrid[4] - 'A') * ((double) 360 /18/10/24);
        lo6 = (miGrid[6] - '0') * ((double) 360/18/10/24/10);
        lo8 = (miGrid[8] - 'A') * ((double) 360/18/10/24/10/24);
        lo10 =(miGrid[10] - '0') * ((double) 360/18/10/24/10/24/10);

        longitud = (lo0 + lo2 + lo4 + lo6 + lo8+lo10) - 180;
        longitud=longitud + (((double) 360/18/10/24/10/24/10)/ 2);
        longuitudGrid=BigDecimal.valueOf(longitud);
        return BigDecimal.valueOf(longitud);

    }

    private BigDecimal obtenerLatitudGrid() {

        //Este metodo devuelve la latitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double latitud, la1, la3, la5, la7, la9,la11;

        la1 = (miGrid[1] - 'A') * ((double) 180/18);
        la3 = (miGrid[3] - '0') * ((double) 180/18/10);
        la5 = (miGrid[5] - 'A') * ((double) 180/18/10/24);
        la7 = (miGrid[7] - '0') * ((double) 180/18/10/24/10);
        la9 = (miGrid[9] - 'A') * ((double) 180/18/10/24/10/24);
        la11 = (miGrid[11] - '0') * ((double) 180/18/10/24/10/24/10);
        latitud = (la1 + la3 + la5 + la7 + la9+la11) - 90;
        latitud=latitud + (((double) 180/18/10/24/10/24/10) / 2);
        latitudGrid=BigDecimal.valueOf(latitud);
        return BigDecimal.valueOf(latitud);
    }

    private double calcularMetrosDesviacion() {

        double radioTierra = 6371.0; // Radio de la Tierra en kilómetros

        // Convertir las coordenadas de grados a radianes
        double latitud1Rad = Math.toRadians(latitud);
        double longitud1Rad = Math.toRadians(longuitud);
        double latitud2Rad = Math.toRadians(obtenerLatitudGrid().doubleValue());
        double longitud2Rad = Math.toRadians(obtenerLongitudGrid().doubleValue());

        // Calcular la diferencia entre las longitudes y latitudes
        double diferenciaLatitud = latitud2Rad - latitud1Rad;
        double diferenciaLongitud = longitud2Rad - longitud1Rad;

        // Calcular la distancia utilizando la fórmula de Haversine
        double a = Math.pow(Math.sin(diferenciaLatitud / 2), 2) + Math.cos(latitud1Rad) * Math.cos(latitud2Rad) * Math.pow(Math.sin(diferenciaLongitud / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia en metros
        double distanciaEnKilometros = radioTierra * c;

        return distanciaEnKilometros * 1000; // Convertir a metros
    }


}
