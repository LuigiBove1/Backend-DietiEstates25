package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.*;
import org.example.exceptions.*;
import org.example.interfaccedao.RicercaDAO;
import org.example.interfaccedao.UtenteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RicercaDAOPostgres implements RicercaDAO {
    DBConnection connection;


    public void saveRicerca(Ricerca ricerca) throws InserimentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement(
                    "insert into ricerca (prezzominimo,prezzomassimo,tipologia,citta,numerostanze,classeenergetica,utente) " +
                            "values(?,?,?,?,?,?,?)");
            prepareStatement(ricerca, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
        }catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("Inserimento ricerca non riuscito");
        }catch (Exception exception) {
            throw new ConnessioneDataBaseException("Errore connessione al database");
        }


    }

    public Ricerca getRicercaById(int id) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Ricerca ricerca;
        try {
            preparedStatement = conn.prepareStatement("SELECT id,prezzomassimo,prezzominimo,tipologia,citta,classeenergetica,utente" +
                    " FROM Ricerca WHERE ricerca.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            ricerca = extractRicercaFromResultSet(id, resultSet);

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
            preparedStatement = conn.prepareStatement("UPDATE Ricerca SET prezzoMinimo=?, prezzoMassimo=?, tipologia=?, citta=?, numeroStanze=?, classeEnergetica=?, utente=? WHERE id=?");
            prepareStatement(ricerca, preparedStatement);
            preparedStatement.setInt(8, ricerca.getId());
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
                    "FROM Ricerca WHERE utente = ? ORDER BY ricerca.id DESC LIMIT 5");
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

    private void prepareStatement(Ricerca ricerca, PreparedStatement preparedStatement) throws SQLException
    {
        preparedStatement.setInt(1, ricerca.getPrezzoMinimo());
        preparedStatement.setInt(2, ricerca.getPrezzoMassimo());
        preparedStatement.setString(3, ricerca.getTipologia());
        preparedStatement.setString(4, ricerca.getCitta());
        preparedStatement.setInt(5, ricerca.getNumeroStanze());
        preparedStatement.setString(6, ricerca.getClasseEnergetica());
        preparedStatement.setString(7, ricerca.getUtente().getEmail());
    }

    private Connection getConnection () {
            connection = DBConnection.getDBConnection();
            return connection.getConnection();
        }

}