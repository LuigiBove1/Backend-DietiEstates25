package org.example.interfaccedao;

import org.example.dto.Utente;

public interface UtenteDAO {
    Utente getUtenteByEmail(String email);
    void saveUtente(Utente utente);
    void updateUtente(Utente utente);
    void deleteUtenteByEmail(String email);
    boolean loginUtente(String email, String password);

}
