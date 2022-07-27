package com.example.sherpa_vm.Procesos;

public class Codigos {

    public String uid;
    public String Nombre;
    public String Apellidos;
    public String Correo;
    public String Contraseña;
    public String NCodigos;



    public Codigos() {
    }

    public Codigos(String uid, String nombre, String apellidos, String correo, String contraseña,String ncodigos) {
        this.uid = uid;
        Nombre = nombre;
        Apellidos = apellidos;
        Correo = correo;
        Contraseña = contraseña;
        NCodigos= ncodigos;

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

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
    public String getNCodigos() {
        return NCodigos;
    }

    public void setNCodigos(String NCodigos) {
        this.NCodigos = NCodigos;
    }

    @Override
    public String toString() {
        return Nombre+" "+Apellidos+"      \t       Codigos="+NCodigos ;
    }
}

