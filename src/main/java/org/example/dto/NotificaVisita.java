package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class NotificaVisita extends Notifica{
    private Visita visita;


    public NotificaVisita(int id, LocalDate data, LocalTime ora, String descrizione, Utente utente, Visita visita) {
        super(id, data, ora, descrizione, utente);
        this.visita = visita;
    }

    public NotificaVisita() {
    }

    @Override
    public String toString() {
        return "NotificaVisita{" +
                super.toString() +
                "visita=" + visita +
                '}';
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotificaVisita that = (NotificaVisita) o;
        return Objects.equals(visita, that.visita);
    }

}
