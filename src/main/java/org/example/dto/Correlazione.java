package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Correlazione {
    private int id;
    private LocalDate data;
    private LocalTime ora;
    private Inserzione inserzione;
    private Ricerca ricerca;

    public Correlazione(int id, LocalDate data, LocalTime ora, Inserzione inserzione, Ricerca ricerca) {
        this.id = id;
        this.data = data;
        this.ora = ora;
        this.inserzione = inserzione;
        this.ricerca = ricerca;
    }

    public Correlazione() {
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

    public Inserzione getInserzione() {
        return inserzione;
    }

    public void setInserzione(Inserzione inserzione) {
        this.inserzione = inserzione;
    }

    public Ricerca getRicerca() {
        return ricerca;
    }

    public void setRicerca(Ricerca ricerca) {
        this.ricerca = ricerca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correlazione that = (Correlazione) o;
        return id == that.id;
    }


    @Override
    public String toString() {
        return "Correlazione{" +
                "id=" + id +
                ", data=" + data +
                ", ora=" + ora +
                ", inserzione=" + inserzione +
                ", ricerca=" + ricerca +
                '}';
    }
}
