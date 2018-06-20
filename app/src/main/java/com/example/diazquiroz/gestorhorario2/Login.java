package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diazquiroz.gestorhorario2.api.model.User;
import com.example.diazquiroz.gestorhorario2.api.model.UserDetail;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText edUser;
    private EditText edPass;
    private Button iniciarBtn;

    private String email;
    private String pass;

    private CharSequence bienvenida = "Bienvenido";
    private CharSequence error_login = "Correo y/o contraseña no válidos";
    private CharSequence datos_vacios = "Faltan datos";
    private CharSequence ERROR = "UPPS PASO ALGO";
    private Toast toast;

    private Intent intent;
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private ApiService apiService;
    private final String TAG = Login.class.getSimpleName();
    private User user;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.userTxt);
        edPass = findViewById(R.id.passTxt);
        iniciarBtn = findViewById(R.id.iniciarBtn);

        iniciarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edUser.getText().toString();
                pass = edPass.getText().toString();

                context = getApplicationContext();
                if (!email.isEmpty() && !pass.isEmpty()) {
                    validateUser("{\"email\":\"" + email + "\",\"password\":\"" + pass + "\"}");
                } else {
                    toast = Toast.makeText(context, datos_vacios, duration);
                    toast.show();
                }
            }
        });
    }

    private void validateUser(String json) {
        apiService = ApiAdapter.createService(ApiService.class);
        Call<User> call = apiService.validateUser(json);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user = response.body();
                    //Log.i(TAG, response.body().toString());
                    //status = response.body().getStatus();
                    //Log.i(TAG, "VALOR DE STATUS:\n" + status);
                    //if (!status.equals(null)) {
                        if (user.getStatus().equals("ok")) {
                            getDetalleUsuario(user.getIdEmpleado());
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
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                toast = Toast.makeText(context, ERROR, duration);
                toast.show();
            }
        });
    }

    //FUNCION PARA OBTENER EL NOMBRE DE LA TIENDA e IDROL para poder hacer el intent respectivo
    public void getDetalleUsuario(int idEmpleado){
        apiService = ApiAdapter.createService(ApiService.class);
        Call<UserDetail> call = apiService.getDetalleUsuario(idEmpleado);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if(response.isSuccessful()){
                    String direccionTienda = response.body().getData().getIdTienda().getDireccion();
                    int idTienda = response.body().getData().getIdTienda().getIdTienda();
                    int idRol = response.body().getData().getIdRol().getIdRol();

                    toast = Toast.makeText(context, bienvenida, duration);
                    toast.show();

                    // CACHIMBO PENDEJO PON UNA CONDICIONAL PARA MANDAR AL USER A SU RESPECTIVA VISTA
                    if(idRol == 1){
                        intent = new Intent(Login.this, PrincipalAdmin.class);
                        startActivity(intent);
                    }

                    else{
                        intent = new Intent(Login.this, Principal.class);
                        intent.putExtra("USER_ID", user.getIdEmpleado());
                        intent.putExtra("USER_FULL_NAME", user.getNombre() + " " + user.getApPaterno() + " " + user.getApMaterno());
                        intent.putExtra("USER_DNI", user.getDni());
                        intent.putExtra("USER_TIENDA", direccionTienda);
                        intent.putExtra("ID_TIENDA", idTienda);
                        startActivity(intent);
                    }
                    edUser.setText("");
                    edPass.setText("");

                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Log.e(TAG, "Error al obtener detalle de usuario");
            }
        });
    }

}