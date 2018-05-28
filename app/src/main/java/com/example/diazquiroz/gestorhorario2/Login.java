package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diazquiroz.gestorhorario2.api.model.User;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText edUser;
    private EditText edPass;

    private String email;
    private String pass;

    private ApiService apiService;
    private final String TAG = Login.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.userTxt);
        edPass = findViewById(R.id.passTxt);
    }

    public void iniciarSesion(View view) {
        email = edUser.getText().toString();
        pass = edPass.getText().toString();

        Context context = getApplicationContext();
        CharSequence bienvenida = "Bienvenido";
        CharSequence error = "Correo y/o contraseña no válidos";
        int duration = Toast.LENGTH_SHORT;

        if(!email.isEmpty() && !pass.isEmpty()){
            //estructura para trabajar el supuesto json
            validateUser("{\"email\":\""+email+"\",\"password\":\""+pass+"\"}");
            if ( email.equals("admin") && pass.equals("1234") ){
                Intent intent = new Intent(this, Principal.class);
                startActivity(intent);

                Toast toast = Toast.makeText(context, bienvenida, duration);
                toast.show();

                edUser.setText("");
                edPass.setText("");
            }else{
                Toast toast = Toast.makeText(context, error, duration);
                toast.show();
            }
        }

    }

    private void validateUser(String json){
        apiService = ApiAdapter.createService(ApiService.class);
        Call<User> call = apiService.validateUser(json);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "post submitted to API." + response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
}