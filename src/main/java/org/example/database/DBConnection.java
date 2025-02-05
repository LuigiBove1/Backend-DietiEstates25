package org.example.database;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static DBConnection dbcon = null;

    private  Connection conn = null;

    private DBConnection(){}


    public static DBConnection getDBConnection()
    {   // se la classe connessione è nulla, la crea
        if (dbcon == null) {
            dbcon = new DBConnection();
        }

        return dbcon;
    }

    // metodo pubblico per ottenere la connessione
    public Connection getConnection()
    {


        try
        {
            Properties properties = new Properties();
            InputStream input = Files.newInputStream(Paths.get("credenziali.txt"));
            properties.load(input);


            String nome = properties.getProperty("db.nome");
            String password = properties.getProperty("db.password");
            String url = properties.getProperty("db.url");
            String driver = properties.getProperty("db.driver");


            if(conn==null || conn.isClosed())
            {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, nome, password);
            }
        } catch (SQLException | ClassNotFoundException  throwables) {
            LOGGER.log(Level.SEVERE,"Errore connessione al database!", throwables);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE,"Errore lettura file credenziali!", e);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Errore caricamento file credenziali!", e);
        }

        return conn;
    }




}
