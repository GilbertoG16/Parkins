package com.example.parkins.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.parkins.MainActivity;
import com.example.parkins.R;

public class AdminHome extends AppCompatActivity {
    TextView name, id ,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        MapearDatos();
        InicializarControles();
    }

    private void InicializarControles() {
        name = (TextView)findViewById(R.id.lblName);
        id = (TextView)findViewById(R.id.lblId);
        user = (TextView)findViewById(R.id.txtUser);
    }

    private void MapearDatos() {
        try {
            SharedPreferences login = getSharedPreferences("Login", Context.MODE_PRIVATE);

            name.setText(login.getString("name",""));
            id.setText(login.getString("id",""));
            user.setText(login.getString("user",""));

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
}