package com.example.parkins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkins.Admin.AdminHome;
import com.example.parkins.Cliente.ClientHome;
import com.example.parkins.Models.Usuarios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InicializarControles();
    }

    private void ValidarLogin(String userType){
        SharedPreferences usuarioLogueado = getSharedPreferences("Login", Context.MODE_PRIVATE);

        String usuario = usuarioLogueado.getString("user","");

        if(!usuario.equals("")){
            Intent intent;
            if(userType.equals("Admin")){
                intent = new Intent(getApplicationContext(), AdminHome.class);
            }else if(userType.equals("Cliente")){
                intent = new Intent(getApplicationContext(), ClientHome.class);
            }else{
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            startActivity(intent);
        }
    }


    private void InicializarControles() {
        name = (EditText)findViewById(R.id.txtUser);
        password = (EditText)findViewById(R.id.txtPassword);
    }
    public void LoguearUsuario(View v){
        try{
            String nombreIngresado = name.getText().toString();
            String contraseñaIngresado = password.getText().toString();

            List<Usuarios> usuarios = FileToList();
            boolean logueado = false;

            for(Usuarios usuario : usuarios){

                if(nombreIngresado.equals(usuario.getUser())&& contraseñaIngresado.equals(usuario.getPassword())){
                    SharedPreferences user = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = user.edit();
                    editor.putString("name",usuario.getName());
                    editor.putString("id",usuario.getId());
                    editor.putInt("age",usuario.getAge());
                    editor.putString("user",usuario.getUser());
                    editor.putString("password",usuario.getPassword());
                    editor.putString("userType",usuario.getUserType());

                    editor.commit();
                    logueado = true;

                    if(usuario.getUserType().equals("Admin")){
                        ValidarLogin(usuario.getUserType());

                    }else if(usuario.getUserType().equals("Cliente")){
                        ValidarLogin(usuario.getUserType());

                    }

                    break;
                }
            }
            if(!logueado){
                this.Notify("Xa, tú no existe aquí broer qué xopa para merfi vieo");
            }
        }catch(Exception e){
            this.Notify("Error vieo la vaina ta afe => "+e.getMessage());
        }
    }

    private void Notify(String mensajito) {
        Toast.makeText(this,mensajito,Toast.LENGTH_SHORT).show();
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
    public void RegistrarPersona(View v){
        startActivity(new Intent(this, Registro.class));
    }
}