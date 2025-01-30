package org.example.interfaccedao;

import org.example.dto.Offerta;

import java.util.List;

public interface OffertaDAO{

    Offerta getOffertaById(int id);

    void saveOfferta(Offerta offerta);

    void updateOfferta(Offerta offerta);

    void deleteOffertaById(int id);

    List<Offerta> getOfferteByUtente(String utente);

    List<Offerta> getOfferteByAgente(String agente);

    void updateEsitoById(int id, String esito);


}
