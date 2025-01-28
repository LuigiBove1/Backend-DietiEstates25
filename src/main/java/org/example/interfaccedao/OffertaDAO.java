package org.example.interfaccedao;

import org.example.dto.Offerta;

import java.util.List;

public interface OffertaDAO{

    public Offerta getOffertaById(int id);

    public void saveOfferta(Offerta offerta);

    public void updateOfferta(Offerta offerta);

    public void deleteOffertaById(int id);

    public List<Offerta> getOfferteByUtente(String utente);

    public List<Offerta> getOfferteByAgente(String agente);

}
