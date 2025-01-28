package org.example.interfaccedao;

import org.example.dto.Controfferta;

import java.util.List;

public interface ControffertaDAO {
    public Controfferta getControffertaById(int id);
    public void saveControfferta(Controfferta controfferta);
    public void updateControfferta(Controfferta controfferta);
    public void deleteControffertaById(int id);
    public List<Controfferta> getControffertaByUtente(String utente);
    public List<Controfferta> getControffertaByAgente(String agente);

}
