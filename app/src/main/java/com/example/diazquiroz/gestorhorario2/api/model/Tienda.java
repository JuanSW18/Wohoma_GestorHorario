package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DIAZ QUIROZ on 29/05/2018.
 */

public class Tienda {

    private String status = "ok";

    @SerializedName("idTienda")
    private Integer idTienda;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("referencia")
    private String referencia;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("latitud0")
    private double latitud0;

    @SerializedName("longitud0")
    private double longitud0;

    @SerializedName("latitud1")
    private double latitud1;

    @SerializedName("longitud1")
    private double longitud1;

    @SerializedName("latitud2")
    private double latitud2;

    @SerializedName("longitud2")
    private double longitud2;

    @SerializedName("latitud3")
    private double latitud3;

    @SerializedName("longitud3")
    private double longitud3;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda= idTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getLatitud0() {
        return latitud0;
    }

    public void setLatitud0(double latitud0) {
        this.latitud0 = latitud0;
    }

    public double getLongitud0() {
        return longitud0;
    }

    public void setLongitud0(double longitud0) {
        this.longitud0 = longitud0;
    }

    public double getLatitud1() {
        return latitud1;
    }

    public void setLatitud1(double latitud1) {
        this.latitud1 = latitud1;
    }

    public double getLongitud1() {
        return longitud1;
    }

    public void setLongitud1(double longitud1) {
        this.longitud1 = longitud1;
    }

    public double getLatitud2() {
        return latitud2;
    }

    public void setLatitud2(double latitud2) {
        this.latitud2 = latitud2;
    }

    public double getLongitud2() {
        return longitud2;
    }

    public void setLongitud2(double longitud2) {
        this.longitud2 = longitud2;
    }

    public double getLatitud3() {
        return latitud3;
    }

    public void setLatitud3(double latitud3) {
        this.latitud3 = latitud3;
    }

    public double getLongitud3() {
        return longitud3;
    }

    public void setLongitud3(double longitud3) {
        this.longitud3 = longitud3;
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "direccion='" + direccion + '\'' +
                ", referencia='" + referencia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", latitud0=" + latitud0 +
                ", longitud0=" + longitud0 +
                ", latitud1=" + latitud1 +
                ", longitud1=" + longitud1 +
                ", latitud2=" + latitud2 +
                ", longitud2=" + longitud2 +
                ", latitud3=" + latitud3 +
                ", longitud3=" + longitud3 +
                '}';
    }
}