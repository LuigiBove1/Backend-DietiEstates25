package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class NotificaPromozionale extends Notifica{

    public NotificaPromozionale(int id, LocalDate data, LocalTime ora, String descrizione, Utente utente) {
        super(id, data, ora, descrizione, utente);
    }

    public NotificaPromozionale() {
    }


    @Override
    public String toString() {
        return super.toString()+"NotificaPromozionale{}";
    }
}
