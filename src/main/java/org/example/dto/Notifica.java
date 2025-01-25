package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Notifica {
    private int id;
    private LocalDate data;
    private LocalTime ora;
    private String descrizione;
    private Utente utente;

    public Notifica(int id, LocalDate data, LocalTime ora, String descrizione, Utente utente) {
        this.id = id;
        this.data = data;
        this.ora = ora;
        this.descrizione = descrizione;
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Notifica{" +
                "id=" + id +
                ", data=" + data +
                ", ora=" + ora +
                ", descrizione='" + descrizione + '\'' +
                ", utente=" + utente +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Notifica notifica = (Notifica) o;
        return id == notifica.id;
    }
    public Notifica() {}
}
