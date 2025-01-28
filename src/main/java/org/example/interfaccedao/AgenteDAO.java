package org.example.interfaccedao;

import org.example.dto.Agente;

public interface AgenteDAO {
    public Agente getAgenteByEmail(String email);
    public void saveAgente(Agente agente);
    public void updateAgente(Agente agente);
    public void deleteAgenteByEmail(String email);
    public boolean loginAgente(String email, String password);
}
