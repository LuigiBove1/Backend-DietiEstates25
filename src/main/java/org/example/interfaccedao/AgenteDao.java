package org.example.interfaccedao;

import org.example.dto.Agente;

public interface AgenteDao{
    public Agente getAgenteByEmail(String email);
    public boolean saveAgente(Agente agente);
    public boolean updateAgente(Agente agente);
    public boolean deleteAgenteByEmail(String email);
}
