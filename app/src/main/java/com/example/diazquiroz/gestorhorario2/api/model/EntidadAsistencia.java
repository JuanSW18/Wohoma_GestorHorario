package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by DIAZ QUIROZ on 5/06/2018.
 */

public class EntidadAsistencia {

    private String status = "ok";

    @SerializedName("idAsistencia")
    private Integer idAsistencia;

    @SerializedName("hEntrada")
    private Time hEntrada;

    @SerializedName("hSalida")
    private Time hSalida;

    @SerializedName("fecha")
    private Date fecha;

    @SerializedName("hExtras")
    private int hExtras;

    @SerializedName("idEmpleado")
    private int idempleado;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Time gethEntrada() {
        return hEntrada;
    }

    public void sethEntrada(String hEntrada) {
        this.hEntrada = java.sql.Time.valueOf(hEntrada);
    }

    public Time gethSalida() {
        return hSalida;
    }

    public void sethSalida(String hSalida) {
        this.hSalida = java.sql.Time.valueOf(hSalida);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = new Date(fecha);
    }

    public int gethExtras() {
        return hExtras;
    }

    public void sethExtras(int hExtras) {
        this.hExtras = hExtras;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
}
