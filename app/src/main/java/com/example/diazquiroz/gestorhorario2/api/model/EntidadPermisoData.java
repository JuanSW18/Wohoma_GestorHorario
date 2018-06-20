package com.example.diazquiroz.gestorhorario2.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntidadPermisoData {

    @SerializedName("data")
    private List<EntidadPermiso> data;

    public List<EntidadPermiso> getData() {
        return data;
    }

    public void setData(List<EntidadPermiso> data) {
        this.data = data;
    }
}
