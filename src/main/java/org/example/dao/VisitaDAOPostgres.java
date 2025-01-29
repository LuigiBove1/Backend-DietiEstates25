package org.example.dao;
import org.example.database.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.example.dto.Visita;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.VisitaDAO;

public class VisitaDAOPostgres implements VisitaDAO {
    DBConnection connection;

    public void saveVisita(Visita visita) throws InserimentoNonRiuscitoException{
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement("insert into visita values(?,?,?)");
            prepareStatement(visita, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento visita non riuscito");
        }  catch (Exception exception){
            System.out.println("Errore connessione al db");
        }

    }

    private void prepareStatement(Visita visita, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, visita.getEsito());
        preparedStatement.setDate(2, Date.valueOf(visita.getData()));
        preparedStatement.setTime(3, Time.valueOf(visita.getOra()));
    }

    public void updateVisita(Visita visita) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Visita SET esito = ?, data= ? ,ora= ? WHERE id = ?");
            preparedStatement.setInt(4, visita.getId());
            prepareStatement(visita, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento visita non riuscito");
        }
    }

    public void deleteVisitaById(int id) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM Visita WHERE id= ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione utente non riuscita");
        }
    }
    public Visita getVisitaById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Visita visita;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Visita WHERE visita.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean esito = resultSet.getBoolean("esito");
            LocalDate data = resultSet.getDate("data").toLocalDate();
            LocalTime ora = resultSet.getTime("ora").toLocalTime();
            visita = new Visita(id,esito, ora, data);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("visita non trovato");
        }
        return visita;
    }
    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
