package org.example.interfaccedao;

import org.example.dto.Utente;

public interface UtenteDao{
    public Utente getUtenteByEmail(String email);
    public boolean saveUtente(Utente utente);
    public boolean updateUtente(Utente utente);
    public boolean deleteUtenteByEmail(String email);
    public boolean loginUtente(String email, String password);

}
