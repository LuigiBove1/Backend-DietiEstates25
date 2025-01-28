package org.example.interfaccedao;

import org.example.dto.Notifica;

import java.util.List;

public interface NotificaDAO {
    public Notifica getNotificaById(int id);
    public void saveNotifica(Notifica notifica);
    public void updateNotifica(Notifica notifica);
    public void deleteNotificaById(int id);
    public List<Notifica> getNotificheByUtente(String email);
}
