package org.example.interfaccedao;

import org.example.dto.Notifica;

import java.util.List;

public interface NotificaDAO {
    Notifica getNotificaById(int id);
    void saveNotifica(Notifica notifica);
    void updateNotifica(Notifica notifica);
    void deleteNotificaById(int id);
    List<Notifica> getNotificheByUtente(String email);
}
