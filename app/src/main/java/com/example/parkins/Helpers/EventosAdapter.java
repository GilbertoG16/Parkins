package com.example.parkins.Helpers;

import static com.example.parkins.R.id.lblEventoLugarList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkins.Models.Eventos;
import com.example.parkins.R;

import java.util.List;

public class EventosAdapter extends ArrayAdapter<Eventos> {
    List<Eventos> opciones;
    public EventosAdapter(Context context, List<Eventos> datos) {
        super(context, R.layout.listview_eventos,datos);

        opciones = datos;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_eventos,null);

        TextView lugar = (TextView)item.findViewById(R.id.lblEventoLugarList);
        lugar.setText(opciones.get(position).getLugar());

        TextView nombre = (TextView)item.findViewById(R.id.lblEventoNombreList);
        nombre.setText(opciones.get(position).getNombre());

        TextView hora = (TextView)item.findViewById(R.id.lblEventoHoraList);
        hora.setText(opciones.get(position).getHora());


        return (item);
    }
}
