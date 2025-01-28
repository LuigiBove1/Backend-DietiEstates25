package org.example.interfaccedao;

import org.example.dto.Agenzia;
import org.example.dto.Correlazione;

public interface CorrelazioneDAO{

    public Correlazione getCorrelazioneById(int id);

    public void saveCorrelazione(Correlazione correlazione);

    public void updateCorrelazione(Correlazione correlazione);

    public void deleteCorrelazioneById(int id);
}
