package org.example.interfaccedao;

import org.example.dto.Correlazione;

public interface CorrelazioneDAO{

    Correlazione getCorrelazioneById(int id);

    void saveCorrelazione(Correlazione correlazione);

    void updateCorrelazione(Correlazione correlazione);

    void deleteCorrelazioneById(int id);
}
