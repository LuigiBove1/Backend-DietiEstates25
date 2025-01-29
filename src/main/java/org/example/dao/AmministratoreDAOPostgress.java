package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Agenzia;
import org.example.dto.Amministratore;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.interfaccedao.AgenziaDAO;
import org.example.interfaccedao.AmministratoreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAOPostgress{
    DBConnection connection;

    public void saveAmministratore(Amministratore amministratore) throws InserimentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try{
            preparedStatement = conn.prepareStatement("insert into amministratore values(?,?,?)");
            preparedStatement(amministratore, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();


        }catch (SQLException throwables) {
            throw new InserimentoNonRiuscitoException("inserimento amministratore non riuscito");
        }catch (Exception exception) {
            System.out.println("errore connessione al db");
        }
    }

    private void preparedStatement(Amministratore amministratore, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, amministratore.getNomeAdmin());
        preparedStatement.setString(2, amministratore.getPassword());
        preparedStatement.setString(3, amministratore.getAgenzia().getNome());
    }

    public Amministratore getAmmministratoreByNomeAdmin(String nomeAdmin) throws NonTrovatoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        Amministratore amministratore;
        ResultSet resultSet;

        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM amministratore WHERE amministratore.nomeAdmin=?");
            preparedStatement.setString(1, nomeAdmin);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String password = resultSet.getString("password");
            String nomeAgenzia = resultSet.getString("nomeAgenzia");
            AgenziaDAO agenziaDAO = new AgenziaDAOPostgress();
            Agenzia agenzia = agenziaDAO.getAgenziaByNome(nomeAgenzia);
            amministratore = new Amministratore(nomeAdmin, password, agenzia);

            preparedStatement.close();
            conn.close();
        }catch (SQLException throwables) {
            throw new NonTrovatoException("amministratore non trovato");
        }
        return amministratore;
    }

    public void updateAmministratore(Amministratore amministratore) throws AggiornamentoNonRiuscitoException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try{
            preparedStatement = conn.prepareStatement("UPDATE amministratore SET password=?,agenzia=? WHERE nomeAdmin=?");
            preparedStatement(amministratore, preparedStatement);

            preparedStatement.close();
            conn.close();
        }catch (SQLException throwables){
            throw new AggiornamentoNonRiuscitoException("aggiornamento amministratore non riuscito");
        }
    }
    
    public void deleteAmministratoreByNomeAdmin(String nomeAdmin) throws CancellazioneNonRiuscitaException
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;

        try{
            preparedStatement = conn.prepareStatement("DELETE FROM amministratore WHERE amministratore.nomeAdmin=?");
            preparedStatement.setString(1, nomeAdmin);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        }catch(SQLException throwables){
            throw new CancellazioneNonRiuscitaException("cancellazione  amministratore non riuscita");
        }
    }

    public boolean loginAdmin(String nomeAdmin, String password)
    {
        Connection conn = getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try{
            preparedStatement = conn.prepareStatement("SELECT * FROM amministratore WHERE amministratore.nomeAdmin=? AND password=?");
            preparedStatement.setString(1, nomeAdmin);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch(SQLException throwables){
            return false;
        }
        return false;
    }

public void updatePasswordByNomeAdmin(String nomeAdmin, String password) throws AggiornamentoNonRiuscitoException
{
    Connection conn = getConnection();
    PreparedStatement preparedStatement;

    try{
        preparedStatement = conn.prepareStatement("UPDATE amministratore SET password=? WHERE nomeAdmin=?");
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, nomeAdmin);

        preparedStatement.close();
        conn.close();
    }catch (SQLException throwables){
        throw new AggiornamentoNonRiuscitoException("aggiornamento amministratore non riuscito");
    }


}
    private Connection getConnection() {
        connection= DBConnection.getDBConnection();
        return connection.getConnection();
    }
}
