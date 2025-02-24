package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.*;
import org.example.interfaccedao.CorrelazioneDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class CorrelazioneDAOPostrgres implements CorrelazioneDAO {
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
        }

    }

    private void prepareStatement(Correlazione correlazione, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(correlazione.getData()));
        preparedStatement.setTime(2, Time.valueOf(correlazione.getOra()));
        preparedStatement.setInt(3, correlazione.getInserzione().getId());
        preparedStatement.setInt(4, correlazione.getRicerca().getId());
    }

    public  Correlazione getCorrelazioneById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Correlazione correlazione;
        try {
            preparedStatement = conn.prepareStatement("SELECT id,data,ora,inserzione,ricerca FROM correlazione WHERE correlazione.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            correlazione = extractCorrelazioneFromResultSet(id, resultSet);
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Correlazione non trovato");
        }
        return correlazione;
    }

    private Correlazione extractCorrelazioneFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Correlazione correlazione;
        LocalDate data = resultSet.getDate("data").toLocalDate();
        LocalTime ora = resultSet.getTime("ora").toLocalTime();
        InserzioneDAOPostgres inserzioneDAOPostgres = new InserzioneDAOPostgres();
        RicercaDAOPostgres ricercaDAOPostgres = new RicercaDAOPostgres();
        Inserzione inserzione = inserzioneDAOPostgres.getInserzioneById(resultSet.getInt("Inserzione"));
        Ricerca ricerca = ricercaDAOPostgres.getRicercaById(resultSet.getInt("Ricerca"));
        correlazione = new Correlazione(id, data, ora, inserzione, ricerca);
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
