package com.example.gridlocator;

import android.content.pm.ActivityInfo;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Brujula brujula;
    private GPS gps;

    private final String TAG = "Log.GridLocator";

    private Handler handlerGPS, handlerGrados;

    private boolean isAppInForeground = false;

    private boolean buscando = false;
    private double gradosAzimut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btIr = findViewById(R.id.bt_buscar);

        brujula = new Brujula(this);
        gps = new GPS(this);

        handlerGPS = new Handler(Looper.getMainLooper());
        handlerGrados = new Handler(Looper.getMainLooper());

        if (!brujula.brujulaPresente()) {
            Log.d(TAG, "ERROR, este terminal no tiene la brujula disponible.");
        }


        //double longitudInicial, latitudInicial, precision, latitudGridLocator, longuitudGridLocator;
        //double latitudObservador, longuitudObservador, azimut, rumboFinal, declinacion;
        //int distancia;

        //String gridLocator;

        btIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = findViewById(R.id.bt_buscar);
                TextView textView = findViewById(R.id.tv_grid_destino);
                if (buscando) {
                    buscando = false;
                    button.setText("Buscar");
                    textView.setEnabled(true);
                } else {
                    buscando = true;
                    button.setText("Parar");
                    textView.setEnabled(false);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (brujula.brujulaPresente()) {
        brujula.start();
        startUpdatingDegrees();
        //}
        isAppInForeground = true;
        gps.startListening();
        startUpdatingLocation();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //if (brujula.brujulaPresente()) {
        brujula.stop();
        stopUpdatingDegrees();
        // }
        isAppInForeground = false;
        gps.stopListening();
        stopUpdatingLocation();

    }

    private void startUpdatingLocation() {
        handlerGPS.post(updateLocationRunnable);
    }

    private void stopUpdatingLocation() {
        handlerGPS.removeCallbacks(updateLocationRunnable);
    }


    private void startUpdatingDegrees() {
        handlerGrados.post(updateDegreesRunnable);
    }

    private void stopUpdatingDegrees() {
        handlerGrados.removeCallbacks(updateDegreesRunnable);

    }

    private final Runnable updateLocationRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                Location location = gps.getCurrentLocation();
                if (location != null) {
                    Log.d(TAG, "Coordenadas GPS: " + location.getLatitude() + ", " + location.getLongitude());
                    rellenarDatos();
                } else {
                    Log.d(TAG, "Coordenadas GPS no disponibles.");
                }
            }
            handlerGPS.postDelayed(this, 1500); // Actualizar msx1000 cada 2,5 segundos
        }
    };

    private final Runnable updateDegreesRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                if (brujula.brujulaPresente()) {
                    Log.d(TAG, "Brujula: " + String.format("%.2f°", brujula.getGrados()));
                } else {
                    Log.d(TAG, "Este dispositivo no tiene brújula.");
                }
                if (buscando) {
                    double declinacion = GeoUtilidades.calcularDeclinacionMagnetica(gps.getCurrentLocation().getLatitude(), gps.getCurrentLocation().getLongitude(), (int) gps.getCurrentLocation().getAltitude());
                    double gradosBrujula = brujula.getGrados() + declinacion;

                    gradosBrujula = (Math.round(gradosBrujula));
                    String textoFormateado = toString().valueOf((int) gradosBrujula);
                    textoFormateado = textoFormateado.replace("-", "");

                    TextView tvGradosBrujula = findViewById(R.id.tv_grados_brujula);
                    tvGradosBrujula.setText(textoFormateado + "º");

                    ImageView compassImage = findViewById(R.id.compass_image);
                    float degrees = brujula.getGrados();

                    compassImage.setRotation((float) gradosAzimut - degrees);

                }
            }
            handlerGrados.postDelayed(this, 500); // Actualizar cada 5000ms (5 segundos)
        }
    };

    private void rellenarDatos() {

        GridLocator miGridLocator = new GridLocator();

        TextView tvCoordenadasGPS = findViewById(R.id.tv_coordenadas_gps);
        TextView tvAltitudGPS = findViewById(R.id.tv_altitud_gps);
        TextView tvMiGrid = findViewById(R.id.tv_mi_grid);

        double altitudGPS = gps.getCurrentLocation().getAltitude();
        double latitudGPS = gps.getCurrentLocation().getLatitude();
        double longuitudGPS = gps.getCurrentLocation().getLongitude();
        double precisionGPS = gps.getCurrentLocation().getAccuracy();

        miGridLocator.setLatitudLongitud(latitudGPS, longuitudGPS);
        String miGrid = miGridLocator.getGridLocator();

        tvCoordenadasGPS.setText(GeoUtilidades.formatearCoordenadas(7, latitudGPS, longuitudGPS));
        tvAltitudGPS.setText("Altitud: " + (int) altitudGPS + " m.    -    Precisión: " + (int) precisionGPS + " m.");
        tvMiGrid.setText(miGrid);

        if (buscando) {
            TextView tvGridDestino = findViewById(R.id.tv_grid_destino);

            miGridLocator.setGridLocator(tvGridDestino.getText().toString());

            Log.d(TAG, "grilocator: " + tvGridDestino.toString());
            double latitudDestino = miGridLocator.getLatitud();
            double longitudDestino = miGridLocator.getLongitud();

            TextView tvCoordenadasDestino = findViewById(R.id.tv_coordenadas_destino);
            tvCoordenadasDestino.setText(GeoUtilidades.formatearCoordenadas(7, latitudDestino, longitudDestino));

            TextView tvDistanciaDestino = findViewById(R.id.tv_distancia_destino);
            double distanciaDestino = Math.round(GeoUtilidades.calcularDistancia(latitudGPS, longuitudGPS, latitudDestino, longitudDestino));

            if (distanciaDestino < 1000) {
                tvDistanciaDestino.setText(toString().valueOf((int) distanciaDestino + " m."));
            } else {
                distanciaDestino = distanciaDestino / 1000;
                DecimalFormat miDF = new DecimalFormat("###,###.##");
                String textoFormateado = miDF.format(distanciaDestino);
                char caracter = textoFormateado.charAt(textoFormateado.length() - 3);
                if (caracter == '.') {
                    textoFormateado = textoFormateado.replace(".", "-");
                    textoFormateado = textoFormateado.replace(",", ".");
                    textoFormateado = textoFormateado.replace("-", ",");
                }
                tvDistanciaDestino.setText(textoFormateado + " Km.");
            }

            TextView tvAzimutDestino = findViewById(R.id.tv_azimut_destino);
            gradosAzimut = Math.round(GeoUtilidades.calcularAzimut(latitudGPS, longuitudGPS, latitudDestino, longitudDestino));

            tvAzimutDestino.setText(toString().valueOf((int) gradosAzimut) + "º");


        }


    }


}


