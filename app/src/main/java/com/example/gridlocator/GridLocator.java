package com.example.gridlocator;

public class GridLocator {

    private double latitud, longuitud, desviacion;

    private String gridLocator;


    public GridLocator() {
    }

    public String getGridLocator() {
        return gridLocator;
    }

    public void setLatitudLonguitud(double latitud,double longuitud) {
        this.latitud = latitud;
        this.longuitud = longuitud;
        gridLocator = calcularGridLocator(latitud, longuitud);
        desviacion = metrosDesviacion();
    }


    public double getDesviacion() {
        return desviacion;
    }

    private String calcularGridLocator(double latitudInicial, double longitudInicial) {

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

        return String.valueOf(grid);
    }

    private double longitudGrid() {

        //Este metodo devuelve la longitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double longitud, lo0, lo2, lo4, lo6, lo8;

        lo0 = (miGrid[0] - 'A') * 20;
        lo2 = (miGrid[2] - '0') * 2;
        lo4 = (miGrid[4] - 'A') * 0.08333333;//0.083333;
        lo6 = (miGrid[6] - '0') * 0.00833333;//0.0083333;
        lo8 = (miGrid[8] - 'A') * 0.00034722;//0.000347221;
        //lo10 = (miGrid[10] - '0') * 0.0000347221; --  Activar si el grid tiene 6 de precision

        longitud = (lo0 + lo2 + lo4 + lo6 + lo8) - 180;

        //return  Double.parseDouble(String.format("%.6f", longitud));
        return longitud + (0.0003472222 / 2);
    }

    private double latitudGrid() {

        //Este metodo devuelve la latitud a partir de un grid.

        char[] miGrid = gridLocator.toCharArray();
        double latitud, la1, la3, la5, la7, la9;

        la1 = (miGrid[1] - 'A') * 10;
        la3 = (miGrid[3] - '0') * 1;
        la5 = (miGrid[5] - 'A') * 0.04166666;//0.0416665;
        la7 = (miGrid[7] - '0') * 0.00416666;//0.00416665;
        la9 = (miGrid[9] - 'A') * 0.00017361;//0.00017361;
        //la11 = (miGrid[11] - '0') * 0.000017361;  --  Activar si el grid tiene 6 de precision

        latitud = (la1 + la3 + la5 + la7 + la9) - 90;

        //return  Double.parseDouble(String.format("%.6f",latitud));
        return latitud + (0.0001736111 / 2);
    }

    private double metrosDesviacion() {

        double radioTierra = 6371.0; // Radio de la Tierra en kilómetros

        // Convertir las coordenadas de grados a radianes
        double latitud1Rad = Math.toRadians(latitud);
        double longitud1Rad = Math.toRadians(longuitud);
        double latitud2Rad = Math.toRadians(latitudGrid());
        double longitud2Rad = Math.toRadians(longitudGrid());

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
