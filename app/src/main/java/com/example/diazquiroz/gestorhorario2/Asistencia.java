package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Asistencia extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextView tvToolBar;

    private Context context;
    private Toast toast_horaMarcada;
    private String mensaje_toast;

    private TextView tvTienda;
    private TextView entradaH;
    private TextView salidaH;

    private Bundle bundle;
    private int idEmpleado;
    private String nombreTienda;

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

        tvToolBar.setText("Asistencia");
        //tvTienda.setText(nombreTienda);
    }

    public void entrada(View view) {
        Date anotherCurDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        String formattedDateString = formatter.format(anotherCurDate);
        entradaH.setText(formattedDateString);

        mensaje_toast = "Hora de entrada marcada\n" + formattedDateString;
        toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
        toast_horaMarcada.show();
    }

    public void salida(View view) {
        Date anotherCurDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        String formattedDateString = formatter.format(anotherCurDate);
        salidaH.setText(formattedDateString);

        mensaje_toast = "Hora de salida marcada\n" + formattedDateString;
        toast_horaMarcada = Toast.makeText(context, mensaje_toast, Toast.LENGTH_SHORT);
        toast_horaMarcada.show();
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
