package com.example.diazquiroz.gestorhorario2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Historial extends AppCompatActivity {

    private Toolbar myToolbar;
    private TextView tvToolBar;
    //private MenuView.ItemView iv_cerrarSesion;

    private TextView tvNombre;
    private TextView tvDni;
    private TextView tvTienda;

    private Bundle bundle;
    private int idEmpleado;
    private String nombreEmpleado;
    private String dniEmpleado;
    private String nombreTienda;

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
        //nombreTienda = bundle.getString("USER_TIENDA");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Historial");

        tvNombre.setText(nombreEmpleado);
        tvDni.setText(dniEmpleado);
        //tvTienda.setText(nombreTienda);
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
