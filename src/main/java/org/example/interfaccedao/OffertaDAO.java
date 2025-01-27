package org.example.interfaccedao;

import org.example.dto.Offerta;

import java.util.List;

public interface OffertaDAO {

    public Offerta getOffertaById(int id);

    public boolean saveOfferta(Offerta offerta);

    public boolean updateOfferta(Offerta offerta);

    public boolean deleteOffertaById(int id);

    public List<Offerta> getOfferteByUtente(String utente);

    public List<Offerta> getOfferteByAgente(String agente);

}
