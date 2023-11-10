package com.pruebasena.appeasycredit.model;

public class UsuarioModel {
    private String rol;
    private String nombres;
    private String apellidos;
    private String identificacion;
    private String contraseña;
    private String telefono;
    private String email;
    private String direccion;
    private int edad;

    // Constructor
    public UsuarioModel(String rol, String nombres, String apellidos, String identificacion,
                        String contraseña, String telefono, String email, String direccion, int edad) {
        this.rol = rol;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.edad = edad;
    }

    // Métodos para acceder y modificar los atributos del usuario
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
