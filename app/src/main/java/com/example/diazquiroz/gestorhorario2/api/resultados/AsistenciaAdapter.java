package com.example.diazquiroz.gestorhorario2.api.resultados;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diazquiroz.gestorhorario2.R;
import com.example.diazquiroz.gestorhorario2.api.model.EntidadAsistencia;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>{

    private ArrayList<EntidadAsistencia> asistencias;
    private Context context;
    private static Integer numero = 0;

    public AsistenciaAdapter(Context context, ArrayList<EntidadAsistencia> asistencias){
        this.asistencias = asistencias;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView idAsistencia, hEntrada, hSalida, fecha, hExtras;
        public ViewHolder(View itemView) {
            super(itemView);
            idAsistencia = itemView.findViewById(R.id.idAsistencia);
            hEntrada = itemView.findViewById(R.id.hEntrada);
            hSalida = itemView.findViewById(R.id.hSalida);
            fecha = itemView.findViewById(R.id.fecha);
            hExtras = itemView.findViewById(R.id.hExtras);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EntidadAsistencia asistencia = asistencias.get(position);
        //++numero;
        //holder.idAsistencia.setText();
        holder.hEntrada.setText(asistencia.gethEntrada().toString());
        holder.hSalida.setText(asistencia.gethSalida().toString());
        holder.fecha.setText(asistencia.getFecha().toString());
        holder.hExtras.setText(asistencia.gethExtras().toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asistencia, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return asistencias.size();
    }

}
