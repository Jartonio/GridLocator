package com.example.gridlocator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatearDecimales {
    public static double formatear(double numero) {

        // Creamos un objeto DecimalFormat con el formato deseado
        DecimalFormat df = new DecimalFormat("#.000000");
        // Convertimos el número double a String usando el formato
        String str = df.format(numero);
        // Convertimos el String de nuevo a double y lo devolvemos

        return Double.parseDouble(str);

    }


}
