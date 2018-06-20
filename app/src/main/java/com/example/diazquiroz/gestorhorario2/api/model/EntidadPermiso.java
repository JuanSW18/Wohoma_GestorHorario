package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
            value = this.timestamp + this.offset;
            /*SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = originalFormat.parse(String.valueOf(value));

            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formatedDate = newFormat.format(date);*/

            int year = value / 10000;
            int month = (value % 10000) / 100;
            int day = value % 100;
            Date date = new GregorianCalendar(year, month, day).getTime();
            return date.toString();
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
