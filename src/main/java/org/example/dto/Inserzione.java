package org.example.dto;

import java.util.Objects;

public class Inserzione {
    private int id;
    private String titolo;
    private String descrizione;
    private int prezzo;
    private String foto;
    private String tipologia;
    private int piano;
    private Immobile immobile;
    private Agente agente;

    public Inserzione(int id, String titolo, String descrizione, int prezzo, String foto, String tipologia, int piano, Immobile immobile, Agente agente) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.foto = foto;
        this.tipologia = tipologia;
        this.piano = piano;
        this.immobile = immobile;
        this.agente = agente;
    }

    public Inserzione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public int getPiano() {
        return piano;
    }

    public void setPiano(int piano) {
        this.piano = piano;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inserzione that = (Inserzione) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "Inserzione{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", foto='" + foto + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", piano=" + piano +
                ", immobile=" + immobile +
                ", agente=" + agente +
                '}';
    }
}
