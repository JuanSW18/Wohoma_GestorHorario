package com.example.diazquiroz.gestorhorario2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.diazquiroz.gestorhorario2.api.model.AsistenciaList;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadAsistencia;

import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.model.TiendaList;
import com.example.diazquiroz.gestorhorario2.api.resultados.AsistenciaAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextView tvToolBar;
    //private MenuView.ItemView iv_cerrarSesion;

    private TextView tvNombre;
    private TextView tvDni;
    private TextView tvTienda;

    private RecyclerView recyclerView;
    private ArrayList<EntidadAsistencia> asistencias = new ArrayList<>();
    private AsistenciaAdapter asistenciaAdapter;

    private Bundle bundle;
    private int idEmpleado;
    private String nombreEmpleado;
    private String dniEmpleado;
    private String nombreTienda;

    private ApiService apiService;

    private int idEmpleado2;

    private final String TAG = Asistencia.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar =  myToolbar.findViewById(R.id.appToolBar_title);
        //iv_cerrarSesion = findViewById(R.id.item_cerrar_sesion);

        tvNombre = findViewById(R.id.nombreTxt);
        tvDni = findViewById(R.id.DNITxt);
        tvTienda = findViewById(R.id.tiendaTxt);

        bundle = getIntent().getExtras();
        idEmpleado = bundle.getInt("USER_ID");
        nombreEmpleado = bundle.getString("USER_FULL_NAME");
        dniEmpleado = bundle.getString("USER_DNI");
        nombreTienda = bundle.getString("USER_TIENDA");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Historial");

        recyclerView = findViewById(R.id.my_recycler_view);
        asistenciaAdapter = new AsistenciaAdapter(Historial.this, asistencias);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Historial.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(asistenciaAdapter);

        getAsistencia();

        tvNombre.setText(nombreEmpleado);
        tvDni.setText(dniEmpleado);
        tvTienda.setText(nombreTienda);
    }

    public void getAsistencia() {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<AsistenciaList> call = apiService.getAsistencia(idEmpleado);
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
                        asistencia.setIdempleado(idEmpleado);
                        Log.e(TAG, asistencia.toString());
                        asistencias.add(asistencia);
                    }
                    Log.e(TAG, asistencias.toString());
                    asistenciaAdapter.notifyDataSetChanged();
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
}
