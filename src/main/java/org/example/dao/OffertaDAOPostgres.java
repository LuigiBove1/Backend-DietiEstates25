package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.OffertaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OffertaDAOPostgres implements OffertaDAO {
    DBConnection connection;
    @Override
    public Offerta getOffertaById(int id) throws NonTrovatoException {
       Connection conn=getConnection();
       PreparedStatement preparedStatement;
       Offerta offerta;
       try {
           preparedStatement=conn.prepareStatement("SELECT valore,esito,utente,agente,inserzione FROM offerta WHERE offerta.id=?");
           preparedStatement.setInt(1,id);
           ResultSet resultSet=preparedStatement.executeQuery();
           resultSet.next();
           offerta = extractOffertaFromResultSet(id, resultSet);
           preparedStatement.close();
           conn.close();
           return offerta;

       }catch(SQLException e){
           throw new NonTrovatoException("Offerta non trovata");
       }
    }

    private Offerta extractOffertaFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Offerta offerta;
        int valore= resultSet.getInt("valore");
        String esito= resultSet.getString("esito");

        UtenteDAOPostgres utenteDAOPostgres=new UtenteDAOPostgres();
        AgenteDAOPostgres agenteDAOPostgres=new AgenteDAOPostgres();
        InserzioneDAOPostgres inserzioneDAOPostgres=new InserzioneDAOPostgres();

        Utente utente=utenteDAOPostgres.getUtenteByEmail(resultSet.getString("utente"));
        Agente agente=agenteDAOPostgres.getAgenteByEmail(resultSet.getString("agente"));
        Inserzione inserzione=inserzioneDAOPostgres.getInserzioneById(resultSet.getInt("inserzione"));

        offerta=new Offerta(id,valore,esito,utente,agente,inserzione);
        return offerta;
    }

    @Override
    public void saveOfferta(Offerta offerta) throws InserimentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO offerta(valore,esito,utente,agente,inserzione) VALUES(?,?,?,?,?)");
            prepareStatement(offerta, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new InserimentoNonRiuscitoException("Inserimento offerta non riuscito");
        }
    }

    @Override
    public void updateOfferta(Offerta offerta) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE offerta SET valore=?,esito=?,utente=?,agente=?,inserzione=? WHERE offerta.id=?");
            prepareStatement(offerta, preparedStatement);
            preparedStatement.setInt(6, offerta.getId());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento offerta non riuscito");
        }
    }

    @Override
    public void deleteOffertaById(int id) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM offerta WHERE offerta.id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new CancellazioneNonRiuscitaException("Cancellazione offerta non riuscita");
        }

    }

    @Override
    public List<Offerta> getOfferteByUtente(String utente) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Offerta> offerte = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM offerta WHERE offerta.utente = ?");
            preparedStatement.setString(1, utente);
            extractAllFromQuery(preparedStatement, offerte);
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new NonTrovatoException("Offerte ricevute da"+ utente +"non trovate");
        }
        if(offerte.isEmpty()){
            throw new NonTrovatoException("Offerte ricevute da"+ utente +"non trovate");
        }
        return offerte;
    }

    @Override
    public List<Offerta> getOfferteByAgente(String agente) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Offerta> offerte = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM offerta WHERE offerta.agente = ?");
            preparedStatement.setString(1, agente);
            extractAllFromQuery(preparedStatement, offerte);
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new NonTrovatoException("Offerte ricevute da"+ agente +"non trovate");
        }
        if(offerte.isEmpty()){
            throw new NonTrovatoException("Offerte ricevute da"+ agente +"non trovate");
        }
        return offerte;
    }

    @Override
    public void updateEsitoById(int id, String esito) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE offerta SET esito = ? WHERE offerta.id = ?");
            preparedStatement.setString(1, esito);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento esito offerta non riuscito");
        }
    }


    private void extractAllFromQuery(PreparedStatement preparedStatement, ArrayList<Offerta> offerte) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Offerta offerta = extractOffertaFromResultSet(resultSet.getInt("id"), resultSet);
            offerte.add(offerta);
        }
    }

    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }


    private void prepareStatement(Offerta offerta, PreparedStatement preparedStatement) throws SQLException {
            preparedStatement.setInt(1,offerta.getValore());
            preparedStatement.setString(2,offerta.getEsito());
            preparedStatement.setString(3,offerta.getUtente().getEmail());
            preparedStatement.setString(4,offerta.getAgente().getEmail());
            preparedStatement.setInt(5,offerta.getInserzione().getId());

        }
}
