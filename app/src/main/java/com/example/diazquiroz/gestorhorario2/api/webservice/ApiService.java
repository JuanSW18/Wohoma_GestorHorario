package com.example.diazquiroz.gestorhorario2.api.webservice;

import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.model.User;
import com.example.diazquiroz.gestorhorario2.api.resultados.TrackEntityHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/usuario/login")
    @FormUrlEncoded
    Call<User> validateUser(@Field("json") String json);

    @POST("/tienda/nuevo")
    @FormUrlEncoded
    Call<Tienda> saveTienda(@Field("json") String json);

    @GET("/tienda/lista")
    Call<TrackEntityHolder> listTienda(@Query("tagged") String tags);

    @GET("/tienda/lista")
    Call<TrackEntityHolder> listTienda();

}
