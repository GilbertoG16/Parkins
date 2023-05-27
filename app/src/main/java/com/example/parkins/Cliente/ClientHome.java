package com.example.parkins.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkins.Helpers.EventosAdapter;
import com.example.parkins.MainActivity;
import com.example.parkins.Models.Asistencias;
import com.example.parkins.Models.Eventos;
import com.example.parkins.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ClientHome extends AppCompatActivity {
    TextView name , id , user;
    ListView lstEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        InicializarControles();
        MapearDatos();
        Tocar();
    }

    private void InicializarControles() {
        name = (TextView)findViewById(R.id.lblNameC);
        id = (TextView)findViewById(R.id.lblId);
        user = (TextView)findViewById(R.id.lblUserC);
        lstEventos = (ListView)findViewById(R.id.lstEventos);
        SetearAdapter();
    }
    private void SetearAdapter(){
        EventosAdapter adapter = new EventosAdapter(getApplicationContext(),this.FileToList());
        lstEventos.setAdapter(adapter);
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
    private List<Asistencias> FileToListA() {
        List<Asistencias> asistencias = new ArrayList<Asistencias>();

        try{
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(openFileInput("eventoasistencia.txt")));
            String datos = bf.readLine();

            String[] arrAsistencias = datos.split("~");

            for (String strAsistencia : arrAsistencias){
                String[] campoEvento = strAsistencia.split("\\|");

                Asistencias asistencia = new Asistencias(
                        campoEvento[0],
                        campoEvento[1]
                );
                asistencias.add(asistencia);
            }
        }catch(Exception e){

        }

        return asistencias;
    }

    private void Notify(String mensajito) {
        Toast.makeText(getApplicationContext(),mensajito,Toast.LENGTH_LONG).show();
    }

    private void MapearDatos() {
        try {
            SharedPreferences usuarioLogueado = getSharedPreferences("Login", Context.MODE_PRIVATE);

            name.setText(usuarioLogueado.getString("name",""));
            id.setText(usuarioLogueado.getString("id",""));
            user.setText(usuarioLogueado.getString("user",""));

        }catch(Exception e){

        }
    }
    public void Tocar(){

        lstEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Eventos eventoSeleccionado = (Eventos) parent.getItemAtPosition(position);

                GuardarAsistencia(eventoSeleccionado.getNombre());
            }
        });

    }

    public void Logout(View v){
        SharedPreferences login = getSharedPreferences("Login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();

        editor.putString("name","");
        editor.putString("id","");
        editor.putInt("age",0);
        editor.putString("user","");
        editor.putString("password","");
        editor.putString("userType","");

        editor.commit();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void GuardarAsistencia(String evento){
        SharedPreferences usuarioLogueado = getSharedPreferences("Login", Context.MODE_PRIVATE);

        try {
            List<Asistencias> asistencias = FileToListA();


                String usuarioExistente = checkSiEstaRegistrado(asistencias, usuarioLogueado.getString("user",""),evento);
                if (usuarioExistente != null) {
                    Toast.makeText(this, usuarioExistente, Toast.LENGTH_LONG).show();
                } else {
                    String guardar =
                            usuarioLogueado.getString("user","") + "|" +
                                    evento + "~" ;


                    int res = GuardarArchivoAsistenciaEvento(guardar);
                    if (res == 1) {
                        this.Notify("Offi io te hiciste el loco bebi");
                    } else
                        this.Notify("BROEEEEER TA MAL LOCO QUÉ hicite");
                }
        } catch (Exception e) {
            this.Notify("Error: " + e.getMessage());
        }
    }
    public int GuardarArchivoAsistenciaEvento(String guardar){
        try {
            String oldTexto="";
            BufferedReader bf = null;
            String texto = "";
            try {
                bf = new BufferedReader(new InputStreamReader(openFileInput("eventoasistencia.txt")));
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
            File archivo = new File(getFilesDir(),"eventoasistencia.txt");
            if(!archivo.exists()){
                archivo.createNewFile();
            }

            OutputStreamWriter fout = new OutputStreamWriter(
                    openFileOutput("eventoasistencia.txt", Context.MODE_PRIVATE));
            fout.write(oldTexto+guardar);
            fout.close();

            return 1;
        }catch(Exception e){
            Log.e("Ficheros", "Error al escribir fichero a memoria interna"+e.getMessage());
            return 0;
        }
    }

    private String checkSiEstaRegistrado(List<Asistencias> asistencias,String user,String evento){
        for(Asistencias asistencia : asistencias){
            if(asistencia.getNombreUser().equals(user)&&asistencia.getNombreEvento().equals(evento)){
                return "Ya está registrado al evento";
            }
        }

        return null;
    }
}