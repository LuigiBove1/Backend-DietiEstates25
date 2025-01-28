package org.example.dao;
import org.example.database.DBConnection;
import org.example.dto.Utente;
import org.example.exceptions.*;

import org.example.interfaccedao.UtenteDAO;
import org.example.utils.CredentialCheckerUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOPostgres implements UtenteDAO {
    DBConnection connection;



    public void saveUtente(Utente utente) throws InserimentoNonRiuscitoException
    {
        if(CredentialCheckerUtils.checkCredentials(utente.getEmail(), utente.getPassword() ))
        {
            Connection conn = getConnection();
            PreparedStatement preparedStatement;

            try {
                preparedStatement = conn.prepareStatement("insert into utente values(?,?,?,?)");
                prepareStatement(utente, preparedStatement);

                preparedStatement.execute();
                preparedStatement.close();
                conn.close();
            } catch (SQLException throwables) {
                throw new InserimentoNonRiuscitoException("Inserimento utente non riuscito");
            }  catch (Exception exception){
                System.out.println("Errore connessione al db");
            }
        }else{
            throw new CredenzialiNonValideException("Credenziali inserite non valide");
        }

    }

    private void prepareStatement(Utente utente, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, utente.getNome());
        preparedStatement.setString(2, utente.getCognome());
        preparedStatement.setString(3, utente.getEmail());
        preparedStatement.setString(4, utente.getPassword());
    }

    public Utente getUtenteByEmail(String email) throws NonTrovatoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Utente utente;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Utente WHERE utente.email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String nome = resultSet.getString("nome");
            String cognome = resultSet.getString("cognome");
            String password = resultSet.getString("password");
            utente = new Utente(nome,cognome,email,password);

            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new NonTrovatoException("Utente non trovato");
        }
        return utente;
    }

    public void updateUtente(Utente utente) throws AggiornamentoNonRiuscitoException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("UPDATE Utente SET nome = ?, cognome = ?, password = ? WHERE email = ?");
            prepareStatement(utente, preparedStatement);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throw new AggiornamentoNonRiuscitoException("Aggiornamento utente non riuscito");
        }
    }

    public void deleteUtenteByEmail(String email) throws CancellazioneNonRiuscitaException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM utente WHERE email = ?");
            preparedStatement.setString(1, email);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            throw new CancellazioneNonRiuscitaException("Cancellazione utente non riuscita");
        }
    }

    public boolean loginUtente(String email, String password){
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM Utente WHERE utente.email = ? AND utente.password = ?");
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


    public List<Utente> getAllUtenti() throws NonTrovatoException
    {
        connection= DBConnection.getDBConnection();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement;
        ArrayList<Utente> utenti = new ArrayList<>();
        ResultSet resultSet;
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM utente");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String password = resultSet.getString("password");
                Utente utente = new Utente(nome,cognome,email,password);
                utenti.add(utente);
            }
            preparedStatement.close();
            conn.close();

        } catch (SQLException throwables) {
            System.out.println("Errore esecuzione query get all utenti");
        }
        if(utenti.isEmpty())
        {
            throw new NonTrovatoException("Nessun utente trovato");
            //lancia exception al controller
        }
        return utenti;


    }
}
