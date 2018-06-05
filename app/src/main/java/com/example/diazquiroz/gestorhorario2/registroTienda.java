package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.widget.Toast;
import android.widget.Button;

import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registroTienda extends AppCompatActivity {

    private TextView tvToolBar;
    private Toolbar myToolbar;

    private EditText direcTienda;
    private EditText refTienda;
    private EditText telfTienda;

    private String direccion;
    private String referencia;
    private String telefono;
    double latitud, longitud;

    private Intent intent;
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private CharSequence correcto = "Exito en el registro";
    private CharSequence error_login = "Ocurrio un error en el registro";
    private CharSequence datos_vacios = "Faltan datos";
    private CharSequence ERROR = "UPPS PASO ALGO";
    private Toast toast;

    private Tienda tienda;

    private ApiService apiService;
    private String status;
    private final String TAG = registroTienda.class.getSimpleName();

    private LocationManager locationManager = null;
    LocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tienda);

        apiService = ApiAdapter.createService(ApiService.class);
        tienda = new Tienda();

        direcTienda = findViewById(R.id.direcTiendaTxt);
        refTienda = findViewById(R.id.refTiendaTxt);
        telfTienda = findViewById(R.id.telfTiendaTxt);

        Button boton1 = (Button) findViewById(R.id.GPS1Btn);
        boton1.setEnabled(true);
        Button boton2 = (Button) findViewById(R.id.GPS2Btn);
        boton2.setEnabled(false);
        Button boton3 = (Button) findViewById(R.id.GPS3Btn);
        boton3.setEnabled(false);
        Button boton4 = (Button) findViewById(R.id.GPS4Btn);
        boton4.setEnabled(false);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar = myToolbar.findViewById(R.id.appToolBar_title);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Registrar Tienda");
    }

    public void gps1Btn(View view) {
        boolean flag = obtenerGPS();
        if(flag) {
            tienda.setLatitud0(latitud);
            tienda.setLongitud0(longitud);
            Button boton1 = (Button) findViewById(R.id.GPS1Btn);
            boton1.setEnabled(false);
            Button boton2 = (Button) findViewById(R.id.GPS2Btn);
            boton2.setEnabled(true);
        }
    }

    public void gps2Btn(View view) {
        boolean flag = obtenerGPS();
        if(flag) {
            tienda.setLatitud1(latitud);
            tienda.setLongitud1(longitud);
            Button boton1 = (Button) findViewById(R.id.GPS2Btn);
            boton1.setEnabled(false);
            Button boton2 = (Button) findViewById(R.id.GPS3Btn);
            boton2.setEnabled(true);
        }
    }

    public void gps3Btn(View view) {
        boolean flag = obtenerGPS();
        if(flag) {
            tienda.setLatitud2(latitud);
            tienda.setLongitud2(longitud);
            Button boton1 = (Button) findViewById(R.id.GPS3Btn);
            boton1.setEnabled(false);
            Button boton2 = (Button) findViewById(R.id.GPS4Btn);
            boton2.setEnabled(true);
        }
    }

    public void gps4Btn(View view) {
        boolean flag = obtenerGPS();
        if(flag) {
            tienda.setLatitud3(latitud);
            tienda.setLongitud3(longitud);
            Button boton1 = (Button) findViewById(R.id.GPS4Btn);
            boton1.setEnabled(false);
        }
    }

    public void registrarBtn(View view) {
        tienda.setDireccion(direcTienda.getText().toString());
        tienda.setReferencia(refTienda.getText().toString());
        tienda.setTelefono(telfTienda.getText().toString());

        context = getApplicationContext();
        if (!tienda.getDireccion().isEmpty() && !tienda.getReferencia().isEmpty()
                && !tienda.getTelefono().isEmpty() && tienda.getLatitud0() != 0.0 && tienda.getLongitud0() != 0.0
                && tienda.getLatitud1() != 0.0 && tienda.getLongitud1() != 0.0
                && tienda.getLatitud2() != 0.0 && tienda.getLongitud2() != 0.0
                && tienda.getLatitud3() != 0.0 && tienda.getLongitud3() != 0.0) {
            saveTienda("{\"direccion\":\"" + tienda.getDireccion()
                    + "\",\"referencia\":\"" + tienda.getReferencia()
                    + "\",\"telefono\":\"" + tienda.getTelefono()
                    + "\",\"latitud0\":\"" + tienda.getLatitud0()
                    + "\",\"longitud0\":\"" + tienda.getLongitud0()
                    + "\",\"latitud1\":\"" + tienda.getLatitud1()
                    + "\",\"longitud1\":\"" + tienda.getLongitud1()
                    + "\",\"latitud2\":\"" + tienda.getLatitud2()
                    + "\",\"longitud2\":\"" + tienda.getLongitud2()
                    + "\",\"latitud3\":\"" + tienda.getLatitud2()
                    + "\",\"longitud3\":\"" + tienda.getLongitud2() + "\"}");
        } else {
            toast = Toast.makeText(context, datos_vacios, duration);
            toast.show();
        }
    }

    public boolean obtenerGPS () {
        boolean flag = false;
        Context context = getApplicationContext();
        CharSequence error = "security exception, no location available";
        CharSequence latlong = "latitud: " + latitud + " longitud: " + longitud;
        CharSequence error2 = "Presione otra vez";
        int duration = Toast.LENGTH_SHORT;
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            if (longitud != 0.0 && latitud != 0.0 ) {
                Toast toast = Toast.makeText(context, latlong, duration);
                toast.show();
                flag = true;
            } else {
                Toast toast = Toast.makeText(context, error2, duration);
                toast.show();
                flag = false;
            }
        } catch (SecurityException ex) {
            Toast toast = Toast.makeText(context, error, duration);
            toast.show();
            flag = false;
        }
        return flag;
    }

    private void saveTienda(String json) {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<Tienda> call = apiService.saveTienda(json);
        call.enqueue(new Callback<Tienda>() {
            @Override
            public void onResponse(Call<Tienda> call, Response<Tienda> response) {
                if(response.isSuccessful()){
                    tienda = response.body();
                    status = response.body().getStatus();
                    Log.i(TAG, "VALOR DE STATUS:\n" + status);
                    if (status.equals("SUCCESS")) {
                        toast = Toast.makeText(context, correcto, duration);
                        toast.show();

                        intent = new Intent(registroTienda.this, PrincipalAdmin.class);
                        startActivity(intent);

                        direcTienda.setText("");
                        refTienda.setText("");
                        telfTienda.setText("");
                    } else {
                        toast = Toast.makeText(context, error_login, duration);
                        toast.show();
                    }
                    /*} else{
                        Log.i(TAG, "Status del login: " + status);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<Tienda> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                toast = Toast.makeText(context, ERROR, duration);
                toast.show();
            }
        });
    }

}