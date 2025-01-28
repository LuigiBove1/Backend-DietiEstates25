package org.example.interfaccedao;

import org.example.dto.Visita;

public interface VisitaDao {
    public Visita getVisitaById(int id);
    public boolean saveVisita(Visita visita);
    public boolean updateVisita(Visita visita);
    public boolean deleteVisitaById(int id);

}
