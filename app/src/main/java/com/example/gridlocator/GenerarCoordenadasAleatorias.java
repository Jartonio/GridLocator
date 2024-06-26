package com.example.gridlocator;

import java.util.Random;

public class GenerarCoordenadasAleatorias {

    // Rango válido para latitud en grados decimales
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE = 90.0;

    // Rango válido para longitud en grados decimales
    private static final double MIN_LONGITUDE = -180.0;
    private static final double MAX_LONGITUDE = 180.0;

    public static double latitudAleatoria() {
        Random random = new Random();
        return MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * random.nextFloat();
    }

    public static double longitudAleatoria() {
        Random random = new Random();
        return   MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * random.nextFloat();

    }
}

