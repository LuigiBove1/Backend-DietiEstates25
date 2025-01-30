package org.example.interfaccedao;

import org.example.dto.Agente;

public interface AgenteDAO {
    Agente getAgenteByEmail(String email);
    void saveAgente(Agente agente);
    void updateAgente(Agente agente);
    void deleteAgenteByEmail(String email);
    boolean loginAgente(String email, String password);
}
