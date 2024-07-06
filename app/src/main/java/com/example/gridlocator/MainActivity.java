package com.example.gridlocator;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button bt_buscar;
    private ImageView iv_compass_image;
    private EditText tv_grid_destino;
    private TextView tv_grados_brujula;
    private TextView tv_coordenadas_gps;
    private TextView tv_altitud_gps;
    private TextView tv_mi_grid;
    private TextView tv_coordenadas_destino;
    private TextView tv_azimut_destino;
    private TextView tv_distancia_destino;

    private Brujula brujula;

    private GPS gps;

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
        miGridLocator = new GridLocator();

        tv_grid_destino.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });


        handlerGPS = new Handler(Looper.getMainLooper());
        handlerBrujula = new Handler(Looper.getMainLooper());

        bt_buscar.setEnabled(false);
        gps = new GPS(this);

        // Verificar el estado del GPS y los permisos después de la creación de GPS2
        if (gps.isPermissionDenied()) {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
        } else if (!gps.isGPSEnabled()) {
            Toast.makeText(this, "GPS no está activado", Toast.LENGTH_SHORT).show();
        }


        if (!brujula.brujulaPresente()) {
            Log.d(TAG, "ERROR, este terminal no tiene la brujula disponible.");
        }

        bt_buscar.setOnClickListener(new View.OnClickListener() {
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


        tv_grid_destino.addTextChangedListener(new TextWatcher() {
            boolean[] isTextModifiedInternally = {false}; // Flag to track internal modifications
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not required for this implementation
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not required for this implementation
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!isTextModifiedInternally[0]) {
                    isTextModifiedInternally[0] = true;
                    tv_grid_destino.setText(s.toString().toUpperCase());
                    tv_grid_destino.append("");
                    isTextModifiedInternally[0] = false;
                    tv_grid_destino.setSelection(tv_grid_destino.getText().length());
                    if (miGridLocator.gridValido(tv_grid_destino.getText().toString())) {
                        bt_buscar.setEnabled(true);
                    }else{
                        bt_buscar.setEnabled(false);
                    }
                }
            }
        });
    }


    private final Runnable runnableActualizarGPS = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                rellenarDatos();
            }
            handlerGPS.postDelayed(this, 2500); // Actualizar msx1000 cada 2,5 segundos
        }
    };

    private final Runnable runnableActualizarBrujula = new Runnable() {
        @Override
        public void run() {
            if (isAppInForeground) {
                if (buscando) {
                    double declinacionMagnetica = GeoUtilidades.calcularDeclinacionMagnetica(gps.getLatitud(), gps.getLongitud(), (int) gps.getLatitud());
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
        double altitudGPS = gps.getAltitud();
        double latitudGPS = gps.getLatitud();
        double longuitudGPS = gps.getLongitud();
        double precisionGPS = gps.getPrecision();

        miGridLocator.setLatitudLongitud(latitudGPS, longuitudGPS);
        String miGrid = miGridLocator.getGridLocator();

        if ((gps.getLatitud() == 0.0 && gps.getLongitud() == 0.0 && gps.getPrecision() == 0.0 && gps.getAltitud() == 0.0)) {//Primer pase al iniciar el programa
            tv_coordenadas_gps.setText("Obteniendo coordenadas del GPS.");
            tv_altitud_gps.setText("Iniciando el programa");
            tv_mi_grid.setText("Por favor, espere.");
        } else {

            tv_coordenadas_gps.setText(GeoUtilidades.formatearCoordenadas(7, latitudGPS, longuitudGPS));
            tv_altitud_gps.setText("Altitud: " + (int) altitudGPS + " m.    -    Precisión: " + (int) precisionGPS + " m.");
            tv_mi_grid.setText(miGrid);

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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos concedidos, reiniciar GPS2 para comenzar a obtener la ubicación
                gps = new GPS(this);
            } else {
                // Permisos denegados, mostrar un mensaje al usuario
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gps != null) {
            gps.stopLocationUpdates();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (brujula.brujulaPresente()) {
            brujula.start();
            handlerBrujula.post(runnableActualizarBrujula);
        }
        isAppInForeground = true;
        handlerGPS.post(runnableActualizarGPS);
        if (gps != null) {
            gps.startLocationUpdates();
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
        handlerGPS.removeCallbacks(runnableActualizarGPS);
        if (gps != null) {
            gps.stopLocationUpdates();
        }
    }

}
