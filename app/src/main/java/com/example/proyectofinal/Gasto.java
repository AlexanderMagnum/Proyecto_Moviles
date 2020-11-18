package com.example.proyectofinal;

import java.io.Serializable;

public class Gasto implements Serializable {

    private String folio;
    private String concepto;
    private String costo;
    private String numeroPiezas;

    public Gasto() {
    }

    public Gasto(String folio, String concepto, String costo, String numeroPiezas, String imagen) {
        this.folio = folio;
        this.concepto = concepto;
        this.costo = costo;
        this.numeroPiezas = numeroPiezas;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getNumeroPiezas() {
        return numeroPiezas;
    }

    public void setNumeroPiezas(String numeroPiezas) {
        this.numeroPiezas = numeroPiezas;
    }

    @Override
    public String toString()   {
        return  "Folio:     "                + folio     + "\n"
                +"Concepto:  "          + concepto        + "\n"
                +"Costo:     "             + costo        + "\n"
                +"Numero de piezas: "  + numeroPiezas    + "\n";
    }
}
