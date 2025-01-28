package org.example.interfaccedao;
import org.example.dto.Ricerca;

import java.util.List;

public interface RicercaDAO {
    public Ricerca getRicercaById(int id);
    public void saveRicerca(Ricerca ricerca);
    public void updateRicerca(Ricerca ricerca);
    public void deleteRicercaById(int id);
    public List<Ricerca> getUltimeRicercheByUtente(String utente);


}
