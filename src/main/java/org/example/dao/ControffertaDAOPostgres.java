package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Agente;
import org.example.dto.Controfferta;
import org.example.dto.Inserzione;
import org.example.dto.Utente;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.ControffertaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControffertaDAOPostgres implements ControffertaDAO {
    public static final String NON_TROVATELBL = "non trovate";
    public static final String CONTROFFERTELBL = "Controfferte ricevute da";
    DBConnection connection;

    @Override
    public Controfferta getControffertaById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        Controfferta controfferta;
        try {
            preparedStatement = conn.prepareStatement("SELECT valore,esito,utente,agente,inserzione FROM controfferta WHERE controfferta.id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            controfferta = extractControffertaFromResultSet(id, resultSet);
            preparedStatement.close();
            conn.close();
            return controfferta;

        } catch (SQLException e) {
            throw new NonTrovatoException("Controfferta non trovata");
        }

    }

    private Controfferta extractControffertaFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Controfferta controfferta;
        int valore = resultSet.getInt("valore");
        String esito = resultSet.getString("esito");
        UtenteDAOPostgres utenteDAOPostgres = new UtenteDAOPostgres();
        AgenteDAOPostgres agenteDAOPostgres = new AgenteDAOPostgres();
        InserzioneDAOPostgres inserzioneDAOPostgres = new InserzioneDAOPostgres();
        Utente utente = utenteDAOPostgres.getUtenteByEmail(resultSet.getString("utente"));
        Agente agente = agenteDAOPostgres.getAgenteByEmail(resultSet.getString("agente"));
        Inserzione inserzione = inserzioneDAOPostgres.getInserzioneById(resultSet.getInt("inserzione"));
        controfferta = new Controfferta(id, valore, esito, utente, agente, inserzione);
        return controfferta;
    }

    @Override
    public void saveControfferta(Controfferta controfferta) throws InserimentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO controfferta(valore,esito,utente,agente,inserzione) VALUES(?,?,?,?,?)");
            prepareStatement(controfferta, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new InserimentoNonRiuscitoException("Inserimento non riuscito");
        }
    }

    private void prepareStatement(Controfferta controfferta, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, controfferta.getValore());
        preparedStatement.setString(2, controfferta.getEsito());
        preparedStatement.setString(3, controfferta.getUtente().getEmail());
        preparedStatement.setString(4, controfferta.getAgente().getEmail());
        preparedStatement.setInt(5, controfferta.getInserzione().getId());
    }

    @Override
    public void updateControfferta(Controfferta controfferta) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE controfferta SET valore=?,esito=?,utente=?,agente=?,inserzione=? WHERE controfferta.id=?");
            prepareStatement(controfferta, preparedStatement);
            preparedStatement.setInt(6, controfferta.getId());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
           throw new AggiornamentoNonRiuscitoException("Aggiornamento non riuscito");
        }
    }

    @Override
    public void deleteControffertaById(int id) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM controfferta WHERE controfferta.id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new CancellazioneNonRiuscitaException("Cancellazione non riuscita");
        }
    }

    @Override
    public List<Controfferta> getControffertaByUtente(String utente) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Controfferta> controfferte = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT id,valore,esito,utente,agente,inserzione FROM controfferta WHERE controfferta.utente = ?");
            preparedStatement.setString(1, utente);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractAllFromResultSet(resultSet, controfferte);
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new NonTrovatoException(CONTROFFERTELBL + utente + NON_TROVATELBL);
        }
        if (controfferte.isEmpty()) {
            throw new NonTrovatoException(CONTROFFERTELBL + utente + NON_TROVATELBL);
        }
        return controfferte;
    }

    @Override
    public List<Controfferta> getControffertaByAgente(String agente) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Controfferta> controfferte = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT id,valore,esito,utente,agente,inserzione FROM controfferta WHERE controfferta.agente = ?");
            preparedStatement.setString(1, agente);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractAllFromResultSet(resultSet, controfferte);
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new NonTrovatoException(CONTROFFERTELBL + agente + NON_TROVATELBL);
        }
        if (controfferte.isEmpty()) {
            throw new NonTrovatoException(CONTROFFERTELBL + agente + NON_TROVATELBL);
        }
        return controfferte;
    }

    @Override
    public void updateEsitoById(int id, String esito) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE controfferta SET esito=? WHERE controfferta.id=?");
            preparedStatement.setString(1, esito);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento esito non riuscito");
        }
    }

    private void extractAllFromResultSet(ResultSet resultSet, ArrayList<Controfferta> controfferte) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Controfferta controfferta = extractControffertaFromResultSet(id, resultSet);
            controfferte.add(controfferta);
        }
    }

    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
