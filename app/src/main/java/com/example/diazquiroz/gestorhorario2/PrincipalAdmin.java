package com.example.diazquiroz.gestorhorario2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.resultados.AnswersAdapter;
import com.example.diazquiroz.gestorhorario2.api.resultados.TrackEntityHolder;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiAdapter;
import com.example.diazquiroz.gestorhorario2.api.webservice.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalAdmin extends AppCompatActivity {

    private TextView tvToolBar;
    private Toolbar myToolbar;

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

    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ApiService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);

        myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        tvToolBar = myToolbar.findViewById(R.id.appToolBar_title);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolBar.setText("Tiendas");

        System.out.print("Hola mundo");


        //
        mService = ApiAdapter.createService(ApiService.class);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new AnswersAdapter(this, new ArrayList<Tienda>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(PrincipalAdmin.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);*/

        loadAnswers();

    }

    public void nuevaTiendaBtn(View view) {
        Intent intent = new Intent(this, registroTienda.class);
        startActivity(intent);
    }

    public void loadAnswers() {
        mService.listTienda().enqueue(new Callback<TrackEntityHolder>() {
            @Override
            public void onResponse(Call<TrackEntityHolder> call, Response<TrackEntityHolder> response) {

                if(response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body().getResults());
                    System.out.print(mAdapter.toString());
                    //Log.d("PrincipalAdmin", "posts loaded from API");
                    toast = Toast.makeText(context, correcto, duration);
                    toast.show();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    toast = Toast.makeText(context, error_login, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<TrackEntityHolder> call, Throwable t) {
                //showErrorMessage();
                //Log.d("PrincipalAdmin", "error loading from API");
                Log.e(TAG, "PASO ALGO:\n Unable to submit post to API.");
                toast = Toast.makeText(context, ERROR, duration);
                toast.show();
            }
        });
    }


}
