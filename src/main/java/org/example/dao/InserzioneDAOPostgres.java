package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Agente;
import org.example.dto.Immobile;
import org.example.dto.Inserzione;
import org.example.dto.Ricerca;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.AgenteDAO;
import org.example.interfaccedao.ImmobileDAO;
import org.example.interfaccedao.InserzioneDAO;
import org.example.utils.GeoApifyUtils;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InserzioneDAOPostgres implements InserzioneDAO {
    DBConnection connection;

    public Inserzione getInserzioneById(int id) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        Inserzione inserzione;
        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT titolo,descrizione,prezzo,foto,tipologia,immobile,agente FROM Inserzione WHERE inserzione.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            inserzione = extractInserzioneFromResultSet(id, resultSet);
            preparedStatement.close();
            conn.close();
            return inserzione;
        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'id");
        }
    }

    private Inserzione extractInserzioneFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Inserzione inserzione;
        ImmobileDAO immobileDAO = new ImmobileDAOPostgres();
        String titolo=resultSet.getString("titolo");
        String descrizione=resultSet.getString("descrizione");
        int prezzo=resultSet.getInt("prezzo");
        String foto=resultSet.getString("foto");
        String tipologia=resultSet.getString("tipologia");
        Immobile immobile=immobileDAO.getImmobileById(resultSet.getInt("immobile"));
        AgenteDAO agenteDAO = new AgenteDAOPostgres();

        Agente agente=agenteDAO.getAgenteByEmail(resultSet.getString("agente"));
        inserzione = new Inserzione(id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente);
        return inserzione;
    }

    public void saveInserzione(Inserzione inserzione) throws InserimentoNonRiuscitoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {

            preparedStatement = conn.prepareStatement(
                    "insert into inserzione (prezzo,titolo,foto,tipologia,descrizione,agente,immobile) values(?,?,?,?,?,?,?)");
            prepareStatementSave(inserzione, preparedStatement);
            conn=connection.getConnection();
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {

            throw new InserimentoNonRiuscitoException("Inserimento inserzione non riuscito");
        }
    }

    public void updateInserzione(Inserzione inserzione){
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Inserzione SET prezzo = ?, titolo = ?, foto = ?, tipologia = ?, descrizione = ?, agente = ?, immobile = ? WHERE inserzione.id = ?");
            prepareStatementUpdate(inserzione, preparedStatement);
            preparedStatement.setInt(8, inserzione.getId());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new InserimentoNonRiuscitoException("Aggiornamento inserzione non riuscito");
        }
    }

    public void deleteInserzioneById(int id) {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM Inserzione WHERE inserzione.id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new CancellazioneNonRiuscitaException("Cancellazione inserzione non riuscita");
        }
    }

    @Override
    public List<Inserzione> getInserzioniByParametriMultipli(Ricerca ricerca) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
