package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntidadPermiso {

    @SerializedName("idPermiso")
    private int idPermiso;

    @SerializedName("razon")
    private String razon;

    @SerializedName("fechaInicial")
    private Fecha fechaInicial;

    @SerializedName("fechaFinal")
    private Fecha fechaFinal;

    @SerializedName("band")
    private boolean band;


    public class Fecha{
        @SerializedName("timestamp")
        private int timestamp;

        @SerializedName("offset")
        private int offset;

        public int getTimestamp() {
            return timestamp;
        }

        public int getOffset() {
            return offset;
        }

        public String getFechatoString() throws ParseException {
            int value;
            value = this.timestamp - this.offset;
            Timestamp ts = new Timestamp(value*1000L);
            Date date = new Date(ts.getTime());
            SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy");
            String java_date = jdf.format(date);

            return java_date;
        }
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public String getRazon() {
        return razon;
    }

    public Fecha getFechaInicial() {
        return fechaInicial;
    }

    public Fecha getFechaFinal() {
        return fechaFinal;
    }

    public boolean isBand() {
        return band;
    }

}
