package org.example.interfaccedao;

import org.example.dto.Inserzione;
import org.example.dto.Ricerca;

import java.util.List;

public interface InserzioneDAO{

    public Inserzione getInserzioneById(int id);

    public void saveInserzione(Inserzione inserzione);

    public void updateInserzione(Inserzione inserzione);

    public void deleteInserzioneById(int id);

    public List<Inserzione> getInserzioneByParametriMultipli(Ricerca ricerca);

    public List<Inserzione> getInserzioneByRangePrezzo(int minimo, int massimo);

    public List<Inserzione> getInserzioneByNumeroDiStanze(int numStanze);

    public List<Inserzione> getInserzioneByClasseEnergetica(String classeEnergetica);

    public List<Inserzione> getInserzioneByCitta(String citta);

    public List<Inserzione> getInserzioneByIndirizzo(String indirizzo, int raggio);

    public List<Inserzione> getInserzioneByAgenzia(String agenzia);

    public List<Inserzione> getInserzioneByAgente(String agente);

}
