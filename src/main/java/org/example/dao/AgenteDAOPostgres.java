package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Agente;
import org.example.dto.Agenzia;
import org.example.exceptions.*;
import org.example.interfaccedao.AgenteDAO;
import org.example.interfaccedao.AgenziaDAO;
import org.example.utils.CredentialCheckerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AgenteDAOPostgres implements AgenteDAO {
    DBConnection connection;

    public void saveAgente(Agente agente) throws InserimentoNonRiuscitoException
    {
        if(CredentialCheckerUtils.checkCredentials(agente.getEmail(), agente.getPassword() )) {
            Connection conn = getConnection();
            PreparedStatement preparedStatement;

            try {
                preparedStatement = conn.prepareStatement("insert into agente values(?,?,?,?,?)");
                prepareStatement(agente, preparedStatement);

                preparedStatement.execute();
                preparedStatement.close();
                conn.close();
            } catch (SQLException throwables) {
                throw new InserimentoNonRiuscitoException("Inserimento agente non riuscito");
            } catch (Exception exception) {
                throw new ConnessioneDataBaseException("Errore connessione al database");
            }
        }else {
            throw new InserimentoNonRiuscitoException("Inserimento agente non riuscito: credenziali non valide");
        }

    }

    private void prepareStatement(Agente agente, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, agente.getNome());
        preparedStatement.setString(2, agente.getCognome());
        preparedStatement.setString(3, agente.getEmail());
        preparedStatement.setString(4, agente.getPassword());
        preparedStatement.setString(5, agente.getAgenzia().getNome());
    }

    public Agente getAgenteByEmail(String email) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Agente agente;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Agente WHERE agente.email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String nome = resultSet.getString("nome");
            String cognome = resultSet.getString("cognome");
            String password = resultSet.getString("password");
            String nomeAgenzia = resultSet.getString("agenzia");
            AgenziaDAO agenziaDAO= new AgenziaDAOPostgres();
            Agenzia agenzia = agenziaDAO.getAgenziaByNome(nomeAgenzia);
            agente = new Agente(nome,cognome,email,password,agenzia);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Agente non trovato");
        }
        return agente;
    }


    public void updateAgente(Agente agente) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Agente SET nome = ?, cognome = ?, password = ?, agenzia = ? WHERE email = ?");
            prepareStatementUpdate(agente, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento Agente non riuscito");
        }
    }
    private void prepareStatementUpdate(Agente agente, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, agente.getNome());
        preparedStatement.setString(2, agente.getCognome());
        preparedStatement.setString(3, agente.getPassword());
        preparedStatement.setString(4, agente.getAgenzia().getNome());
        preparedStatement.setString(5, agente.getEmail());
    }

    public void deleteAgenteByEmail(String email) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM Agente WHERE email = ?");
            preparedStatement.setString(1, email);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione agente non riuscita");
        }
    }

    @Override
    public boolean loginAgente(String email, String password) {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Agente WHERE agente.email = ? AND agente.password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return true;
            }
        } catch (SQLException throwables) {
            return false;
        }

        return false;
    }

    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
