package com.example.crud;

public class Servicio {

    private String placa, fecha;
    private int noOrden, km;
    private double importe;

    public Servicio(int noOrden, String placa, int km, double importe, String fecha) {
        this.placa = placa;
        this.fecha = fecha;
        this.noOrden = noOrden;
        this.km = km;
        this.importe = importe;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(int noOrden) {
        this.noOrden = noOrden;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
