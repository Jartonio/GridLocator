package com.example.gridlocator;

import android.util.Log;

public class CalcularGridCuadriculas {


    public static String grid(double latitudInicial, double longitudInicial) {

        char[] grid = new char[10];

        double numeroGrid;

        double cuadriculasTiene1Grado;

        double cuadriculasMiLongitud = 0;
        double cuadriculasMiLatitud=0;
        double cuadrosNivel=0;
        double restoCuadros=0;

        latitudInicial=latitudInicial+90;
        longitudInicial=longitudInicial+180;

        cuadriculasTiene1Grado=2880;//(18*10*24*10*24)/360  //son las cuadriculas que tiene un grado
        cuadriculasMiLongitud = longitudInicial * cuadriculasTiene1Grado; //son las cuadriculas que ocula la longitud


        //Nivel 1 longitud
        cuadrosNivel=57000; //10*24*10*24
        numeroGrid=cuadriculasMiLongitud/cuadrosNivel;
        restoCuadros=cuadriculasMiLongitud%cuadrosNivel;
        grid[0] = (char) ('A' + (int)numeroGrid);


        //Nivel 2 longitud
        cuadrosNivel=5700; //24*10*24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLongitud%cuadrosNivel;
        grid[2] = (char) ('0' + (int)numeroGrid);

        //Nivel 3 longitud
        cuadrosNivel=240; //10*24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLongitud%cuadrosNivel;
        grid[4] = (char) ('A' + (int)numeroGrid);

        //Nivel 5 longitud
        cuadrosNivel=24; //24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLongitud%cuadrosNivel;
        grid[6] = (char) ('0' + (int)numeroGrid);

        //nivel 6 longitud
        grid[8] = (char) ('A' + (int)restoCuadros);



        cuadriculasTiene1Grado =5790;//5760(18*10*24*10*24)/180  //son las cuadriculas que tiene un grado
        cuadriculasMiLatitud = latitudInicial * cuadriculasTiene1Grado;

        //Nivel 1 latitud
        cuadrosNivel=57000; //10*24*10*24
        numeroGrid=cuadriculasMiLatitud/cuadrosNivel;
        restoCuadros=cuadriculasMiLatitud%cuadrosNivel;
        grid[1] = (char) ('A' + (int)numeroGrid);


        //Nivel 2 longitud
        cuadrosNivel=5700; //24*10*24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLatitud%cuadrosNivel;
        grid[3] = (char) ('0' + (int)numeroGrid);

        //Nivel 3 longitud
        cuadrosNivel=240; //10*24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLatitud%cuadrosNivel;
        grid[5] = (char) ('A' + (int)numeroGrid);

        //Nivel 5 longitud
        cuadrosNivel=24; //24
        numeroGrid=restoCuadros/cuadrosNivel;
        restoCuadros=cuadriculasMiLatitud%cuadrosNivel;
        grid[7] = (char) ('0' + (int)numeroGrid);

        //nivel 6 longitud
        grid[9] = (char) ('A' + (int)restoCuadros);



        return String.valueOf(grid);

    }

}


