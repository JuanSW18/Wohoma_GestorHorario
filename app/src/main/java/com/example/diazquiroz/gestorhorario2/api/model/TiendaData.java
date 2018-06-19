package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DIAZ QUIROZ on 17/06/2018.
 */

public class TiendaData {

    @SerializedName("data")
    private Tienda data;


    public Tienda getData() {
        return data;
    }

    public void setData(Tienda data) {
        this.data = data;
    }
}
