package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Agenzia;
import org.example.exceptions.*;
import org.example.interfaccedao.AgenziaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AgenziaDAOPostgres implements AgenziaDAO {
    DBConnection connection;



    public void saveAgenzia(Agenzia agenzia) throws InserimentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement("insert into agenzia values(?,?,?)");
            prepareStatement(agenzia, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento agenzia non riuscito");
        }  catch (Exception exception){
            throw new ConnessioneDataBaseException("Errore connessione al database");
        }

    }

    private void prepareStatement(Agenzia agenzia, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, agenzia.getNome());
        preparedStatement.setString(2, agenzia.getCitta());
        preparedStatement.setString(3, agenzia.getIndirizzo());
    }

    public Agenzia getAgenziaByNome(String nome) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Agenzia agenzia;
        try {
            preparedStatement = conn.prepareStatement("SELECT citta,indirizzo FROM Agenzia WHERE agenzia.nome = ?");
            preparedStatement.setString(1, nome);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String citta = resultSet.getString("citta");
            String indirizzo = resultSet.getString("indirizzo");
            agenzia = new Agenzia(nome,citta,indirizzo);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Agenzia non trovato");
        }
        return agenzia;
    }

    public void updateAgenzia(Agenzia agenzia) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Agenzia SET citta = ?, indirizzo = ? WHERE nome = ?");
            preparedStatement.setString(3, agenzia.getNome());
            preparedStatement.setString(1, agenzia.getCitta());
            preparedStatement.setString(2, agenzia.getIndirizzo());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento agenzia non riuscito");
        }
    }

    public void deleteAgenziaByNome(String nome) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM Agenzia WHERE nome = ?");
            preparedStatement.setString(1, nome);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione Agenzia non riuscita");
        }
    }

    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
