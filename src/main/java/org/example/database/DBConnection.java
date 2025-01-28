package org.example.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

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
            FileInputStream input = new FileInputStream("src/main/java/org/example/database/credenziali.txt");
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
            System.out.println("Errore connessione al database!");
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Errore lettura file credenziali!");
        } catch (IOException e) {
            System.out.println("Errore caricamento file credenziali!");
        }

        return conn;
    }




}
