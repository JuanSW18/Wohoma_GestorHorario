package com.example.diazquiroz.gestorhorario2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diazquiroz.gestorhorario2.api.model.PermisoResponse;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Permisos extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextView tvToolBar;

    private EditText edRazon;

    DatePickerDialog datePickerDialog;
    private EditText fechaInicio;
    private String diaInicio, mesInicio, anyoInicio;
    private EditText fechaFin;
    private String diaFin, mesFin, anyoFin;

    private Bundle bundle;
    private int idEmpleado;

    private CharSequence ENVIO_EXITOSO = "PERMISO ENVIADO";
    private Toast toast;

    private Intent intent;
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private ApiService apiService;
    private final String TAG = Permisos.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar =  myToolbar.findViewById(R.id.appToolBar_title);
        fechaInicio = findViewById(R.id.permisoFechaInicio);
        fechaFin = findViewById(R.id.permisoFechaFin);
        edRazon = findViewById(R.id.razonTxt);

        bundle = getIntent().getExtras();

        idEmpleado = bundle.getInt("USER_ID");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Solicitud de Permiso");

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escogerFecha(fechaInicio, 1);
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escogerFecha(fechaFin, 2);
            }
        });
    }

    public void enviarBtn (View view) {
        String json, fechaInicial, fechaFin;
        fechaInicial = diaInicio + "-" + mesInicio + "-" + anyoInicio;
        fechaFin = diaFin + "-" + mesFin + "-" + anyoFin;
        json = "{\"idEmpleado\":\"" + idEmpleado +
                "\",\"razon\":\"" + edRazon.getText() +
                "\",\"fechaInicial\":\"" + fechaInicial +
                "\",\"flag\":\"" + "falso" +
                "\",\"fechaFinal\":\"" + fechaFin + "\"}";
        enviarPermiso(json);
    }

    public void enviarPermiso(String json){
        apiService = ApiAdapter.createService(ApiService.class);
        Call<PermisoResponse> call = apiService.enviarPermiso(json);
        call.enqueue(new Callback<PermisoResponse>() {
            @Override
            public void onResponse(Call<PermisoResponse> call, Response<PermisoResponse> response) {
                if(response.isSuccessful()){
                    /*toast = Toast.makeText(context, ENVIO_EXITOSO, duration);
                    toast.show();*/
                    // AL ENVIAR INTENT A PRINCIPAL, ESTA VISTA ESPERA QUE SE LE ENVIEN CAMPOS
                    //intent = new Intent(Permisos.this, Principal.class);
                    //startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<PermisoResponse> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
            }
        });
    }

    public void escogerFecha(final EditText fecha, final int setInicio_Fin){
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(Permisos.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        if (setInicio_Fin == 1){
                            diaInicio = String.valueOf(dayOfMonth);
                            mesInicio = String.valueOf(monthOfYear + 1);
                            anyoInicio = String.valueOf(year);
                        }else {
                            diaFin = String.valueOf(dayOfMonth);
                            mesFin = String.valueOf(monthOfYear + 1);
                            anyoFin = String.valueOf(year);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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
