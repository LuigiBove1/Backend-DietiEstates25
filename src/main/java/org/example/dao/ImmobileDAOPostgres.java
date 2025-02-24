package org.example.dao;

import org.example.interfaccedao.ImmobileDAO;
import org.example.utils.GeoApifyUtils;
import org.example.database.DBConnection;
import org.example.dto.Immobile;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;

import java.sql.*;
import java.io.IOException;

public class ImmobileDAOPostgres implements ImmobileDAO {
    DBConnection connection;

    public Immobile getImmobileById(int id) {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        Immobile immobile;
        PreparedStatement preparedStatement;
        try{
            preparedStatement = conn.prepareStatement(
                    "SELECT indirizzo,dimensione,numeroStanze,ascensore,classeEnergetica,piano,citta,codicePostale,latitudine, longitudine, puntiDiInteresse FROM Immobile WHERE immobile.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            immobile=extractImmobileFromResultSet(id, resultSet);
            preparedStatement.close();

            return immobile;

        }catch(SQLException e) {
            throw new InserimentoNonRiuscitoException("Nessun immobile trovato in base all'id");
        }

    }

    private Immobile extractImmobileFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Immobile immobile;

        String indirizzo = resultSet.getString("indirizzo");
        int dimensione = resultSet.getInt("dimensione");
        int numeroStanze = resultSet.getInt("numeroStanze");
        boolean ascensore = resultSet.getBoolean("ascensore");
        String classeEnergetica = resultSet.getString("classeEnergetica");
        int piano = resultSet.getInt("piano");
        String citta = resultSet.getString("citta");
        String codicePostale = resultSet.getString("codicePostale");
        double latitudine = resultSet.getDouble("latitudine");
        double longitudine = resultSet.getDouble("longitudine");
        String puntiDiInteresse = resultSet.getString("puntiDiInteresse");

        immobile = new Immobile(id, indirizzo, dimensione, numeroStanze, ascensore, classeEnergetica, piano, citta, codicePostale, longitudine, latitudine, puntiDiInteresse);
        return immobile;
    }

    public void saveImmobile(Immobile immobile) throws InserimentoNonRiuscitoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO public.immobile" +
                            "(indirizzo, dimensione, numerostanze, ascensore, classeenergetica, piano, citta, codicepostale, latitudine, longitudine, puntidiinteresse) " +
                            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            prepareStatementUpdateCoordinates(immobile, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento immobile non riuscito");
        }

    }
    public void updateImmobile(Immobile immobile) {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {
            aggiornaCoordinate(immobile);
            preparedStatement = conn.prepareStatement(
                    "UPDATE immobile SET indirizzo = ?, dimensione = ?, numerostanze = ?, ascensore = ?, classeenergetica = ?, piano = ?, citta = ?, codicepostale = ?, latitudine = ?, longitudine = ?, puntidiinteresse = ? WHERE immobile.id = ?");
            prepareStatementUpdateCoordinates(immobile, preparedStatement);
            preparedStatement.setInt(12, immobile.getId());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento immobile non riuscito");
        }
    }

    public void deleteImmobileById(int id) throws CancellazioneNonRiuscitaException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM immobile WHERE immobile.id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione immobile non riuscita");
        }
    }
    @Override
    public int getIdByImmobile(Immobile immobile) {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(
                    "SELECT immobile.id FROM immobile" +
                            " WHERE immobile.indirizzo = ? AND immobile.dimensione = ? AND immobile.numerostanze = ? " +
                            "AND immobile.ascensore = ? AND immobile.classeenergetica = ? AND immobile.piano = ? " +
                            "AND immobile.citta = ? AND immobile.codicepostale = ? AND immobile.latitudine = ? " +
                            "AND immobile.longitudine = ? AND immobile.puntidiinteresse = ?");
            prepareStatementGetId(immobile, preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id=resultSet.getInt("id");
            preparedStatement.close();

            return id;
        } catch (SQLException throwables) {

            throw new NonTrovatoException("Nessun immobile trovato");
        }
    }

    private void prepareStatementGetId(Immobile immobile, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, immobile.getIndirizzo());
        preparedStatement.setInt(2, immobile.getDimensione());
        preparedStatement.setInt(3, immobile.getNumeroStanze());
        preparedStatement.setBoolean(4, immobile.isAscensore());
        preparedStatement.setString(5, immobile.getClasseEnergetica());
        preparedStatement.setInt(6, immobile.getPiano());
        preparedStatement.setString(7, immobile.getCitta());
        preparedStatement.setString(8, immobile.getCodicePostale());
        preparedStatement.setDouble(9, immobile.getLongitude());
        preparedStatement.setDouble(10, immobile.getLatitude());
        preparedStatement.setString(11, immobile.getPuntiDiInteresse());
    }

    private void prepareStatementUpdateCoordinates(Immobile immobile, PreparedStatement preparedStatement) throws SQLException {
        aggiornaCoordinate(immobile);
        prepareStatementGetId(immobile, preparedStatement);
    }

    private void aggiornaCoordinate(Immobile immobile) {
        double[] coordinates = new double[2];
        try {
            coordinates = GeoApifyUtils.addressToCoordinatesDouble(immobile.getIndirizzo() + ", "+immobile.getCodicePostale()+" "+ immobile.getCitta());
            immobile.setLatitude(coordinates[1]);
            immobile.setLongitude(coordinates[0]);
            String pointsOfInterest = GeoApifyUtils.getPOIFromCoordinates(immobile.getLatitude(), immobile.getLongitude());
            immobile.setPuntiDiInteresse(pointsOfInterest);
        } catch (IOException | InterruptedException e) {
            coordinates[0] = 0;
            coordinates[1] = 0;
            Thread.currentThread().interrupt();
        }
    }
}
