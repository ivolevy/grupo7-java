package com.example.uade.tpo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ContactForm {

    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreApellido;

    @NotEmpty(message = "La problemática es obligatoria")
    private String problematica;

    @NotEmpty(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String descripcion;

    private String[] fotos;

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getProblematica() {
        return problematica;
    }

    public void setProblematica(String problematica) {
        this.problematica = problematica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String[] getFotos() {
        return fotos;
    }

    public void setFotos(String[] fotos) {
        this.fotos = fotos;
    }
}
