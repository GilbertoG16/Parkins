package com.example.parkins.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.parkins.MainActivity;
import com.example.parkins.Models.Eventos;
import com.example.parkins.R;

import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {
    TextView name, id ,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        InicializarControles();
        MapearDatos();

    }

    private void InicializarControles() {
        name = (TextView)findViewById(R.id.lblName);
        id = (TextView)findViewById(R.id.lblId);
        user = (TextView)findViewById(R.id.lblUser);
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
    public void CrearEvento(View v){
        startActivity(new Intent(getApplicationContext(), RegistroEventos.class));
    }
    public void VerAsistencias(View v){
        startActivity(new Intent(getApplicationContext(), VerAsistencias.class));
    }

}