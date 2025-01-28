package org.example.interfaccedao;

import org.example.dto.Visita;

public interface VisitaDAO {
    public Visita getVisitaById(int id);
    public void saveVisita(Visita visita);
    public void updateVisita(Visita visita);
    public void deleteVisitaById(int id);

}
