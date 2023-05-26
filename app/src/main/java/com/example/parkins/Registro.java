package com.example.parkins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parkins.Models.Usuarios;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {
    EditText name, id,user,password,edad;
    ArrayList<String> Ops = new ArrayList<String>();
    Spinner spnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        InicializarControles();
        AgregandoValores();
        darClick();
    }

    private void InicializarControles() {
        name = (EditText)findViewById(R.id.txtName);
        id = (EditText)findViewById(R.id.txtCedula);
        user = (EditText)findViewById(R.id.txtUser);
        edad = (EditText)findViewById(R.id.txtAge);
        password = (EditText)findViewById(R.id.txtPassword);
        spnUser = (Spinner)findViewById(R.id.spnuser);
    }
    private void AgregandoValores(){
        Ops.add("Cliente");
        Ops.add("Admin");
    }
    public void darClick(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Ops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUser.setAdapter(adapter);
    }

    private void Notify(String mensajito){
        Toast.makeText(this,mensajito,Toast.LENGTH_LONG).show();
    }

    public void GuardarPersona(View v) {
        try {
            List<Usuarios> usuarios = FileToList();
            boolean existeArchivo = VerificarExistenciaArchivo();

            if (name.length() == 0 || id.length() == 0 || edad.length() == 0 || edad.length() == 0 || user.length() == 0 || password.length() == 0) {
                Toast.makeText(this, "bro faltan datos akii io qué xopa bro", Toast.LENGTH_LONG).show();
            } else {
                String usuarioExistente = checkIfUserExists(usuarios, user.getText().toString());
                if (usuarioExistente != null) {
                    Toast.makeText(this, "Este awebao: " + usuarioExistente + " ya existe", Toast.LENGTH_LONG).show();
                } else {
                    String guardar =
                            name.getText().toString() + "|" +
                                    id.getText().toString() + "|" +
                                    edad.getText().toString() + "|" +
                                    user.getText().toString() + "|" +
                                    password.getText().toString() + "|" +
                                    spnUser.getSelectedItem().toString() + "~";

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

    private String checkIfUserExists(List<Usuarios> usuarios, String username) {
        for (Usuarios usuario : usuarios) {
            if (usuario.getUser().equals(username)) {
                return usuario.getUser();
            }
        }
        return null;
    }




    public int GuardarArchivo(String guardar){
        try {
            String oldTexto="";
            BufferedReader bf = null;
            String texto = "";
            try {
                bf = new BufferedReader(new InputStreamReader(openFileInput("credenciales.txt")));
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
            File archivo = new File(getFilesDir(),"credenciales.txt");
            if(!archivo.exists()){
                archivo.createNewFile();
            }

            OutputStreamWriter fout = new OutputStreamWriter(
                    openFileOutput("credenciales.txt", Context.MODE_PRIVATE));
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
                    new BufferedReader(new InputStreamReader(openFileInput("credenciales.txt")));
            String texto = bf.readLine();
            bf.close();

            if(!texto.isEmpty())
                return true;
        }catch(Exception e){
            return false;
        }
        return false;
    }

    private List<Usuarios> FileToList() {
        List<Usuarios> usuarios = new ArrayList<Usuarios>();

        try{
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(openFileInput("credenciales.txt")));
            String datos = bf.readLine();

            String[] arrUsuarios = datos.split("~");

            for (String strUsuario : arrUsuarios){
                String[] campoUsuario = strUsuario.split("\\|");

                Usuarios usuario = new Usuarios(
                        campoUsuario[0],
                        campoUsuario[1],
                        Integer.parseInt(campoUsuario[2]),
                        campoUsuario[3],
                        campoUsuario[4],
                        campoUsuario[5]
                );
                usuarios.add(usuario);
            }
        }catch(Exception e){
            this.Notify("Errorcito => "+e.getMessage());
        }

        return usuarios;
    }
}