package org.example.dto;

import java.util.Objects;

public class Ricerca {
    private int id;
    private int prezzoMinimo;
    private int prezzoMaximo;
    private String tipologia;
    private String citta;
    private int numeroStanze;
    private String classeEnergetica;
    private Agenzia agenzia;

    public Ricerca(int id, int prezzoMinimo, int prezzoMaximo, String tipologia, String citta, int numeroStanze, String classeEnergetica, Agenzia agenzia) {
        this.id = id;
        this.prezzoMinimo = prezzoMinimo;
        this.prezzoMaximo = prezzoMaximo;
        this.tipologia = tipologia;
        this.citta = citta;
        this.numeroStanze = numeroStanze;
        this.classeEnergetica = classeEnergetica;
        this.agenzia = agenzia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrezzoMinimo() {
        return prezzoMinimo;
    }

    public void setPrezzoMinimo(int prezzoMinimo) {
        this.prezzoMinimo = prezzoMinimo;
    }

    public int getPrezzoMaximo() {
        return prezzoMaximo;
    }

    public void setPrezzoMaximo(int prezzoMaximo) {
        this.prezzoMaximo = prezzoMaximo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public int getNumeroStanze() {
        return numeroStanze;
    }

    public void setNumeroStanze(int numeroStanze) {
        this.numeroStanze = numeroStanze;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public Agenzia getAgenzia() {
        return agenzia;
    }

    public void setAgenzia(Agenzia agenzia) {
        this.agenzia = agenzia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ricerca ricerca = (Ricerca) o;
        return id == ricerca.id;
    }

    @Override
    public String toString() {
        return "Ricerca{" +
                "id=" + id +
                ", prezzoMinimo=" + prezzoMinimo +
                ", prezzoMaximo=" + prezzoMaximo +
                ", tipologia='" + tipologia + '\'' +
                ", citta='" + citta + '\'' +
                ", numeroStanze=" + numeroStanze +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", agenzia=" + agenzia +
                '}';
    }
}
