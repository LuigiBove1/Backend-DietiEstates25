package org.example.interfaccedao;

import org.example.dto.Amministratore;

public interface AmministratoreDAO{

    public Amministratore getAmministratoreByNomeAdmin(String nomeAdmin);

    public void saveAmministratore(Amministratore amministratore);

    public void updateAmministratore(Amministratore amministratore);

    public void deleteAmministratoreByNomeAdmin(String nomeAdmin);

    public boolean loginAdmin(String nomeAdmin, String password);

    public void updatePasswordByNomeAdmin(String nomeAdmin, String password);

}
