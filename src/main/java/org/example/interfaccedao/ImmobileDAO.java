package org.example.interfaccedao;

import org.example.dto.Immobile;

public interface ImmobileDAO {
    Immobile getImmobileById(int id);
    void saveImmobile(Immobile immobile);
    void updateImmobile(Immobile immobile);
    void deleteImmobileById(int id);
}