//           RicercaDAO ricercaDAO = new RicercaDAOPostgres();
//           ricercaDAO.saveRicerca(ricerca);
             preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                     "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id " +
                     "WHERE immobile.citta = ? AND immobile.classeenergetica = ? " +
                     "AND immobile.numerostanze = ? AND inserzione.prezzo >= ? AND inserzione.prezzo <= ? " +
                     "AND inserzione.tipologia = ?");
                preparedStatement.setString(1, ricerca.getCitta());
                preparedStatement.setString(2, ricerca.getClasseEnergetica());
                preparedStatement.setInt(3, ricerca.getNumeroStanze());
                preparedStatement.setInt(4, ricerca.getPrezzoMinimo());
                preparedStatement.setInt(5, ricerca.getPrezzoMassimo());
                preparedStatement.setString(6, ricerca.getTipologia());
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
                }
                preparedStatement.close();
                conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base ai parametri");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base ai parametri");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByRangePrezzo(int minimo, int massimo) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id " +
                    "WHERE inserzione.prezzo >= ? AND inserzione.prezzo <= ? ");
            preparedStatement.setInt(1, minimo);
            preparedStatement.setInt(2, massimo);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base al range di prezzo");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base al range di prezzo");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByNumeroDiStanze(int numStanze) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id " +
                    "WHERE immobile.numerostanze = ? ");
            preparedStatement.setInt(1, numStanze);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base al numero di stanze");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base al numero di stanze");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByClasseEnergetica(String classeEnergetica) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id " +
                    "WHERE immobile.classeenergetica = ? ");
            preparedStatement.setString(1, classeEnergetica);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base alla classe energetica");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base alla classe energetica");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByCitta(String citta) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id " +
                    "WHERE immobile.citta = ? ");
            preparedStatement.setString(1, citta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base alla città");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base ala città");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByIndirizzo(String indirizzo, int raggio) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        List<Inserzione> inserzioniNelRaggio;
        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT id,titolo, descrizione, prezzo, foto, tipologia, immobile, agente FROM inserzione");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            inserzioniNelRaggio = filtraInserzioniPerIndirizzoRaggio(inserzioni, indirizzo, raggio);
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'indirizzo e al raggio");
        }
        if(inserzioniNelRaggio.isEmpty()){
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'indirizzo e al raggio");
        }
        return inserzioniNelRaggio;
    }

    @Override
    public List<Inserzione> getInserzioniByRaggio(double latitude, double longitude, int raggio) {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        List<Inserzione> inserzioniNelRaggio;
        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT id,titolo, descrizione, prezzo, foto, tipologia, immobile, agente FROM inserzione");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            inserzioniNelRaggio = filtraInserzioni(inserzioni,raggio, latitude,longitude);
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'indirizzo e al raggio");
        }
        if(inserzioniNelRaggio.isEmpty()){
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'indirizzo e al raggio");
        }
        return inserzioniNelRaggio;
    }



    private List<Inserzione> filtraInserzioniPerIndirizzoRaggio(List<Inserzione> inserzioni, String indirizzo, int raggio) {
        List<Inserzione> inserzioniNelRaggio;
        double[] coordinates;
        try{
            coordinates= GeoApifyUtils.addressToCoordinatesDouble(indirizzo);
        }catch(IOException | InterruptedException e){
            throw new NonTrovatoException("Coordinate non trovate");
        }
        double lat1=coordinates[0];
        double lon1=coordinates[1];
        inserzioniNelRaggio=filtraInserzioni(inserzioni, raggio, lat1, lon1);
        return inserzioniNelRaggio;
    }

    private List<Inserzione> filtraInserzioni(List<Inserzione> inserzioni, int raggio, double lat1, double lon1) {
        List<Inserzione> inserzioniNelRaggio = new ArrayList<>();
        for (Inserzione inserzione : inserzioni) {
            double distanza= GeoApifyUtils.haversine(lat1, lon1,inserzione.getImmobile().getLatitude(),inserzione.getImmobile().getLongitude());
            if (distanza<= raggio) {
                inserzioniNelRaggio.add(inserzione);
            }
        }
        return inserzioniNelRaggio;
    }

    @Override
    public List<Inserzione> getInserzioniByAgenzia(String agenzia) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id INNER JOIN agente ON inserzione.agente = agente.email " +
                    "WHERE agente.agenzia = ? ");
            preparedStatement.setString(1, agenzia);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'agenzia");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'agenzia");
        }
        return inserzioni;
    }

    @Override
    public List<Inserzione> getInserzioniByAgente(String agente) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Inserzione> inserzioni = new ArrayList<>();
        try{
            preparedStatement=conn.prepareStatement("SELECT inserzione.id, titolo, descrizione, prezzo, foto, tipologia, immobile, agente " +
                    "FROM Inserzione INNER JOIN immobile ON inserzione.immobile = immobile.id INNER JOIN agente ON inserzione.agente = agente.email " +
                    "WHERE agente.email = ? ");
            preparedStatement.setString(1, agente);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                inserzioni.add(extractInserzioneFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'agente");
        }
        if (inserzioni.isEmpty()) {
            throw new NonTrovatoException("Nessuna inserzione trovata in base all'agente");
        }
        return inserzioni;
    }
    private void prepareStatementSave(Inserzione inserzione, PreparedStatement preparedStatement) throws SQLException {
        prepareStatement(inserzione, preparedStatement);
        ImmobileDAOPostgres immobileDAO = new ImmobileDAOPostgres();
        immobileDAO.saveImmobile(inserzione.getImmobile());
        int idImmobile = immobileDAO.getIdByImmobile(inserzione.getImmobile());
        preparedStatement.setInt(7, idImmobile);


    }
    private void prepareStatement(Inserzione inserzione, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, inserzione.getPrezzo());
        preparedStatement.setString(2, inserzione.getTitolo());
        preparedStatement.setString(3, inserzione.getFoto());
        preparedStatement.setString(4, inserzione.getTipologia());
        preparedStatement.setString(5, inserzione.getDescrizione());
        preparedStatement.setString(6, inserzione.getAgente().getEmail());
    }
    private void prepareStatementUpdate(Inserzione inserzione, PreparedStatement preparedStatement) throws SQLException {
        prepareStatement(inserzione, preparedStatement);
        ImmobileDAO immobileDAO = new ImmobileDAOPostgres();
        immobileDAO.updateImmobile(inserzione.getImmobile());
        preparedStatement.setInt(7, inserzione.getImmobile().getId());


    }
}