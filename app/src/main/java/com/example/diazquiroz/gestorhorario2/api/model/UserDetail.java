package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("data")
    private Data data;

    public class Data{

        @SerializedName("idEmpleado")
        private int idEmpleado;

        @SerializedName("idTienda")
        private Tienda idTienda;

        public int getIdEmpleado() {
            return idEmpleado;
        }

        public Tienda getIdTienda() {
            return idTienda;
        }
    }

    public Data getData() {
        return data;
    }
}
