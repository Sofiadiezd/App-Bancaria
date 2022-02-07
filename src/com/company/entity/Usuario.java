package com.company.entity;


import java.util.ArrayList;
import java.util.Collection;

public class Usuario {
    String user;
    Integer pass;
    double saldo;
    ArrayList<Extracto> extractos;

    public Usuario(String user, int pass, double saldo) {
        this.user = user;
        this.pass = pass;
        this.saldo = saldo;
    }

    public Usuario() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public ArrayList<Extracto> getExtractos() {
        return extractos;
    }

    public void setExtractos(ArrayList<Extracto> extractos) {
        this.extractos = extractos;
    }
}
