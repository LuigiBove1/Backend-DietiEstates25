package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.NotificaDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NotificaDAOPostgres implements NotificaDAO {
    DBConnection connection;


    @Override
    public Notifica getNotificaById(int id) throws NonTrovatoException {
        connection=DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        Notifica notifica;
        try{
            preparedStatement = conn.prepareStatement(
                    "SELECT id,data,ora,descrizione,tipo,correlazione,visita,utente FROM notifica WHERE notifica.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            notifica = extractNotificaFromResultSet(id, resultSet);
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return notifica;
        } catch (SQLException e) {
            throw new NonTrovatoException("Notifica non trovata");
        }
    }

    private Notifica extractNotificaFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Notifica notifica;
        String tipoNotifica = resultSet.getString("tipo");

        if(tipoNotifica.equals("visita")){
            notifica = extractNotificaVisitaFromResultSet(id, resultSet);
        } else if (tipoNotifica.equals("correlazione")) {
            notifica = extractNotificaCorrelazioneFromResultSet(id, resultSet);
        }else{
            notifica = extractNotificaPromozionaleFromResultSet(id, resultSet);
        }
        return notifica;
    }

    private Notifica extractNotificaPromozionaleFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Notifica notifica;
        LocalDate data= resultSet.getDate("data").toLocalDate();
        LocalTime ora= resultSet.getTime("ora").toLocalTime();
        String descrizione= resultSet.getString("descrizione");
        UtenteDAOPostgres utenteDAOPostgres=new UtenteDAOPostgres();
        Utente utente=utenteDAOPostgres.getUtenteByEmail(resultSet.getString("utente"));
        notifica=new NotificaPromozionale(id,data,ora,descrizione,utente);
        return notifica;
    }

    private Notifica extractNotificaCorrelazioneFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Notifica notifica;
        LocalDate data= resultSet.getDate("data").toLocalDate();
        LocalTime ora= resultSet.getTime("ora").toLocalTime();
        String descrizione= resultSet.getString("descrizione");

        CorrelazioneDAOPostrgres correlazioneDAOPostgres=new CorrelazioneDAOPostrgres();
        Correlazione correlazione=correlazioneDAOPostgres.getCorrelazioneById(resultSet.getInt("correlazione"));

        UtenteDAOPostgres utenteDAOPostgres=new UtenteDAOPostgres();
        Utente utente=utenteDAOPostgres.getUtenteByEmail(resultSet.getString("utente"));

        notifica=new NotificaCorrelazione(id,data,ora,descrizione,utente,correlazione);
        return notifica;
    }

    private Notifica extractNotificaVisitaFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Notifica notifica;
        LocalDate data= resultSet.getDate("data").toLocalDate();
        LocalTime ora= resultSet.getTime("ora").toLocalTime();
        String descrizione= resultSet.getString("descrizione");
        VisitaDAOPostgres visitaDAOPostgres=new VisitaDAOPostgres();
        Visita visita=visitaDAOPostgres.getVisitaById(resultSet.getInt("visita"));
        UtenteDAOPostgres utenteDAOPostgres=new UtenteDAOPostgres();
        Utente utente=utenteDAOPostgres.getUtenteByEmail(resultSet.getString("utente"));
        notifica=new NotificaVisita(id,data,ora,descrizione,utente,visita);
        return notifica;
    }


    @Override
    public void saveNotifica(Notifica notifica) throws InserimentoNonRiuscitoException {
        connection=DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        try {
            insertNotifica(notifica, conn);
            conn.close();
        }catch(SQLException e){
            throw new InserimentoNonRiuscitoException("Inserimento notifica non riuscito");
        }
    }

    private void insertNotifica(Notifica notifica, Connection conn) throws SQLException {
        if (notifica instanceof NotificaPromozionale) {
            insertNotificaPromozionale(notifica, conn);
        } else if (notifica instanceof NotificaCorrelazione) {
            insertNotificaCorrelazione(notifica, conn);
        } else if (notifica instanceof NotificaVisita) {
            insertNotificaVisita(notifica, conn);
        }
    }

    private void insertNotificaVisita(Notifica notifica, Connection conn) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(
                "INSERT INTO Notifica (data,ora,descrizione,tipo,utente,visita) VALUES (?,?,?,?,?,?)");
        preparedStatement.setDate(1, Date.valueOf(notifica.getData()));
        preparedStatement.setTime(2, Time.valueOf(notifica.getOra()));
        preparedStatement.setString(3, notifica.getDescrizione());
        preparedStatement.setString(4, "visita");
        preparedStatement.setString(5, notifica.getUtente().getEmail());
        preparedStatement.setInt(6, ((NotificaVisita) notifica).getVisita().getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private void insertNotificaCorrelazione(Notifica notifica, Connection conn) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(
                "INSERT INTO Notifica (data,ora,descrizione,tipo,utente,correlazione) VALUES (?,?,?,?,?,?)");
        preparedStatement.setDate(1, Date.valueOf(notifica.getData()));
        preparedStatement.setTime(2, Time.valueOf(notifica.getOra()));
        preparedStatement.setString(3, notifica.getDescrizione());
        preparedStatement.setString(4, "correlazione");
        preparedStatement.setString(5, notifica.getUtente().getEmail());
        preparedStatement.setInt(6, ((NotificaCorrelazione) notifica).getCorrelazione().getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private void insertNotificaPromozionale(Notifica notifica, Connection conn) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(
                "INSERT INTO Notifica (data,ora,descrizione,tipo,utente) VALUES (?,?,?,?,?)");
        preparedStatement.setDate(1, Date.valueOf(notifica.getData()));
        preparedStatement.setTime(2, Time.valueOf(notifica.getOra()));
        preparedStatement.setString(3, notifica.getDescrizione());
        preparedStatement.setString(4, "promozionale");
        preparedStatement.setString(5, notifica.getUtente().getEmail());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateNotifica(Notifica notifica) throws AggiornamentoNonRiuscitoException {
            connection=DBConnection.getDBConnection();
            Connection conn = connection.getConnection();
            PreparedStatement preparedStatement;
            try{
                preparedStatement = conn.prepareStatement(
                        "UPDATE Notifica SET data = ?, ora = ?, descrizione = ? WHERE notifica.id = ?");
                preparedStatement.setDate(1, Date.valueOf(notifica.getData()));
                preparedStatement.setTime(2, Time.valueOf(notifica.getOra()));
                preparedStatement.setString(3, notifica.getDescrizione());
                preparedStatement.setInt(4, notifica.getId());
                preparedStatement.execute();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                throw new AggiornamentoNonRiuscitoException("Aggiornamento notifica non riuscito");
            }
    }



    @Override
    public void deleteNotificaById(int id) {
            connection=DBConnection.getDBConnection();
            Connection conn = connection.getConnection();
            PreparedStatement preparedStatement;
            try{
                preparedStatement = conn.prepareStatement("DELETE FROM Notifica WHERE notifica.id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                throw new CancellazioneNonRiuscitaException("Cancellazione notifica non riuscita");
            }
    }
    @Override
    public List<Notifica> getNotificheByUtente(String email) throws NonTrovatoException {
        connection=DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Notifica> notifiche=new ArrayList<>();
        try{
            preparedStatement = conn.prepareStatement("SELECT id,data,ora,descrizione,tipo,correlazione,visita,utente FROM Notifica WHERE notifica.utente = ? ORDER BY data DESC, ora DESC");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Notifica notifica = extractNotificaFromResultSet(resultSet.getInt("id"), resultSet);
                notifiche.add(notifica);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return notifiche;
        }catch(SQLException e){
            throw new NonTrovatoException("Notifiche non trovate");
        }

    }
}
