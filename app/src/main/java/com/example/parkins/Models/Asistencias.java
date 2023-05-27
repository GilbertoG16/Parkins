package com.example.parkins.Models;

public class Asistencias {
    private String nombreUser;
    private String nombreEvento;

    public Asistencias(String nombreUser, String nombreEvento) {
        this.nombreUser = nombreUser;
        this.nombreEvento = nombreEvento;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
}
