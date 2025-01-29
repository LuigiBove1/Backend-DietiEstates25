package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {
    private int id;
    private boolean esito;
    private LocalTime ora;
    private LocalDate data;

    public Visita(int id, boolean esito, LocalTime ora, LocalDate data) {
        this.id = id;
        this.esito = esito;
        this.ora = ora;
        this.data = data;
    }

    public Visita() {
    }

    @Override
    public String toString() {
        return "Visita{" +
                "id=" + id +
                ", esito=" + esito +
                ", ora=" + ora +
                ", data=" + data +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEsito() {
        return esito;
    }

    public void setEsito(boolean esito) {
        this.esito = esito;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visita visita = (Visita) o;
        return id == visita.id;
    }

}
