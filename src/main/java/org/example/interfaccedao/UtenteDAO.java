package org.example.interfaccedao;

import org.example.dto.Utente;

public interface UtenteDAO {
    public Utente getUtenteByEmail(String email);
    public void saveUtente(Utente utente);
    public void updateUtente(Utente utente);
    public void deleteUtenteByEmail(String email);
    public boolean loginUtente(String email, String password);

}
