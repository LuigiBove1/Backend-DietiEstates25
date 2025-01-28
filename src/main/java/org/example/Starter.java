package org.example;


import org.example.dao.NotificaDAOPostgres;
import org.example.dao.UtenteDAOPostgres;

import org.example.dto.*;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.NotificaDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Starter {
    public static void main(String[] args) {
        UtenteDAOPostgres utenteDAO = new UtenteDAOPostgres();
        try{
        List<Utente> utenti = utenteDAO.getAllUtenti();
        for (Utente utente : utenti) {
            System.out.println(utente);
        }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }



        Utente giorgio = new Utente("Giorgio", "Rossi", "giorgiorossi@hotmail.com", "password");
        try {
            utenteDAO.saveUtente(giorgio);
            System.out.println("Utente inserito correttamente");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        giorgio.setPassword("nuovapassword");
        try {
            utenteDAO.updateUtente(giorgio);
            System.out.println("Utente aggiornato correttamente");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        try {
            Utente utente = utenteDAO.getUtenteByEmail("giorgiorossi@hotmail.com");
            System.out.println("\n"+utente);
        } catch (NonTrovatoException e) {
            System.out.println("Errore: " + e.getMessage());
        }


        try {
            utenteDAO.deleteUtenteByEmail("giorgiorossi@hotmail.com");
            System.out.println("Utente cancellato correttamente");

        } catch (CancellazioneNonRiuscitaException e) {
            System.out.println("Errore: " + e.getMessage());
        }


        if(utenteDAO.loginUtente("primo@utente.com", "primoutente1!"))
        {
            System.out.println("Login effettuato correttamente");
        }else{
            System.out.println("Login fallito");
        }


    }
}
