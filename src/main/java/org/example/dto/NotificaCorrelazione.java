package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class NotificaCorrelazione extends Visita{
    private Correlazione correlazione;

    public NotificaCorrelazione(int id, boolean esito, LocalTime ora, LocalDate data, NotificaVisita notificaVisita, Correlazione correlazione) {
        super(id, esito, ora, data, notificaVisita);
        this.correlazione = correlazione;
    }

    public NotificaCorrelazione(Correlazione correlazione) {
        this.correlazione = correlazione;
    }

    @Override
    public String toString() {
        return "NotificaCorrelazione{" +
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
    public NotificaCorrelazione() {}
}
