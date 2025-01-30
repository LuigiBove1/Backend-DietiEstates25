package org.example.interfaccedao;
import org.example.dto.Ricerca;

import java.util.List;

public interface RicercaDAO {
    Ricerca getRicercaById(int id);
    void saveRicerca(Ricerca ricerca);
    void updateRicerca(Ricerca ricerca);
    void deleteRicercaById(int id);
    List<Ricerca> getUltimeRicercheByUtente(String utente);


}
