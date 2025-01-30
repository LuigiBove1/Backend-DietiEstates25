package org.example.interfaccedao;

import org.example.dto.Controfferta;

import java.util.List;

public interface ControffertaDAO {
    Controfferta getControffertaById(int id);
    void saveControfferta(Controfferta controfferta);
    void updateControfferta(Controfferta controfferta);
    void deleteControffertaById(int id);
    List<Controfferta> getControffertaByUtente(String utente);
    List<Controfferta> getControffertaByAgente(String agente);
    void updateEsitoById(int id, String esito);

}
