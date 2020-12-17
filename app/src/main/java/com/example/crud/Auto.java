package com.example.crud;

public class Auto {
    private String placa, marca, modelo;
    private int year, cveCliente;

    public Auto(String placa, String marca, String modelo, int year, int cveCliente ){
        setPlaca(placa);
        setMarca(marca);
        setModelo(modelo);
        setYear(year);
        setCveCliente(cveCliente);
    }

    public void setPlaca(String placa){
        this.placa=placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getYear() {
        return year;
    }

    public int getCveCliente() {
        return cveCliente;
    }

    public void setCveCliente(int cveCliente) {
        this.cveCliente = cveCliente;
    }


}
