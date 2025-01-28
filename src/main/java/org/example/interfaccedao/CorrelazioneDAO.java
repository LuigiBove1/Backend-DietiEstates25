package org.example.interfaccedao;

import org.example.dto.Agenzia;
import org.example.dto.Correlazione;

public interface CorrelazioneDAO{

    public Correlazione getCorrelazioneById(int id);

    public boolean saveCorrelazione(Correlazione correlazione);

    public boolean updateCorrelazione(Correlazione correlazione);

    public boolean deleteCorrelazioneById(int id);
}
