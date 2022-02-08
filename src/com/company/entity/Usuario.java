package com.company.entity;


import java.util.ArrayList;
import java.util.Collection;

public class Usuario {
    String user;
    Integer pass;
    double saldo;
    ArrayList<Extracto> extractos;

    public Usuario(String user, Integer pass, double saldo, ArrayList<Extracto> extractos) {
        this.user = user;
        this.pass = pass;
        this.saldo = saldo;
        this.extractos = extractos;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "user='" + user + '\'' +
                ", pass=" + pass +
                ", saldo=" + saldo +
                ", extractos=" + extractos +
                '}';
    }
}
