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

        @SerializedName("idRol")
        private Rol dataRol;

        public int getIdEmpleado() {
            return idEmpleado;
        }

        public Tienda getIdTienda() {
            return idTienda;
        }

        public Rol getIdRol() {
            return dataRol;
        }

        public class Rol{
            @SerializedName("idRol")
            private int idRol;

            public int getIdRol() {
                return idRol;
            }

            public void setIdRol(int idRol) {
                this.idRol = idRol;
            }
        }
    }

    public Data getData() {
        return data;
    }
}
