package com.company.entity;

import java.io.Serializable;

public class Extracto implements Serializable {
    String clave;
    double cantidad;
    double dineroHistorico;

    public Extracto(String clave, double cantidad, double dineroHistorico) {
        this.clave = clave;
        this.cantidad = cantidad;
        this.dineroHistorico = dineroHistorico;
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

    public double getDineroHistorico() {
        return dineroHistorico;
    }

    public void setDineroHistorico(double dineroHistorico) {
        this.dineroHistorico = dineroHistorico;
    }

    @Override
    public String toString() {
        return "Extracto: " + "\nOperacion = " + clave + "\nCantidad = " + cantidad + "€" + "\nCuenta: " + dineroHistorico + "€";
    }
}
