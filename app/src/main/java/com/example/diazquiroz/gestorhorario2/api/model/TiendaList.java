package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TiendaList {

    @SerializedName("data")
    private List<Tienda> data;

    public List<Tienda> getData() {
        return data;
    }
}
