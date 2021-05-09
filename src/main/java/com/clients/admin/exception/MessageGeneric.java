package com.clients.admin.exception;

import java.util.ArrayList;

public class MessageGeneric {

    private int codigo;
    private String message;
    private ArrayList detalles;

    public MessageGeneric(int codigo, String message, ArrayList detalles) {
        this.codigo = codigo;
        this.message = message;
        this.detalles = detalles;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList detalles) {
        this.detalles = detalles;
    }
}
