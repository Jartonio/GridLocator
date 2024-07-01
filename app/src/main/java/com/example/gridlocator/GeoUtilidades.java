package com.example.gridlocator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeoUtilidades {


    // Método para calcular el rumbo (azimut) entre dos puntos
    public int calculateRumbo(double startLat, double startLng, double endLat, double endLng) {
        double startLatRad = Math.toRadians(startLat);
        double startLngRad = Math.toRadians(startLng);
        double endLatRad = Math.toRadians(endLat);
        double endLngRad = Math.toRadians(endLng);

        double dLng = endLngRad - startLngRad;
        double y = Math.sin(dLng) * Math.cos(endLatRad);
        double x = Math.cos(startLatRad) * Math.sin(endLatRad) -
                Math.sin(startLatRad) * Math.cos(endLatRad) * Math.cos(dLng);
        double bearingRad = Math.atan2(y, x);
        double bearingDeg = Math.toDegrees(bearingRad);

        // Normalizar el ángulo para que esté entre 0 y 360 grados
        return (int) ((bearingDeg + 360) % 360);
    }


    // Método para calcular la distancia entre dos puntos usando la fórmula del Haversine
    public static float calculateDistancia(double startLat, double startLng, double endLat, double endLng) {
        final int R = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLng - startLng);
        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLng / 2) * Math.sin(dLng / 2) * Math.cos(startLatRad) * Math.cos(endLatRad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (R * c * 1000); // Distancia en metros
    }

    public static String formatearCoordenadas(double miLatitud, double miLonguitud) {

        String miLatitudString,miLonguitudString;
        BigDecimal miBigDecimal;

        miBigDecimal= BigDecimal.valueOf(miLatitud);
        BigDecimal miBidRound =  miBigDecimal.setScale(8, RoundingMode.HALF_UP);
        miLatitudString=miBidRound.toString();

        miBigDecimal= BigDecimal.valueOf(miLonguitud);
        miBidRound =  miBigDecimal.setScale(8, RoundingMode.HALF_UP);
        miLonguitudString=miBidRound.toString();

        return (miLatitudString+", "+miLonguitudString);
    }
}

