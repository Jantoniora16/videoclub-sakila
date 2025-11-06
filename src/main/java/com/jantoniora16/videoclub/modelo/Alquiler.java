package com.jantoniora16.videoclub.modelo;

import java.sql.Timestamp;

public class Alquiler {

    private String tituloPelicula;
    private Timestamp fechaAlquiler;
    private Timestamp fechaDevolucion;

    // Constructor
    public Alquiler(String tituloPelicula, Timestamp fechaAlquiler, Timestamp fechaDevolucion) {
        this.tituloPelicula = tituloPelicula;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y Setters
    public String getTituloPelicula() { return tituloPelicula; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }

    public Timestamp getFechaAlquiler() { return fechaAlquiler; }
    public void setFechaAlquiler(Timestamp fechaAlquiler) { this.fechaAlquiler = fechaAlquiler; }

    public Timestamp getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Timestamp fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}