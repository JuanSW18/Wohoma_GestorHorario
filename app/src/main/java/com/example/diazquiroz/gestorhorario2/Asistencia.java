package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.diazquiroz.gestorhorario2.api.model.AsistenciaList;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadAsistencia;
import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.model.TiendaData;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Asistencia extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextView tvToolBar;

    private Toast toast_horaMarcada;
    private String mensaje_toast;

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private TextView tvTienda;
    private TextView entradaH;
    private TextView salidaH;

    private EntidadAsistencia entidadAsistencia;

    private Bundle bundle;
    private int idEmpleado;
    private String nombreTienda;

    private ApiService apiService;
    private String status;
    private final String TAG = Asistencia.class.getSimpleName();

    private Intent intent;
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private CharSequence correcto = "Exito en el registro";
    private CharSequence error_login = "Ocurrio un error en el registro";
    private CharSequence datos_vacios = "Faltan datos";
    private CharSequence ERROR = "UPPS PASO ALGO";
    private Toast toast;

    //variables para el gps
    double latitud, longitud;
    private LocationManager locationManager = null;
    LocationListener locationListener = null;
    double[] punto;
    //double[][] shape;

    private ArrayList<EntidadAsistencia> asistencias;

    private int idEmpleado2;
    private int idTienda;

    private Tienda tienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        Button salida = findViewById(R.id.btnMarcarSalida);
        salida.setEnabled(false);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar =  myToolbar.findViewById(R.id.appToolBar_title);
        context = getApplicationContext();
        tvTienda = findViewById(R.id.tv_tienda);
        entradaH = findViewById(R.id.entradaH);
        salidaH = findViewById(R.id.salidaH);
        bundle = getIntent().getExtras();

        idEmpleado2 = bundle.getInt("USER_ID");
        idTienda = bundle.getInt("ID_TIENDA");
        nombreTienda = bundle.getString("USER_TIENDA");
        Log.e(TAG, "tienda: " + idTienda);

        getAsistencia();

        tienda = new Tienda();
        getTienda();


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Asistencia");
        tvTienda.setText(nombreTienda);

        asistencias = new ArrayList<>();

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
    }

    public void getAsistencia() {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<AsistenciaList> call = apiService.getAsistencia(idEmpleado2);
        call.enqueue(new Callback<AsistenciaList>() {
            @Override
            public void onResponse(Call<AsistenciaList> call, Response<AsistenciaList> response) {
                if(response.isSuccessful()){
                    for(AsistenciaList.Asistencia asist: response.body().getData()){
                        EntidadAsistencia asistencia = new EntidadAsistencia();
                        asistencia.setIdAsistencia(asist.getIdAsistencia());
                        asistencia.sethEntrada(asist.gethEntrada().getFecha());
                        asistencia.sethSalida(asist.gethSalida().getFecha());
                        asistencia.setFecha(asist.getFecha().getFecha());
                        asistencia.sethExtras(asist.gethExtras());
                        asistencia.setIdempleado(idEmpleado2);
                        Log.e(TAG, asistencia.toString());
                        asistencias.add(asistencia);
                    }
                    Log.e(TAG, asistencias.toString());
                    entidadAsistencia = obtenerAsistencia();
                }else{
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<AsistenciaList> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                /*toast = Toast.makeText(context, ERROR, duration);
                toast.show();*/
            }
        });
    }

    public void entrada(View view) {
        Log.e(TAG, "tienda: " + tienda.toString());
        if(validarUbicacion()) {
        //EntidadAsistencia entidadAsistencia = obtenerAsistencia();
            Log.e(TAG, "entrada: " + entidadAsistencia.toString());
            Date hEntrada = new Date();

            String formatHEntrada = formatter.format(hEntrada);
            entradaH.setText(formatHEntrada);
            entidadAsistencia.sethEntrada(formatHEntrada);
            entidadAsistencia.sethSalida("00:00:00");

            mensaje_toast = "Hora de entrada marcada\n" + formatHEntrada;
            toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
            toast_horaMarcada.show();

            context = getApplicationContext();
            saveAsistencia("{\"hEntrada\":\"" + entidadAsistencia.gethEntrada().toString()
                    + "\",\"fecha\":\"" + entidadAsistencia.getFecha().toString()
                    + "\",\"hSalida\":\"" + entidadAsistencia.gethSalida().toString()
                    + "\",\"hExtras\":\"" + 0
                    + "\",\"idEmpleado\":\"" + idEmpleado2 + "\"}");

            Button entrada = findViewById(R.id.btnMarcarEntrada);
            entrada.setEnabled(false);
            Button salida = findViewById(R.id.btnMarcarSalida);
            salida.setEnabled(true);
        } else {
            Toast toast = Toast.makeText(context, "ERROR: no se encuentra en su puesto de trabajo", duration);
            toast.show();
        }
    }

    public void salida(View view) {
        if (validarUbicacion()) {
            Date hSalida = new Date();

            Log.e(TAG, entidadAsistencia.toString());
            String formatHSalida = formatter.format(hSalida);
            entidadAsistencia.sethSalida(formatHSalida);


            long entrada = entidadAsistencia.gethEntrada().getTime();
            Log.e(TAG, "entrada: " + entidadAsistencia.gethEntrada().getTime());
            long salida = entidadAsistencia.gethSalida().getTime();
            Log.e(TAG, "salida: " + entidadAsistencia.gethSalida().getTime());
            long horas = (salida - entrada)/(60*60*1000);
            Log.e(TAG, "horas: " + horas);

            if (horas >= 0 ) {

                salidaH.setText(formatHSalida);

                mensaje_toast = "Hora de salida marcada\n" + formatHSalida;
                toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
                toast_horaMarcada.show();

                context = getApplicationContext();
                editAsistencia("{\"hSalida\":\"" + entidadAsistencia.gethSalida().toString()
                        + "\",\"hExtras\":\"" + (horas > 8 ? horas - 8 : 0)
                        + "\",\"idEmpleado\":\"" + idEmpleado2 + "\"}", entidadAsistencia.getIdAsistencia());

            } else {
                mensaje_toast = "No ha trabajado lo suficiente";
                toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
                toast_horaMarcada.show();
            }

            Button salidaBtn = findViewById(R.id.btnMarcarSalida);
            salidaBtn.setEnabled(false);
        } else {
            Toast toast = Toast.makeText(context, "ERROR: no se encuentra en su puesto de trabajo", duration);
            toast.show();
        }
    }

    public EntidadAsistencia obtenerAsistencia() {
        EntidadAsistencia entidadAsistencia;
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
        String formatoFecha = formatFecha.format(fecha);
        entidadAsistencia = new EntidadAsistencia();
        if (asistencias.isEmpty()) {
            entidadAsistencia.setFecha(formatoFecha);
            Log.e(TAG, "instancio la fecha en diferente de null");
        } else {
            Log.e(TAG, "entro al diferente del vacio");
            for (EntidadAsistencia asist: asistencias) {
                if (asist.getFecha().toString().equals(formatoFecha)) {
                    entidadAsistencia.setIdAsistencia(asist.getIdAsistencia());
                    entidadAsistencia.sethEntrada(asist.gethEntrada().toString());
                    entidadAsistencia.sethSalida(asist.gethSalida().toString());
                    entidadAsistencia.setFecha(asist.getFecha().toString());
                    entidadAsistencia.sethExtras(asist.gethExtras());
                    entidadAsistencia.setIdempleado(asist.getIdempleado());
                    entradaH.setText(entidadAsistencia.gethEntrada().toString());
                    if (entidadAsistencia.gethSalida().toString().equals("00:00:00")){
                        Button entrada = findViewById(R.id.btnMarcarEntrada);
                        entrada.setEnabled(false);
                        Button salida = findViewById(R.id.btnMarcarSalida);
                        salida.setEnabled(true);
                    } else {
                        salidaH.setText(entidadAsistencia.gethSalida().toString());
                        Button entrada = findViewById(R.id.btnMarcarEntrada);
                        entrada.setEnabled(false);
                        Button salida = findViewById(R.id.btnMarcarSalida);
                        salida.setEnabled(false);
                    }
                }
            }
            if ( entidadAsistencia == null) {
                entidadAsistencia.setFecha(formatoFecha);
                Log.e(TAG, "instancio en fecha diferente");
            }
        }
        Log.e(TAG, "fin del if");
        return entidadAsistencia;
    }

    public void getTienda() {
        Log.e(TAG, "tienda: " + idTienda);
        Log.e(TAG, "idEmpleado: " + idEmpleado2);
        apiService = ApiAdapter.createService(ApiService.class);
        Call<TiendaData> call = apiService.getTienda(idTienda);
        call.enqueue(new Callback<TiendaData>() {
            @Override
            public void onResponse(Call<TiendaData> call, Response<TiendaData> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "entro al response");
                    //if ( response.body().getData().getStatus().equals("SUCCESS")) {
                        Log.e(TAG, "entro al if del response");
                        Log.e(TAG, "tienda: " + response.body().getData());
                        tienda = response.body().getData();
                        Log.e(TAG, "tienda: " + tienda.toString());
                    //}
                }else{
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<TiendaData> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit get to API.");
            }
        });
    }

    //Validacion de gps
    static boolean intersects(double[] A, double[] B, double[] P) {
        if (A[1] > B[1])
            return intersects(B, A, P);

        //if (P[1] == A[1] || P[1] == B[1])
        //    P[1] += 0.0001;

        if (P[1] > B[1] || P[1] < A[1] || P[0] > max(A[0], B[0]))
            return false;

        if (P[0] <= min(A[0], B[0]))
            return true;

        double red = (P[1] - A[1]) / (double) (P[0] - A[0]);
        double blue = (B[1] - A[1]) / (double) (B[0] - A[0]);
        return red >= blue;
    }

    static boolean contains(double[][] shape, double[] pnt) {
        boolean inside = false;
        int len = shape.length;
        for (int i = 0; i < len; i++) {
            if (intersects(shape[i], shape[(i + 1) % len], pnt)){
                inside = true;
            }
        }
        return inside;
    }

    public boolean obtenerGPS () {
        boolean flag = false;
        Context context = getApplicationContext();
        CharSequence error = "security exception, no location available";
        CharSequence error2 = "Presione otra vez";
        int duration = Toast.LENGTH_SHORT;
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            if (longitud != 0.0 && latitud != 0.0 ) {
                punto = new double[2];
                punto[0] = longitud;
                punto[1] = latitud;
                Toast toast = Toast.makeText(context, "Exito en obtener su ubicacion", duration);
                Log.e(TAG, "puntos: " + punto[0] + " " + punto[1]);
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

    public boolean validarUbicacion() {
        boolean flag = false;
        if (obtenerGPS()) {
            double[][] shape = {{tienda.getLongitud0(), tienda.getLatitud0()},
                    {tienda.getLongitud1(), tienda.getLatitud1()},
                    {tienda.getLongitud2(), tienda.getLatitud2()},
                    {tienda.getLongitud3(), tienda.getLatitud3()}};
            Log.e(TAG, "shape: " + shape[0][0] + " " + shape[0][1]);
            if (contains(shape, punto)) {
                flag = true;
                Log.e(TAG, "validar ubicacion: " + flag);
                return flag;
            }
        }
        Log.e(TAG, "validar ubicacion: " + flag);
        return flag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id_item = item.getItemId();
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if( id_item == R.id.item_cerrar_sesion){
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void saveAsistencia(String json) {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<EntidadAsistencia> call = apiService.saveAsistencia(json);
        call.enqueue(new Callback<EntidadAsistencia>() {
            @Override
            public void onResponse(Call<EntidadAsistencia> call, Response<EntidadAsistencia> response) {
                if(response.isSuccessful()){
                    //entidadAsistencia = response.body();
                    status = response.body().getStatus();
                    Log.i(TAG, "VALOR DE STATUS:\n" + status);
                    if (status.equals("SUCCESS")) {
                        toast = Toast.makeText(context, correcto, duration);
                        toast.show();

                    } else {
                        toast = Toast.makeText(context, error_login, duration);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntidadAsistencia> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                toast = Toast.makeText(context, ERROR, duration);
                toast.show();
            }
        });
    }

    private void editAsistencia(String json, int idAsistencia) {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<EntidadAsistencia> call = apiService.editAsistencia(json, idAsistencia);
        call.enqueue(new Callback<EntidadAsistencia>() {
            @Override
            public void onResponse(Call<EntidadAsistencia> call, Response<EntidadAsistencia> response) {
                if(response.isSuccessful()){
                    //entidadAsistencia = response.body();
                    status = response.body().getStatus();
                    Log.i(TAG, "VALOR DE STATUS:\n" + status);
                    if (status.equals("SUCCESS")) {
                        toast = Toast.makeText(context, correcto, duration);
                        toast.show();

                    } else {
                        toast = Toast.makeText(context, error_login, duration);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EntidadAsistencia> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                toast = Toast.makeText(context, ERROR, duration);
                toast.show();
            }
        });
    }
}
