package org.example.interfaccedao;

import org.example.dto.Amministratore;

public interface AmministratoreDAO{

    Amministratore getAmministratoreByNomeAdmin(String nomeAdmin);

    void saveAmministratore(Amministratore amministratore);

    void updateAmministratore(Amministratore amministratore);

    void deleteAmministratoreByNomeAdmin(String nomeAdmin);

    boolean loginAdmin(String nomeAdmin, String password);

    void updatePasswordByNomeAdmin(String nomeAdmin, String password);

}
