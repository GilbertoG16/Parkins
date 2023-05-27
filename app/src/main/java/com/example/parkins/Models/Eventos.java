package com.example.parkins.Models;

public class Eventos {

    private String nombre;
    private String lugar;
    private String hora;

    public Eventos(String nombre, String lugar, String hora) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.hora = hora;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
