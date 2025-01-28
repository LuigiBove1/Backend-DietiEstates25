package org.example.interfaccedao;

import org.example.dto.Immobile;

public interface ImmobileDAO {
    public Immobile getImmobileById(int id);
    public void saveImmobile(Immobile immobile);
    public void updateImmobile(Immobile immobile);
    public void deleteImmobileById(int id);
}
