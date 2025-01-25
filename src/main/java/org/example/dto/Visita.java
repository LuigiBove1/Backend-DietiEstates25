package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Visita {
    private int id;
    private boolean esito;
    private LocalTime ora;
    private LocalDate data;
    private NotificaVisita notificaVisita;

    public Visita(int id, boolean esito, LocalTime ora, LocalDate data, NotificaVisita notificaVisita) {
        this.id = id;
        this.esito = esito;
        this.ora = ora;
        this.data = data;
        this.notificaVisita = notificaVisita;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "id=" + id +
                ", esito=" + esito +
                ", ora=" + ora +
                ", data=" + data +
                ", notificaVisita=" + notificaVisita +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEsito() {
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

    public NotificaVisita getNotificaVisita() {
        return notificaVisita;
    }

    public void setNotificaVisita(NotificaVisita notificaVisita) {
        this.notificaVisita = notificaVisita;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visita visita = (Visita) o;
        return id == visita.id;
    }
    public Visita() {}
}
