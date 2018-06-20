package com.example.diazquiroz.gestorhorario2.api.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diazquiroz.gestorhorario2.R;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadPermiso;

import java.text.ParseException;
import java.util.List;

public class PermisoAdapter extends RecyclerView.Adapter<PermisoAdapter.ViewHolder>{

    private List<EntidadPermiso> listaPermisos;
    private Context context;

    public PermisoAdapter(Context context, List<EntidadPermiso> listaPermisos){
        this.context = context;
        this.listaPermisos = listaPermisos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView permisoFechaInicio, permisoFechaFin, permisoBand;
        public ViewHolder(View itemView) {
            super(itemView);
            permisoFechaInicio = itemView.findViewById(R.id.itemPermisoFechaInicio);
            permisoFechaFin = itemView.findViewById(R.id.itemPermisoFechaFin);
            permisoBand = itemView.findViewById(R.id.itemPermisoBand);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EntidadPermiso entidadPermiso = listaPermisos.get(position);
        try {
            holder.permisoFechaInicio.setText(entidadPermiso.getFechaInicial().getFechatoString());
            holder.permisoFechaFin.setText(entidadPermiso.getFechaFinal().getFechatoString());
            holder.permisoBand.setText(String.valueOf(entidadPermiso.isBand()));
        }catch (ParseException e){
            System.out.println("ERROR AL PARSEAR FECHA " + e.getMessage());
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_permiso, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listaPermisos.size();
    }
}
