package com.example.gridlocator;

import java.text.DecimalFormat;

public class CalcularDistanciaEntreCoordenadas {

    // Función para calcular la distancia en metros entre dos coordenadas en grados decimales
    public static double distancia(double latitud1, double longitud1, double latitud2, double longitud2) {

        double radioTierra = 6371.0; // Radio de la Tierra en kilómetros

        // Convertir las coordenadas de grados a radianes
        double latitud1Rad = Math.toRadians(latitud1);
        double longitud1Rad = Math.toRadians(longitud1);
        double latitud2Rad = Math.toRadians(latitud2);
        double longitud2Rad = Math.toRadians(longitud2);

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
