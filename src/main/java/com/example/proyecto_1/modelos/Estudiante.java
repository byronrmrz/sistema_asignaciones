package com.example.proyecto_1.modelos;

import java.time.LocalDate;


public class Estudiante {
    private String carnet;
    private String nombre;
    private String email;
    private LocalDate fecha;
    private boolean solvente;

    public Estudiante(String carnet, String nombre, String email, LocalDate fecha, boolean solvente) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.email = email;
        this.fecha = fecha;
        this.solvente = solvente;
    }

    // Getters y Setters
    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isSolvente() {
        return solvente;
    }

    public void setSolvente(boolean solvente) {
        this.solvente = solvente;
    }


}