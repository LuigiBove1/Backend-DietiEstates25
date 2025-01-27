package org.example.interfaccedao;

import org.example.dto.Amministratore;

public interface AmministratoreDAO {

    public Amministratore getAmministratoreByNomeAdmin(String nomeAdmin);

    public boolean saveAmministratore(Amministratore amministratore);

    public boolean updateAmministratore(Amministratore amministratore);

    public boolean deleteAmministratoreByNomeAdmin(String nomeAdmin);

    public boolean loginAdmin(String nomeAdmin, String password);

    public boolean updatePasswordByNomeAdmin(String nomeAdmin, String password);

}
