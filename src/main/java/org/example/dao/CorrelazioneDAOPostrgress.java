package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.CorrelazioneDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class CorrelazioneDAOPostrgress implements CorrelazioneDAO {
    DBConnection connection;


    public void saveCorrelazione(Correlazione correlazione) throws InserimentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement("insert into correlazione values(?,?,?,?)");
            prepareStatement(correlazione, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento correlazione non riuscito");
        }  catch (Exception exception){
            System.out.println("Errore connessione al db");
        }

    }

    private void prepareStatement(Correlazione correlazione, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(correlazione.getData()));
        preparedStatement.setTime(2, Time.valueOf(correlazione.getOra()));

        //Inserire inserzione e ricerca
    }

    public  Correlazione getCorrelazioneById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Correlazione correlazione;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM correlazione WHERE correlazione.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            LocalDate data = resultSet.getDate("data").toLocalDate();
            LocalTime ora = resultSet.getTime("ora").toLocalTime();
            correlazione = new Correlazione();
                  //  (id, data, ora, Inserzione, Ricerca);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Correlazione non trovato");
        }
        return correlazione;
    }

    public void updateCorrelazione(Correlazione correlazione) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Correlazione SET data = ?, ore = ?, Inserzione = ? , Ricerca = ?,WHERE id = ?");
            preparedStatement.setInt(5, correlazione.getId());
            prepareStatement(correlazione, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento Correlazione non riuscito");
        }
    }

    public void deleteCorrelazioneById(int id) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM utente WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione correlazione non riuscita");
        }
    }


    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
