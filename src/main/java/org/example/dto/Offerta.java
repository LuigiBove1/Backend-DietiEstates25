package org.example.dto;

import java.util.Objects;

public class Offerta {
    private int id;
    private int valore;
    private boolean esito;
    private Utente utente;
    private Agente agente;
    private Inserzione inserzione;

    public Offerta(int id, int valore, boolean esito, Utente utente, Agente agente, Inserzione inserzione) {
        this.id = id;
        this.valore = valore;
        this.esito = esito;
        this.utente = utente;
        this.agente = agente;
        this.inserzione = inserzione;
    }


    @Override
    public String toString() {
        return "Offerta{" +
                "id=" + id +
                ", valore=" + valore +
                ", esito=" + esito +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public boolean isEsito() {
        return esito;
    }

    public void setEsito(boolean esito) {
        this.esito = esito;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    public Inserzione getInserzione() {
        return inserzione;
    }

    public void setInserzione(Inserzione inserzione) {
        this.inserzione = inserzione;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Offerta offerta = (Offerta) o;
        return id == offerta.id;
    }
    public Offerta(){}
}
