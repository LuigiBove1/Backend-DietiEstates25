package org.example.interfaccedao;

import org.example.dto.Visita;

public interface VisitaDAO {
    Visita getVisitaById(int id);
    void saveVisita(Visita visita);
    void updateVisita(Visita visita);
    void deleteVisitaById(int id);

}
