package com.example.diazquiroz.gestorhorario2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PrincipalAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);
    }

    public void nuevaTiendaBtn(View view) {
        Intent intent = new Intent(this, registroTienda.class);
        startActivity(intent);
    }
}
