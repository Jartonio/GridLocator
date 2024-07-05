package com.example.gridlocator;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button bt_ir;
    private Button bt_buscar;
    private ImageView iv_compass_image;
    private TextView tv_grid_destino;
    private TextView tv_grados_brujula;
    private TextView tv_coordenadas_gps;
    private TextView tv_altitud_gps;
    private TextView tv_mi_grid;
    private TextView tv_coordenadas_destino;
    private TextView tv_azimut_destino;
    private TextView tv_distancia_destino;

    private Brujula brujula;

    //private GPS gps;
    private GPS2 gps2;

    private GridLocator miGridLocator;

    private final String TAG = "Log.GridLocator";

    private Handler handlerGPS, handlerBrujula;

    private boolean isAppInForeground = false;

    private boolean buscando = false;

    private double gradosAzimut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        gps2 = new GPS2(this);

        // Verificar el estado del GPS y los permisos después de la creación de GPS2
        if (gps2.isPermissionDenied()) {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
        } else if (!gps2.isGPSEnabled()) {
            Toast.makeText(this, "GPS no está activado", Toast.LENGTH_SHORT).show();
        }


        bt_ir = findViewById(R.id.bt_buscar);
        bt_buscar = findViewById(R.id.bt_buscar);
        tv_grid_destino = findViewById(R.id.tv_grid_destino);
        tv_grados_brujula = findViewById(R.id.tv_grados_brujula);
        iv_compass_image = findViewById(R.id.iv_compass_image);
        tv_coordenadas_gps = findViewById(R.id.tv_coordenadas_gps);
        tv_altitud_gps = findViewById(R.id.tv_altitud_gps);
        tv_mi_grid = findViewById(R.id.tv_mi_grid);
        tv_azimut_destino = findViewById(R.id.tv_azimut_destino);
        tv_coordenadas_destino = findViewById(R.id.tv_coordenadas_destino);
        tv_distancia_destino = findViewById(R.id.tv_distancia_destino);

        brujula = new Brujula(this);
        //gps = new GPS(this);
        miGridLocator = new GridLocator();

        handlerGPS = new Handler(Looper.getMainLooper());
        handlerBrujula = new Handler(Looper.getMainLooper());

        if (!brujula.brujulaPresente()) {
            Log.d(TAG, "ERROR, este terminal no tiene la brujula disponible.");
        }

        bt_ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buscando) {
                    buscando = false;
                    bt_buscar.setText("Buscar");
                    tv_grid_destino.setEnabled(true);
                } else {
                    buscando = true;
                    bt_buscar.setText("Parar");
                    tv_grid_destino.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (brujula.brujulaPresente()) {
            brujula.start();
            handlerBrujula.post(runnableActualizarBrujula);
        }
        isAppInForeground = true;
        //gps.startListening();
        handlerGPS.post(runnableActualizarGPS);
        if (gps2 != null) {
            gps2.startLocationUpdates();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (brujula.brujulaPresente()) {
            brujula.stop();
            handlerBrujula.removeCallbacks(runnableActualizarBrujula);
        }
        isAppInForeground = false;
        //gps.stopListening();
        handlerGPS.removeCallbacks(runnableActualizarGPS);
        if (gps2 != null) {
            gps2.stopLocationUpdates();
        }
    }


    private final Runnable runnableActualizarGPS = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                //Location location = gps.getCurrentLocation();
                //if (location != null) {
                rellenarDatos();
                // }
            }
            handlerGPS.postDelayed(this, 1500); // Actualizar msx1000 cada 2,5 segundos
        }
    };

    private final Runnable runnableActualizarBrujula = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                if (buscando) {
                    //double declinacionMagnetica = GeoUtilidades.calcularDeclinacionMagnetica(gps.getCurrentLocation().getLatitude(),
                    //       gps.getCurrentLocation().getLongitude(), (int) gps.getCurrentLocation().getAltitude());
                    double declinacionMagnetica = GeoUtilidades.calcularDeclinacionMagnetica(gps2.getLatitud(), gps2.getLongitud(), (int) gps2.getLatitud());

                    double gradosBrujula = brujula.getGrados() + declinacionMagnetica;

                    gradosBrujula = (Math.round(gradosBrujula));
                    String textoFormateado = toString().valueOf((int) gradosBrujula);
                    textoFormateado = textoFormateado.replace("-", "");
                    tv_grados_brujula.setText(textoFormateado + "º");

                    //Se rota la imagen para que apunte al destino.
                    float degrees = brujula.getGrados();
                    iv_compass_image.setRotation((float) gradosAzimut - degrees);
                }
            }
            handlerBrujula.postDelayed(this, 500); // Actualizar cada 5000ms (5 segundos)
        }
    };

    private void rellenarDatos() {

        //double altitudGPS = gps.getCurrentLocation().getAltitude();
        double altitudGPS = gps2.getAltitud();
        //double latitudGPS = gps.getCurrentLocation().getLatitude();
        double latitudGPS = gps2.getLatitud();
        //double longuitudGPS = gps.getCurrentLocation().getLongitude();
        double longuitudGPS = gps2.getLongitud();
        //double precisionGPS = gps.getCurrentLocation().getAccuracy();
        double precisionGPS = gps2.getPrecision();

        miGridLocator.setLatitudLongitud(latitudGPS, longuitudGPS);
        String miGrid = miGridLocator.getGridLocator();

        if (gps2.getAltitud() == 0.0 && gps2.getLongitud() == 0.0 && gps2.getPrecision() == 0.0 && gps2.getAltitud() == 0.0) {
            tv_coordenadas_gps.setText("Obteniendo coordenadas del GPS.");
            tv_altitud_gps.setText("Por favor, espere.");
            tv_mi_grid.setText("");
        } else {
            tv_coordenadas_gps.setText(GeoUtilidades.formatearCoordenadas(7, latitudGPS, longuitudGPS));
            tv_altitud_gps.setText("Altitud: " + (int) altitudGPS + " m.    -    Precisión: " + (int) precisionGPS + " m.");
            tv_mi_grid.setText(miGrid);
        }

        if (buscando) {

            miGridLocator.setGridLocator(tv_grid_destino.getText().toString());

            double latitudDestino = miGridLocator.getLatitud();
            double longitudDestino = miGridLocator.getLongitud();

            tv_coordenadas_destino.setText(GeoUtilidades.formatearCoordenadas(7, latitudDestino, longitudDestino));

            double distanciaDestino = Math.round(GeoUtilidades.calcularDistancia(latitudGPS, longuitudGPS, latitudDestino, longitudDestino));

            if (distanciaDestino < 1000) {
                tv_distancia_destino.setText((int) distanciaDestino + " m.");
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
                tv_distancia_destino.setText(textoFormateado + " Km.");
            }
            gradosAzimut = Math.round(GeoUtilidades.calcularAzimut(latitudGPS, longuitudGPS, latitudDestino, longitudDestino));
            tv_azimut_destino.setText((int) gradosAzimut + "º");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos concedidos, reiniciar GPS2 para comenzar a obtener la ubicación
                gps2 = new GPS2(this);
            } else {
                // Permisos denegados, mostrar un mensaje al usuario
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gps2 != null) {
            gps2.stopLocationUpdates();
        }
    }


}
