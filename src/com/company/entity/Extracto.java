package com.company.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Extracto implements Serializable {
    String clave;
    double cantidad;
    double dineroHistorico;
    Date dateExtracto;

    public Extracto(String clave, double cantidad, double dineroHistorico, Date dateExtracto) {
        this.clave = clave;
        this.cantidad = cantidad;
        this.dineroHistorico = dineroHistorico;
        this.dateExtracto = dateExtracto;
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

    public Date getDateExtracto() {
        return dateExtracto;
    }

    public void setDateExtracto(Date dateExtracto) {
        this.dateExtracto = dateExtracto;
    }

    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("HH : mm : ss");
        String tiempo = f.format(dateExtracto);
        return "\n\nExtracto: \nOperacion = " + clave + "\nCantidad = " + cantidad + "€\nCuenta = " + dineroHistorico + "€\nHora = " + tiempo;
    }
}
