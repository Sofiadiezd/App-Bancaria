package com.company.server;

import com.company.entity.Usuario;

import java.io.Serializable;

public class PaqueteDatos implements Serializable {
    Usuario user;
    Boolean serverON;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Boolean getServerON() {
        return serverON;
    }

    public void setServerON(Boolean serverON) {
        this.serverON = serverON;
    }
}