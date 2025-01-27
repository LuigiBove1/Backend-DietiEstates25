package org.example.interfaccedao;

import org.example.dto.Controfferta;

import java.util.List;

public interface ControffertaDao {
    public Controfferta getControffertaById(int id);
    public boolean saveControfferta(Controfferta controfferta);
    public boolean updateControfferta(Controfferta controfferta);
    public boolean deleteControffertaById(int id);
    public List<Controfferta> getControffertaByUtente(String utente);
    public List<Controfferta> getControffertaByAgente(String agente);

}
