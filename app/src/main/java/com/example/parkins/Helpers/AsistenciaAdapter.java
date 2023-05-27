package com.example.parkins.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.parkins.Models.Asistencias;
import com.example.parkins.R;

import java.util.List;

public class AsistenciaAdapter extends ArrayAdapter<Asistencias> {
    List<Asistencias> opciones;
    public AsistenciaAdapter(Context context, List<Asistencias> datos) {
        super(context, R.layout.listview_asistencia,datos);

        opciones = datos;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_eventos,null);

        TextView user = (TextView)item.findViewById(R.id.lblUsuarioAsistencia);
        user.setText(opciones.get(position).getNombreUser());

        TextView evento = (TextView)item.findViewById(R.id.lblEventoNombreList);
        evento.setText(opciones.get(position).getNombreEvento());

        return (item);
    }
}
