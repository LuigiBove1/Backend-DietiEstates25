package org.example.dto;

import java.util.Objects;

public class Immobile {
    private int id;
    private String indirizzo;
    private int dimensione;
    private int numeroStanze;
    private boolean ascensore;
    private String classeEnergetica;
    private String citta;
    private String codicePostale;
    private double longitude;
    private double latitude;
    private String puntiDiInteresse;

    public Immobile(int id, String indirizzo, int dimensione, int numeroStanze, boolean ascensore, String classeEnergetica, String citta, String codicePostale, double longitude, double latitude, String puntiDiInteresse) {
        this.id = id;
        this.indirizzo = indirizzo;
        this.dimensione = dimensione;
        this.numeroStanze = numeroStanze;
        this.ascensore = ascensore;
        this.classeEnergetica = classeEnergetica;
        this.citta = citta;
        this.codicePostale = codicePostale;
        this.longitude = longitude;
        this.latitude = latitude;
        this.puntiDiInteresse = puntiDiInteresse;
    }

    public Immobile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getDimensione() {
        return dimensione;
    }

    public void setDimensione(int dimensione) {
        this.dimensione = dimensione;
    }

    public int getNumeroStanze() {
        return numeroStanze;
    }

    public void setNumeroStanze(int numeroStanze) {
        this.numeroStanze = numeroStanze;
    }

    public boolean isAscensore() {
        return ascensore;
    }

    public void setAscensore(boolean ascensore) {
        this.ascensore = ascensore;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCodicePostale() {
        return codicePostale;
    }

    public void setCodicePostale(String codicePostale) {
        this.codicePostale = codicePostale;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPuntiDiInteresse() {
        return puntiDiInteresse;
    }

    public void setPuntiDiInteresse(String puntiDiInteresse) {
        this.puntiDiInteresse = puntiDiInteresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Immobile immobile = (Immobile) o;
        return id == immobile.id;
    }

    @Override
    public String toString() {
        return "Immobile{" +
                "id=" + id +
                ", indirizzo='" + indirizzo + '\'' +
                ", dimensione=" + dimensione +
                ", numeroStanze=" + numeroStanze +
                ", ascensore=" + ascensore +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", citta='" + citta + '\'' +
                ", codicePostale='" + codicePostale + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", puntiDiInteresse='" + puntiDiInteresse + '\'' +
                '}';
    }
}
