package com.example.gridlocator;

public class GridLocator {

    private double latitud, longuitud, desviacion;
    private String gridLocator;

    public GridLocator() {
    }

    public String getGridLocator() {
        return gridLocator;
    }

    public void setLatitudLonguitud(double latitud, double longuitud) {
        this.latitud = latitud;
        this.longuitud = longuitud;
        gridLocator = calcularGridLocator(latitud, longuitud);
        desviacion = calcularMetrosDesviacion();
    }

    public double getDesviacion() {
        return desviacion;
    }

    private String calcularGridLocator(double latitudInicial, double longitudInicial) {

        //Este metodo calcula el Grid Locator de Maidenhead a partir de una latitud y una longuitud.
        //Entrega un Grid en formato: AA00AA00AA (18x10x24x10x24).

        double longitud = longitudInicial + 180;
        double latitud = latitudInicial + 90;

        double grados, totalCuadros, ultimo, diferenciaCuadros;
        int cuadrosAnteriores, numeroGrid, divisiones;

        char[] grid = new char[10];

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

        return String.valueOf(grid);
    }

    private double obtenerLongitudGrid() {

        //Este metodo devuelve la longitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double longitud, lo0, lo2, lo4, lo6, lo8;

        lo0 = (miGrid[0] - 'A') * ((double) 360 /18);
        lo2 = (miGrid[2] - '0') * ((double) 360 /18/10);
        lo4 = (miGrid[4] - 'A') * ((double) 360 /18/10/24);
        lo6 = (miGrid[6] - '0') * ((double) 360/18/10/24/10);
        lo8 = (miGrid[8] - 'A') * ((double) 360/18/10/24/10/24);
        longitud = (lo0 + lo2 + lo4 + lo6 + lo8) - 180;

        return longitud + (((double) 360/18/10/24/10/24)/ 2);
    }

    private double obtenerLatitudGrid() {

        //Este metodo devuelve la latitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double latitud, la1, la3, la5, la7, la9;

        la1 = (miGrid[1] - 'A') * ((double) 180/18);
        la3 = (miGrid[3] - '0') * ((double) 180/18/10);
        la5 = (miGrid[5] - 'A') * ((double) 180/18/10/24);
        la7 = (miGrid[7] - '0') * ((double) 180/18/10/24/10);
        la9 = (miGrid[9] - 'A') * ((double) 180/18/10/24/10/24);
        latitud = (la1 + la3 + la5 + la7 + la9) - 90;

        return latitud + (((double) 180/18/10/24/10/24) / 2);
    }

    private double calcularMetrosDesviacion() {

        double radioTierra = 6371.0; // Radio de la Tierra en kilómetros

        // Convertir las coordenadas de grados a radianes
        double latitud1Rad = Math.toRadians(latitud);
        double longitud1Rad = Math.toRadians(longuitud);
        double latitud2Rad = Math.toRadians(obtenerLatitudGrid());
        double longitud2Rad = Math.toRadians(obtenerLongitudGrid());

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
