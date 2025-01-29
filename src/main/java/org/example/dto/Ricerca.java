package org.example.dto;


public class Ricerca {
    private int id;
    private int prezzoMinimo;
    private int prezzoMassimo;
    private String tipologia;
    private String citta;
    private int numeroStanze;
    private String classeEnergetica;
    private Utente utente;

    public Ricerca(int id, int prezzoMinimo, int prezzoMassimo, String tipologia, String citta, int numeroStanze, String classeEnergetica, Utente utente) {
        this.id = id;
        this.prezzoMinimo = prezzoMinimo;
        this.prezzoMassimo = prezzoMassimo;
        this.tipologia = tipologia;
        this.citta = citta;
        this.numeroStanze = numeroStanze;
        this.classeEnergetica = classeEnergetica;
        this.utente = utente;
    }

    public Ricerca() {
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

    public int getPrezzoMassimo() {
        return prezzoMassimo;
    }

    public void setPrezzoMassimo(int prezzoMassimo) {
        this.prezzoMassimo = prezzoMassimo;
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

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
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
                ", prezzoMaximo=" + prezzoMassimo +
                ", tipologia='" + tipologia + '\'' +
                ", citta='" + citta + '\'' +
                ", numeroStanze=" + numeroStanze +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", utente=" + utente +
                '}';
    }
}
