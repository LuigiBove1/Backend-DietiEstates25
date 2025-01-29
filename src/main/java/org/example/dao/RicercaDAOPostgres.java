package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.*;
import org.example.interfaccedao.AgenteDAO;
import org.example.interfaccedao.AgenziaDAO;
import org.example.interfaccedao.ImmobileDAO;
import org.example.interfaccedao.UtenteDAO;
import org.example.utils.CredentialCheckerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class RicercaDAOPostgres {
    DBConnection connection;


    public void saveRicerca(Ricerca ricerca) throws InserimentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement("insert into ricerca values(?,?,?,?,?,?,?,?)");
            preparedStatement(ricerca, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        }catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento agente non riuscito");
        }catch (Exception exception) {
            throw new ConnessioneDataBaseException("Errore connessione al database");
        }
            throw new InserimentoNonRiuscitoException("Inserimento agente non riuscito: credenziali non valide");

    }

    public Ricerca getRicercaById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Ricerca ricerca;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Ricerca WHERE ricerca.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int prezzoMinimo = resultSet.getInt("prezzoMinimo");
            int prezzoMassimo = resultSet.getInt("prezzoMassimo");
            String tipologia = resultSet.getString("tipologia");
            String citta = resultSet.getString("citta");
            int numeroStanze = resultSet.getInt("numeroStanze");
            String classeEnergetica = resultSet.getString("classeEnergetica");
            String utenteRicercato = resultSet.getString("utente");
            UtenteDAO utenteDAO= new UtenteDAOPostgres();
            Utente utente = utenteDAO.getUtenteByEmail(utenteRicercato);
            ricerca = new Ricerca(id, prezzoMinimo, prezzoMassimo, tipologia, citta, numeroStanze, classeEnergetica, utente);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Ricerca non trovata");
        }
        return ricerca;
    }

    public void updateRicerca(Ricerca ricerca) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Ricerca SET id=?, prezzoMinimo=?, prezzoMassimo=?, tipologia=?, citta=?, numeroStanze=?, classeEnergetica=?, utente=? WHERE id=?");
            preparedStatement(ricerca, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento ricerca non riuscito");
        }
    }

    public void deleteRicercaById(int id) {
        connection=DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        try{
            preparedStatement = conn.prepareStatement("DELETE FROM Ricerca WHERE ricerca.id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new CancellazioneNonRiuscitaException("Cancellazione ricerca non riuscita");
        }
    }

    private Ricerca extractRicercaFromResultSet(int id, ResultSet resultSet) throws SQLException {
        Ricerca ricerca;
        UtenteDAO utenteDAO = new UtenteDAOPostgres();
        int prezzoMinimo=resultSet.getInt("prezzoMinimo");
        int prezzoMassimo=resultSet.getInt("prezzoMassimo");
        String tipologia=resultSet.getString("tipologia");
        String citta=resultSet.getString("citta");
        int numeroStanze=resultSet.getInt("numeroStanze");
        String classeEnergetica=resultSet.getString("classeEnergetica");
        Utente utente=utenteDAO.getUtenteByEmail(resultSet.getString("utente"));
        ricerca = new Ricerca(id, prezzoMinimo, prezzoMassimo, tipologia, citta, numeroStanze, classeEnergetica, utente);
        return ricerca;
    }

    public List<Ricerca> getUltimeRicercheByUtente(String utente) throws NonTrovatoException {
        connection = DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        List<Ricerca> ricerche = new ArrayList<>(5);
        try{
            preparedStatement=conn.prepareStatement("SELECT ricerca.id, prezzoMinimo, prezzoMassimo, tipologia, citta, numeroStanze, classeEnergetica, utente " +
                    "FROM Ricerca WHERE utente = ?");
            preparedStatement.setString(1, utente);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                ricerche.add(extractRicercaFromResultSet(id, resultSet));
            }
            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new NonTrovatoException("Nessuna ricerca trovata in base all'utente");
        }
        if (ricerche.isEmpty()) {
            throw new NonTrovatoException("Nessuna ricerca trovata in base all'utente");
        }
        return ricerche;
    }

    private void preparedStatement(Ricerca ricerca, PreparedStatement preparedStatement) throws SQLException
    {
        preparedStatement.setInt(1, ricerca.getId());
        preparedStatement.setInt(2, ricerca.getPrezzoMinimo());
        preparedStatement.setInt(3, ricerca.getPrezzoMassimo());
        preparedStatement.setString(4, ricerca.getTipologia());
        preparedStatement.setString(5, ricerca.getCitta());
        preparedStatement.setInt(6, ricerca.getNumeroStanze());
        preparedStatement.setString(7, ricerca.getClasseEnergetica());
        preparedStatement.setString(8, ricerca.getUtente().getEmail());
    }

    private Connection getConnection () {
            connection = DBConnection.getDBConnection();
            return connection.getConnection();
        }

}