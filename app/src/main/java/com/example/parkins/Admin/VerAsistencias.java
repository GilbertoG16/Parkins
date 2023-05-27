package com.example.parkins.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkins.Helpers.AsistenciaAdapter;
import com.example.parkins.Helpers.EventosAdapter;
import com.example.parkins.Models.Asistencias;
import com.example.parkins.Models.Eventos;
import com.example.parkins.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VerAsistencias extends AppCompatActivity {
    TextView user, evento;
    ListView lstAsistencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_asistencias);
        InicializarControles();

    }

    private void InicializarControles() {
        user = (TextView)findViewById(R.id.lblUsuarioAsistencia);
        evento = (TextView)findViewById(R.id.lblEventoAsistencia);
        lstAsistencias = (ListView)findViewById(R.id.lstAsistencia);
        SetearAdapter();
    }

    private void SetearAdapter() {
        AsistenciaAdapter adapter = new AsistenciaAdapter(getApplicationContext(),this.FileToList());
        lstAsistencias.setAdapter(adapter);
    }

    private List<Asistencias> FileToList() {
        List<Asistencias> asistencias = new ArrayList<Asistencias>();

        try{
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(openFileInput("eventoasistencia.txt")));
            String datos = bf.readLine();

            String[] arrAsistencias = datos.split("~");

            for (String strAsistencia : arrAsistencias){
                String[] campoAsistencia = strAsistencia.split("\\|");

                Asistencias asistencia = new Asistencias(
                        campoAsistencia[0],
                        campoAsistencia[1]
                );
                asistencias.add(asistencia);
            }
        }catch(Exception e){
            this.Notify("Errorcito => "+e.getMessage());
        }

        return asistencias;
    }

    private void Notify(String mensajito) {
        Toast.makeText(getApplicationContext(),mensajito,Toast.LENGTH_LONG).show();
    }

}