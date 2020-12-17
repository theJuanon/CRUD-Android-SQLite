package com.example.crud;

public class Cliente {

    private int cve;
    private String nombre, colonia, ciudad, estado;

    public Cliente(int cve, String nombre, String colonia, String ciudad, String estado ) {
        this.cve = cve;
        this.nombre = nombre;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public int getCve() {
        return cve;
    }

    public void setCve(int cve) {
        this.cve = cve;
    }
}
