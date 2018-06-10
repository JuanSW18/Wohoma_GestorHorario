package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
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

import com.example.diazquiroz.gestorhorario2.api.model.EntidadAsistencia;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import java.util.Date;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar =  myToolbar.findViewById(R.id.appToolBar_title);
        context = getApplicationContext();
        tvTienda = findViewById(R.id.tv_tienda);
        entradaH = findViewById(R.id.entradaH);
        salidaH = findViewById(R.id.salidaH);
        bundle = getIntent().getExtras();
        //nombreTienda = bundle.getString("nombreTienda");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        entidadAsistencia = new EntidadAsistencia();

        Date fecha = new Date();
        entidadAsistencia.setFecha(fecha.getTime());

        tvToolBar.setText("Asistencia");
        //tvTienda.setText(nombreTienda);

        Button salida = findViewById(R.id.btnMarcarSalida);
        salida.setEnabled(false);
    }

    public void entrada(View view) {
        Date hEntrada = new Date();

        String formatHEntrada = formatter.format(hEntrada);
        entradaH.setText(formatHEntrada);
        entidadAsistencia.sethEntrada(formatHEntrada);
        entidadAsistencia.sethSalida("00:00:00");

        mensaje_toast = "Hora de entrada marcada\n" + formatHEntrada;
        toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
        toast_horaMarcada.show();

        context = getApplicationContext();
        saveAsistencia("{\"hEntrada\":\"" + entidadAsistencia.gethEntrada()
                + "\",\"fecha\":\"" + entidadAsistencia.getFecha()
                + "\",\"hSalida\":\"" + entidadAsistencia.gethSalida()
                + "\",\"hExtras\":\"" + 0
                + "\",\"idEmpleado\":\"" + 2 + "\"}");

        Button entrada = findViewById(R.id.btnMarcarEntrada);
        entrada.setEnabled(false);
        Button salida = findViewById(R.id.btnMarcarSalida);
        salida.setEnabled(true);
    }

    public void salida(View view) {
        Date hSalida = new Date();

        String formatHSalida = formatter.format(hSalida);
        salidaH.setText(formatHSalida);
        entidadAsistencia.sethSalida(formatHSalida);

        mensaje_toast = "Hora de salida marcada\n" + formatHSalida;
        toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
        toast_horaMarcada.show();

        context = getApplicationContext();
        editAsistencia("{\"hSalida\":\"" + entidadAsistencia.gethSalida()
                + "\",\"hExtras\":\"" + 0
                + "\",\"idEmpleado\":\"" + 2 + "\"}");

        Button salida = findViewById(R.id.btnMarcarSalida);
        salida.setEnabled(false);
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
                    entidadAsistencia = response.body();
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

    private void editAsistencia(String json) {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<EntidadAsistencia> call = apiService.editAsistencia(json, 15);
        call.enqueue(new Callback<EntidadAsistencia>() {
            @Override
            public void onResponse(Call<EntidadAsistencia> call, Response<EntidadAsistencia> response) {
                if(response.isSuccessful()){
                    entidadAsistencia = response.body();
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
