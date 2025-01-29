package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class NotificaCorrelazione extends Notifica{
    private Correlazione correlazione;

    public NotificaCorrelazione(int id, LocalDate data, LocalTime ora, String descrizione, Utente utente, Correlazione correlazione) {
        super(id, data, ora, descrizione, utente);
        this.correlazione = correlazione;
    }

    public NotificaCorrelazione() {
    }

    @Override
    public String toString() {
        return "NotificaCorrelazione{" +
                super.toString() +
                "correlazione=" + correlazione +
                '}';
    }

    public Correlazione getCorrelazione() {
        return correlazione;
    }

    public void setCorrelazione(Correlazione correlazione) {
        this.correlazione = correlazione;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotificaCorrelazione that = (NotificaCorrelazione) o;
        return Objects.equals(correlazione, that.correlazione);
    }

}
