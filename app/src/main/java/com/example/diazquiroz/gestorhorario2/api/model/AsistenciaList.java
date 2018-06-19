package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Created by DIAZ QUIROZ on 10/06/2018.
 */

public class AsistenciaList {
    @SerializedName("data")
    private List<Asistencia> data;

    public List<Asistencia> getData() {
        return data;
    }

    public class Asistencia {
        @SerializedName("idAsistencia")
        private Integer idAsistencia;

        @SerializedName("hEntrada")
        private Fecha hEntrada;

        @SerializedName("hSalida")
        private Fecha hSalida;

        @SerializedName("fecha")
        private Fecha fecha;

        @SerializedName("hExtras")
        private int hExtras;

        //@SerializedName("idEmpleado")
        //private Empleado idempleado;

        public Integer getIdAsistencia() {
            return idAsistencia;
        }

        public void setIdAsistencia(Integer idAsistencia) {
            this.idAsistencia = idAsistencia;
        }

        public Fecha gethEntrada() {
            return hEntrada;
        }

        public void sethEntrada(Fecha hEntrada) {
            this.hEntrada = hEntrada;
        }

        public Fecha gethSalida() {
            return hSalida;
        }

        public void sethSalida(Fecha hSalida) {
            this.hSalida = hSalida;
        }

        public Fecha getFecha() {
            return fecha;
        }

        public void setFecha(Fecha fecha) {
            this.fecha = fecha;
        }

        public int gethExtras() {
            return hExtras;
        }

        public void sethExtras(int hExtras) {
            this.hExtras = hExtras;
        }

        public class Fecha {
            @SerializedName("timestamp")
            private Integer fecha;

            @SerializedName("offset")
            private Integer offset;

            public Integer getFecha() {
                return fecha;
            }

            public void setFecha(Integer fecha) {
                this.fecha = fecha;
            }

            public Integer getOffset() {
                return offset;
            }

            public void setOffset(Integer offset) {
                this.offset = offset;
            }
        }
    }

    /*public class Empleado {
        @SerializedName("idEmpleado")
    }*/
}
