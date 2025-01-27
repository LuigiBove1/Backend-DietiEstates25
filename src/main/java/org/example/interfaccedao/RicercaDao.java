package org.example.interfaccedao;
import org.example.dto.Ricerca;

import java.util.List;

public interface RicercaDao {
    public Ricerca getRicercaById(int id);
    public boolean saveRicerca(Ricerca ricerca);
    public boolean updateRicerca(Ricerca ricerca);
    public boolean deleteRicercaById(int id);
    public List<Ricerca> getUltimeRicercheByUtente(String utente);


}
