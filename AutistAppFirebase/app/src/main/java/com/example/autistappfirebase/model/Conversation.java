package com.example.autistappfirebase.model;

public class Conversation {
    private String cadEsp, cadIng, tipo, hora;

    public Conversation() {
    }

    public Conversation(String cadEsp, String cadIng, String tipo, String hora) {
        this.cadEsp = cadEsp;
        this.cadIng = cadIng;
        this.tipo = tipo;
        this.hora = hora;
    }

    public String getCadEsp() {
        return cadEsp;
    }

    public void setCadEsp(String cadEsp) {
        this.cadEsp = cadEsp;
    }

    public String getCadIng() {
        return cadIng;
    }

    public void setCadIng(String cadIng) {
        this.cadIng = cadIng;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "cadEsp='" + cadEsp + '\'' +
                ", cadIng='" + cadIng + '\'' +
                ", tipo='" + tipo + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
