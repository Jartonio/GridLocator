package com.example.gridlocator;

public class CalcularCoordenadasDesdeGrid {

    public static double longitud(String grid){

        char[] miGrid = grid.toCharArray();
        double lo0, lo2, lo4, lo6, lo8;


        double longitud;

        lo0 = (miGrid[0] - 'A') * 20;
        lo2 = (miGrid[2] - '0') * 2;
        lo4 = (miGrid[4] - 'A') * 0.083333;
        lo6 = (miGrid[6] - '0') * 0.0083333;
        lo8 = (miGrid[8] - 'A') * 0.000347221;
        //lo10 = (miGrid[10] - '0') * 0.0000347221;

        longitud = (lo0 + lo2 + lo4 + lo6 + lo8) - 180;

        return  Double.parseDouble(String.format("%.6f", longitud));
        //return longitud;
    }

    public static double latitud(String grid) {

        char[] miGrid = grid.toCharArray();
        double la1, la3, la5, la7, la9;
        double latitud;

        la1 = (miGrid[1] - 'A') * 10;
        la3 = (miGrid[3] - '0') * 1;
        la5 = (miGrid[5] - 'A') * 0.0416665;
        la7 = (miGrid[7] - '0') * 0.00416665;
        la9 = (miGrid[9] - 'A') * 0.00017361;
        //la11 = (miGrid[11] - '0') * 0.000017361;
        latitud = (la1 + la3 + la5 + la7 + la9) - 90;

        return  Double.parseDouble(String.format("%.6f",latitud));
        //return (latitud);
    }
}
