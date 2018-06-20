package com.example.diazquiroz.gestorhorario2.api.webservice;

import com.example.diazquiroz.gestorhorario2.api.model.AsistenciaList;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadAsistencia;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadPermisoData;
import com.example.diazquiroz.gestorhorario2.api.model.PermisoResponse;
import com.example.diazquiroz.gestorhorario2.api.model.Tienda;
import com.example.diazquiroz.gestorhorario2.api.model.TiendaData;
import com.example.diazquiroz.gestorhorario2.api.model.TiendaList;
import com.example.diazquiroz.gestorhorario2.api.model.User;
import com.example.diazquiroz.gestorhorario2.api.model.UserDetail;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/usuario/login")
    @FormUrlEncoded
    Call<User> validateUser(@Field("json") String json);

    @POST("/tienda/nuevo")
    @FormUrlEncoded
    Call<Tienda> saveTienda(@Field("json") String json);

    @GET("usuario/detalle/{id}")
    Call<UserDetail> getDetalleUsuario(@Path("id") int id);

    @GET("/tienda/lista")
    Call<TiendaList> getTiendas();

    @GET("/tienda/detalle/{id}")
    Call<TiendaData> getTienda(@Path("id") int idTienda);

    @GET("/asistencia/siete/ultimos/{id}")
    Call<AsistenciaList> getAsistencia(@Path("id") int idEmpleado);

    @GET("/asistencia/lista")
    Call<AsistenciaList> getAsistencias();

    @GET("/asistencia/detalle/{id}")
    Call<EntidadAsistencia> getAsistenciaDetalle(@Path("id") int idAsistencia);

    @POST("/asistencia/nuevo")
    @FormUrlEncoded
    Call<EntidadAsistencia> saveAsistencia(@Field("json") String json);

    @POST("/asistencia/editar/{id}")
    @FormUrlEncoded
    Call<EntidadAsistencia> editAsistencia(@Field("json") String json,
                                           @Path("id") int idAsistencia);

    @POST("permiso/nuevo")
    @FormUrlEncoded
    Call<PermisoResponse> enviarPermiso(@Field("json") String json);

    @GET("permiso/detalle/empleado/{id}")
    Call<EntidadPermisoData> getListaPermisoById(@Path("id") int idEmpleado);

}
