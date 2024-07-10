package com.example.gridlocator;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

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
    private Button bt_sos;

    private Brujula miBrujula;

    private GPS gps;

    private GridLocator miGridLocator;

    private final String TAG = "Log.GridLocator";

    private Handler handlerGPS;

    private boolean isAppInForeground = false;

    private boolean buscando = false;

    private double gradosAzimutDestino;

    private float currentAzimuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bt_buscar = findViewById(R.id.bt_buscar);
        bt_sos = findViewById(R.id.bt_sos);
        tv_grid_destino = findViewById(R.id.tv_grid_destino);
        tv_grados_brujula = findViewById(R.id.tv_grados_brujula);
        iv_compass_image = findViewById(R.id.iv_compass_image);
        tv_coordenadas_gps = findViewById(R.id.tv_coordenadas_gps);
        tv_altitud_gps = findViewById(R.id.tv_altitud_gps);
        tv_mi_grid = findViewById(R.id.tv_mi_grid);
        tv_azimut_destino = findViewById(R.id.tv_azimut_destino);
        tv_coordenadas_destino = findViewById(R.id.tv_coordenadas_destino);
        tv_distancia_destino = findViewById(R.id.tv_distancia_destino);
        tv_grid_destino.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});//Filtro para 12 caracteres máximo.
        miBrujula = new Brujula(this);
        miGridLocator = new GridLocator();

        handlerGPS = new Handler(Looper.getMainLooper());
        bt_buscar.setEnabled(false);
        bt_sos.setEnabled(false);
        bt_sos.setTextColor(Color.GRAY);
        bt_sos.setLetterSpacing(0.2f);
        tv_grid_destino.setLetterSpacing(0.2f);
        iv_compass_image.setImageResource(R.drawable.compass_calibration);
        tv_coordenadas_destino.setText("Por favor, calibre la brújula");
        tv_distancia_destino.setText (" duratante 10 segundos antes de empezar");
        tv_azimut_destino.setText("la busqueda.");

        tv_grados_brujula.setVisibility(View.INVISIBLE);

        gps = new GPS(this);

        // Verificar el estado del GPS y los permisos después de la creación de GPS2
        if (gps.isPermissionDenied()) {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
        } else if (!gps.isGPSEnabled()) {
            Toast.makeText(this, "GPS no está activado", Toast.LENGTH_SHORT).show();
        }

        bt_sos.setOnClickListener(view -> {
            // Crea un constructor de AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(miGridLocator.getGridLocator());
            builder.setMessage("Por favor, tramsnita las siguientes palabras con calma:\n\n\n" + miGridLocator.locucionGrid().toString());
            // Agrega los botones y sus manejadores de clics
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                // Acción al hacer clic en "Aceptar"
            });
            // Muestra el diálogo
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        });

        bt_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buscando) {
                    buscando = false;
                    bt_buscar.setText("Buscar");
                    tv_grid_destino.setEnabled(true);
                    miBrujula.stop();
                } else {
                    buscando = true;
                    bt_buscar.setText("Parar");
                    tv_grid_destino.setEnabled(false);
                    setupCompass();
                    miBrujula.start();
                    iv_compass_image.setImageResource(R.drawable.flecha_color);
                    tv_grados_brujula.setVisibility(View.VISIBLE);
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
            public void afterTextChanged(Editable s) {//Pasa los caracteres a mayúsculas y comprueba si es válido el Grid.
                if (!isTextModifiedInternally[0]) {
                    isTextModifiedInternally[0] = true;
                    tv_grid_destino.setText(s.toString().toUpperCase());
                    tv_grid_destino.append("");
                    isTextModifiedInternally[0] = false;
                    tv_grid_destino.setSelection(tv_grid_destino.getText().length());
                    if (miGridLocator.gridValido(tv_grid_destino.getText().toString())) {
                        bt_buscar.setEnabled(true);
                    } else {
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
            handlerGPS.postDelayed(this, 5000); // Actualizar msx1000 cada 2,5 segundos
        }
    };


    private void rellenarDatos() {
        double altitudGPS = gps.getAltitud();
        double latitudGPS = gps.getLatitud();
        double longuitudGPS = gps.getLongitud();
        double precisionGPS = gps.getPrecision();

        miGridLocator.setLatitudLongitud(latitudGPS, longuitudGPS);
        String miGrid = miGridLocator.getGridLocator();

        Locale currentLocale = Locale.getDefault();
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(currentLocale);

        if ((gps.getLatitud() == 0.0 && gps.getLongitud() == 0.0 && gps.getPrecision() == 0.0 && gps.getAltitud() == 0.0)) {//Primer pase al iniciar el programa
            tv_coordenadas_gps.setText("Obteniendo coordenadas del GPS.");
            tv_altitud_gps.setText("Iniciando el programa");
            tv_mi_grid.setText("Por favor, espere.");
        } else {
            bt_sos.setTextColor(Color.WHITE);
            bt_sos.setEnabled(true);
            tv_mi_grid.setLetterSpacing(0.2f);
            tv_grid_destino.setLetterSpacing(0.2f);
            decimalFormat.applyPattern("#,##0"); // Establecer el patrón deseado
            tv_coordenadas_gps.setText(GeoUtilidades.formatearCoordenadas(7, latitudGPS, longuitudGPS));
            tv_altitud_gps.setText("Altitud: " + decimalFormat.format(altitudGPS) + " m.   -    Precisión: " + decimalFormat.format(precisionGPS));
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
                    decimalFormat.applyPattern("#,##0.00"); // Establecer el patrón deseado
                    tv_distancia_destino.setText(decimalFormat.format(distanciaDestino) + " Km.");
                }
                gradosAzimutDestino = Math.round(GeoUtilidades.calcularAzimut(latitudGPS, longuitudGPS, latitudDestino, longitudDestino));
                tv_azimut_destino.setText((int) gradosAzimutDestino + "º");
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
        miBrujula.stop();
        if (gps != null) {
            gps.stopLocationUpdates();
        }
        miBrujula.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (buscando) {
            miBrujula.start();
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
        miBrujula.stop();
        isAppInForeground = false;
        handlerGPS.removeCallbacks(runnableActualizarGPS);
        if (gps != null) {
            gps.stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        miBrujula.stop();
    }

    private void setupCompass() {
        miBrujula = new Brujula(this);
        Brujula.CompassListener cl = getCompassListener();
        miBrujula.setListener(cl);
    }

    private void adjustArrow(float azimuth) {
        Animation an = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = azimuth;
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        iv_compass_image.startAnimation(an);
    }

    private Brujula.CompassListener getCompassListener() {
        return new Brujula.CompassListener() {
            @Override
            public void onNewAzimuth(final float azimuth) {
                // UI updates only in UI thread
                // https://stackoverflow.com/q/11140285/444966
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Se desvia la brujula para que apunte hacia donde hay que ir, no al norte.
                        double declinacionMagnetica = GeoUtilidades.calcularDeclinacionMagnetica(gps.getLatitud(), gps.getLongitud(), (long) gps.getAltitud());
                        double gradosBrujula = azimuth + declinacionMagnetica;
                        gradosBrujula = (int) gradosBrujula;// (Math.round(gradosBrujula));
                        String textoFormateado = toString().valueOf((int) gradosBrujula);
                        textoFormateado = textoFormateado.replace("-", "");
                        if ((int) gradosBrujula == (int) gradosAzimutDestino) {
                            tv_grados_brujula.setTextColor(Color.GREEN);
                            iv_compass_image.setImageResource(R.drawable.flecha_color_verde);
                        } else {
                            tv_grados_brujula.setTextColor(Color.RED);
                            iv_compass_image.setImageResource(R.drawable.flecha_color);
                        }
                        tv_grados_brujula.setText(textoFormateado + "º");
                        tv_grados_brujula.setText("" + (int) gradosBrujula);
                        float targetAzimut = (float) gradosAzimutDestino - (float) gradosBrujula;
                        float deltaAzimut = targetAzimut - (float) azimuth;
                        // Ajustar deltaAzimut para evitar rotaciones largas
                        if (deltaAzimut > 180) {
                            deltaAzimut -= 360;
                        } else if (deltaAzimut < -180) {
                            deltaAzimut += 360;
                        }
                        adjustArrow(azimuth + deltaAzimut);//azimuth);
                    }
                });
            }
        };
    }
}



