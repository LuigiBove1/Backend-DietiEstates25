package org.example.interfaccedao;

import org.example.dto.Inserzione;
import org.example.dto.Ricerca;

import java.util.List;

public interface InserzioneDAO{

    public Inserzione getInserzioneById(int id);

    public void saveInserzione(Inserzione inserzione);

    public void updateInserzione(Inserzione inserzione);

    public void deleteInserzioneById(int id);

    public List<Inserzione> getInserzioniByParametriMultipli(Ricerca ricerca);

    public List<Inserzione> getInserzioniByRangePrezzo(int minimo, int massimo);

    public List<Inserzione> getInserzioniByNumeroDiStanze(int numStanze);

    public List<Inserzione> getInserzioniByClasseEnergetica(String classeEnergetica);

    public List<Inserzione> getInserzioniByCitta(String citta);

    public List<Inserzione> getInserzioniByIndirizzo(String indirizzo, int raggio);

    public List<Inserzione> getInserzioniByRaggio(double latitude,double longitude,int raggio);

    public List<Inserzione> getInserzioniByAgenzia(String agenzia);

    public List<Inserzione> getInserzioniByAgente(String agente);

}
