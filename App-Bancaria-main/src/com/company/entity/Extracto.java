package com.company.entity;

public class Extracto {
    String clave;
    double cantidad;

    public Extracto(String clave, double cantidad) {
        this.clave = clave;
        this.cantidad = cantidad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Extracto: " + "\nOperacion = " + clave + "\nCantidad = " + cantidad + "€";
    }
}