/*
private void test{
        double sumaDistancias = 0;
        double menor1 = 0;
        double de1a2 = 0;
        double de2a3 = 0;
        double de3a4 = 0;
        double de4a5 = 0;
        double mayor5 = 0;
         double longitudInicial, latitudInicial, precision, latitudGridLocator, longuitudGridLocator;
        double latitudObservador, longuitudObservador, azimut, rumboFinal, declinacion;
        int distancia;

        String gridLocator;

        GridLocator miGridLocator = new GridLocator();

        int veces = 1;
        int erroresDeGrid = 0;

        for (int i = 1; i <= veces; i++) {
            latitudObservador = GenerarCoordenadasAleatorias.latitudAleatoria();
            longuitudObservador = GenerarCoordenadasAleatorias.longitudAleatoria();
            latitudInicial = GenerarCoordenadasAleatorias.latitudAleatoria();
            longitudInicial = GenerarCoordenadasAleatorias.longitudAleatoria();

            latitudObservador = 28.370448199705887;
            longuitudObservador = -16.84997473238369;
            latitudInicial = 28.274177430463826;
            longitudInicial = -16.629580431519543;

            miGridLocator.setLatitudLonguitud(latitudInicial, longitudInicial);

            gridLocator = miGridLocator.getGridLocator();

            if (!miGridLocator.gridValido(gridLocator)) {
                erroresDeGrid++;
            }

            miGridLocator.setGridLocator(gridLocator);

            latitudGridLocator = miGridLocator.getLatitud();
            longuitudGridLocator = miGridLocator.getLonguitud();

            precision = GeoUtilidades.calcularDistancia(latitudInicial, longitudInicial, latitudGridLocator, longuitudGridLocator);

            azimut = GeoUtilidades.calcularAzimut(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);

            distancia = (int) GeoUtilidades.calcularDistancia(latitudObservador, longuitudObservador, latitudGridLocator, longuitudGridLocator);
            declinacion = GeoUtilidades.calcularDeclinacionMagnetica(latitudObservador, longuitudObservador);
            rumboFinal = azimut + declinacion;

            Log.d(TAG, "Coordenadas del observador: " + latitudObservador + " " + longuitudObservador);
            Log.d(TAG, "Coordenadas iniciales: " + latitudInicial + " " + longitudInicial);
            Log.d(TAG, "Coordenadas obtenidas: " + latitudGridLocator + " " + longuitudGridLocator);
            Log.d(TAG, "Grid Locator: " + gridLocator);
            Log.d(TAG, "Distancia: " + distancia);
            Log.d(TAG, "Declinacion: " + declinacion);
            Log.d(TAG, "Azimut: " + azimut);
            Log.d(TAG, "Azimut final: " + rumboFinal);


            Log.d(TAG, "------------------------------------------");

            sumaDistancias = sumaDistancias + precision;
            if (precision < 1) {
                menor1++;
            } else {
                if (precision >= 1 && precision < 2) {
                    de1a2++;
                } else {
                    if (precision >= 2 && precision < 3) {
                        de2a3++;
                    } else {
                        if (precision >= 3 && precision < 4) {
                            de3a4++;
                        } else {
                            if (precision >= 4 && precision < 5) {
                                de4a5++;
                            } else {
                                mayor5++;
                            }
                        }
                    }
                }
            }

        }
        Log.d(TAG, "Media de error: " + (sumaDistancias / veces));
        Log.d(TAG, "Distancias menores de 1m:   " + menor1);
        Log.d(TAG, "Distancias entre 1m y 2m:  " + de1a2);
        Log.d(TAG, "Distancias entre 2m y 3m: " + de2a3);
        Log.d(TAG, "Distancias entre 3m y 4m: " + de3a4);
        Log.d(TAG, "Distancias entre 4m y 5m: " + de4a5);
        Log.d(TAG, "Distancias de mas de 5m:   " + mayor5);
        Log.d(TAG, "Errores de Grid: " + erroresDeGrid);
        Log.d(TAG, "Brujula: " + gradosBrujula);
    }*/
