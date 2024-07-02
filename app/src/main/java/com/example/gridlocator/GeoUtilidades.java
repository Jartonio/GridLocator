package com.example.gridlocator;

import android.hardware.GeomagneticField;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeoUtilidades {

    public static double calcularRumbo(double startLat, double startLng, double endLat, double endLng) {
        // Método para calcular el rumbo (azimut) entre dos puntos.
        double startLatRad = Math.toRadians(startLat);
        double startLngRad = Math.toRadians(startLng);
        double endLatRad = Math.toRadians(endLat);
        double endLngRad = Math.toRadians(endLng);
        double dLng = endLngRad - startLngRad;
        double y = Math.sin(dLng) * Math.cos(endLatRad);
        double x = Math.cos(startLatRad) * Math.sin(endLatRad) - Math.sin(startLatRad) * Math.cos(endLatRad) * Math.cos(dLng);
        double bearingRad = Math.atan2(y, x);
        double bearingDeg = Math.toDegrees(bearingRad);
        // Normalizar el ángulo para que esté entre 0 y 360 grados
        return  ((bearingDeg + 360) % 360);
    }

    public static float calcularDistancia(double startLat, double startLng, double endLat, double endLng) {
        // Método para calcular la distancia entre dos puntos usando la fórmula del Haversine.
        final int R = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLng - startLng);
        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLng / 2) * Math.sin(dLng / 2) * Math.cos(startLatRad) * Math.cos(endLatRad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float)(R * c * 1000); // Distancia en metros
    }

    public static String formatearCoordenadas(int decimales,double miLatitud, double miLonguitud) {
        //Metodo para dar formato y limitar los decimales de las coordenadas.
        String miLatitudString,miLonguitudString;
        BigDecimal miBigDecimal;
        miBigDecimal= BigDecimal.valueOf(miLatitud);
        BigDecimal miBidRound =  miBigDecimal.setScale(decimales, RoundingMode.HALF_UP);
        miLatitudString=miBidRound.toString();
        miBigDecimal= BigDecimal.valueOf(miLonguitud);
        miBidRound =  miBigDecimal.setScale(decimales, RoundingMode.HALF_UP);
        miLonguitudString=miBidRound.toString();
        return (miLatitudString+", "+miLonguitudString);
    }

    public static double calcularDerivacion(double lat, double lon){
        long altura=0;
        GeomagneticField miGeo= new GeomagneticField((float)lat,(float)lon,altura,System.currentTimeMillis());
        return miGeo.getDeclination();
    }
}

