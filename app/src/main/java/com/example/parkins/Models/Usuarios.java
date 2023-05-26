package com.example.parkins.Models;

public class Usuarios {


    private String name;
    private String id;
    private int age;
    private String user;
    private String password;

    private String userType;
    public Usuarios() {
    }

    public Usuarios(String name, String id, int edad, String user, String password, String t) {
        this.name = name;
        this.id = id;
        this.age = edad;
        this.user = user;
        this.password = password;
        this.userType = t;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

