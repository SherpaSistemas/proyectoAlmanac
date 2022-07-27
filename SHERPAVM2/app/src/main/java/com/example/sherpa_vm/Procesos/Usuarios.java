package com.example.sherpa_vm.Procesos;

public class Usuarios {

    public String uid;
    public String Nombre;
    public String Apellidos;
    public String Correo;
    public String Password;


    public Usuarios() {
    }

    public Usuarios(String uid, String nombre, String apellidos, String correo, String password) {
        this.uid = uid;
        Nombre = nombre;
        Apellidos = apellidos;
        Correo = correo;
        Password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return Correo ;
    }
}

