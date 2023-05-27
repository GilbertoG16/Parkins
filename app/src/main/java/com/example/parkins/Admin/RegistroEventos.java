package com.example.parkins.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkins.Models.Eventos;
import com.example.parkins.Models.Usuarios;
import com.example.parkins.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RegistroEventos extends AppCompatActivity {
    EditText nombre, lugar;
    TimePicker hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_eventos);
        InicializarControles();
    }

    private void InicializarControles() {
        nombre = (EditText)findViewById(R.id.txtNombreEvento);
        lugar = (EditText)findViewById(R.id.txtLugarEvento);
        hora = (TimePicker)findViewById(R.id.tmHora);
    }
    private void Notify(String mensajito){
        Toast.makeText(getApplicationContext(),mensajito,Toast.LENGTH_LONG).show();
    }
    public void GuardarEvento(View v){
        try {
            List<Eventos> eventos = FileToList();
            boolean existeArchivo = VerificarExistenciaArchivo();

            if (nombre.length() == 0 || lugar.length() == 0) {
                Toast.makeText(this, "bro faltan datos akii io qué xopa bro", Toast.LENGTH_LONG).show();
            } else {
                String usuarioExistente = checkIfEventsExists(eventos, nombre.getText().toString());
                if (usuarioExistente != null) {
                    Toast.makeText(this, "Este awebao: " + usuarioExistente + " ya existe", Toast.LENGTH_LONG).show();
                } else {
                    String guardar =
                            nombre.getText().toString() + "|" +
                                    lugar.getText().toString() + "|" +
                                    hora.getHour()+":"+hora.getMinute()+ "~";


                    int res = GuardarArchivo(guardar);
                    if (res == 1) {
                        this.Notify("Offi io te hiciste el loco bebi");
                    } else
                        this.Notify("BROEEEEER TA MAL LOCO QUÉ hicite");
                }
            }
        } catch (Exception e) {
            this.Notify("Error: " + e.getMessage());
        }
    }




    private String checkIfEventsExists(List<Eventos> eventos, String nombre) {
        for(Eventos evento : eventos){
            if(evento.getNombre().equals(nombre)){
                return evento.getNombre();
            }
        }
        return null;
    }
    private int GuardarArchivo(String guardar) {
        try {
            String oldTexto="";
            BufferedReader bf = null;
            String texto = "";
            try {
                bf = new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
                texto = bf.readLine();
            }catch (Exception e){
                //El archivo no existe io, se creará a continuación compita
            }finally{
                if(bf != null){
                    bf.close();
                }
            }
            if(!texto.isEmpty()){
                oldTexto = texto;
            }
            File archivo = new File(getFilesDir(),"eventos.txt");
            if(!archivo.exists()){
                archivo.createNewFile();
            }

            OutputStreamWriter fout = new OutputStreamWriter(
                    openFileOutput("eventos.txt", Context.MODE_PRIVATE));
            fout.write(oldTexto+guardar);
            fout.close();

            return 1;
        }catch(Exception e){
            Log.e("Ficheros", "Error al escribir fichero a memoria interna"+e.getMessage());
            return 0;
        }
    }
    private boolean VerificarExistenciaArchivo() {
        try {
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
            String texto = bf.readLine();
            bf.close();

            if(!texto.isEmpty())
                return true;
        }catch(Exception e){
            return false;
        }
        return false;
    }
    private List<Eventos> FileToList() {
        List<Eventos> eventos = new ArrayList<Eventos>();

        try{
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
            String datos = bf.readLine();

            String[] arrEventos = datos.split("~");

            for (String strEvento : arrEventos){
                String[] campoEvento = strEvento.split("\\|");

                Eventos evento = new Eventos(
                        campoEvento[0],
                        campoEvento[1],
                        campoEvento[2]
                );
                eventos.add(evento);
            }
        }catch(Exception e){
            this.Notify("Errorcito => "+e.getMessage());
        }

        return eventos;
    }
}