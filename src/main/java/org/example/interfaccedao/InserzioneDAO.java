package org.example.interfaccedao;

import org.example.dto.Inserzione;
import org.example.dto.Ricerca;

import java.util.List;

public interface InserzioneDAO{

    Inserzione getInserzioneById(int id);

    void saveInserzione(Inserzione inserzione);

    void updateInserzione(Inserzione inserzione);

    void deleteInserzioneById(int id);

    List<Inserzione> getInserzioniByParametriMultipli(Ricerca ricerca);

    List<Inserzione> getInserzioniByRangePrezzo(int minimo, int massimo);

    List<Inserzione> getInserzioniByNumeroDiStanze(int numStanze);

    List<Inserzione> getInserzioniByClasseEnergetica(String classeEnergetica);

    List<Inserzione> getInserzioniByCitta(String citta);

    List<Inserzione> getInserzioniByIndirizzo(String indirizzo, int raggio);

    List<Inserzione> getInserzioniByRaggio(double latitude,double longitude,int raggio);

    List<Inserzione> getInserzioniByAgenzia(String agenzia);

    List<Inserzione> getInserzioniByAgente(String agente);

}
