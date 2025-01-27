package org.example.interfaccedao;

import org.example.dto.Notifica;

import java.util.List;

public interface NotificaDao {
    public Notifica getNotificaById(int id);
    public boolean saveNotifica(Notifica notifica);
    public boolean updateNotifica(Notifica notifica);
    public boolean deleteNotificaById(int id);
    public List<Notifica> getNotificheByUtente(String utente);
}
