package org.example.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.AgenteDAOPostgres;
import org.example.dao.AmministratoreDAOPostgres;
import org.example.dao.UtenteDAOPostgres;
import org.example.dto.Agente;
import org.example.dto.Amministratore;
import org.example.dto.Utente;
import org.example.interfaccedao.AgenteDAO;
import org.example.interfaccedao.AmministratoreDAO;
import org.example.interfaccedao.UtenteDAO;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("auth")
public class AuthController {

    private static final String ISSUER = "dietiestates-api";
    public static final String PROPERTY_TOKEN = "token";
    private static Algorithm algorithm;
    private static JWTVerifier verifier;
    private final UtenteDAO utenteDAO = new UtenteDAOPostgres();
    private final AgenteDAO agenteDAO = new AgenteDAOPostgres();
    private final AmministratoreDAO amministratoreDAO = new AmministratoreDAOPostgres();
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    static {
        initializeVerifier();
    }

    private static void initializeVerifier() {

        try
        {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("src/main/java/org/example/database/credenziali.txt");
            properties.load(input);
            String key = properties.getProperty("key");
            algorithm = Algorithm.HMAC256(key);
            verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Errore nella lettura della key", e);
        }
    }

    @Path("utente")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUtente(Utente user){

        if (user.getEmail() == null || user.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        String email = user.getEmail();
        String password = user.getPassword();
        if (utenteDAO.loginUtente(email,password)) {
            String token = createJWT(email, TimeUnit.DAYS.toMillis(365));
            user=utenteDAO.getUtenteByEmail(email);
            String jsonResponse = new Gson().toJson(user);
            JsonObject jsonObject= new Gson().fromJson(jsonResponse, JsonObject.class);
            jsonObject.addProperty(PROPERTY_TOKEN,token);

            return Response
                    .status(Response.Status.OK)
                    .entity(jsonObject.toString())
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }


    @Path("agente")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginAgente(Agente agente){
        if (agente.getEmail() == null || agente.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        String email = agente.getEmail();
        String password = agente.getPassword();
        if (agenteDAO.loginAgente(email,password)) {
            String token = createJWT(email, TimeUnit.DAYS.toMillis(365));
            agente=agenteDAO.getAgenteByEmail(email);
            String jsonResponse = new Gson().toJson(agente);
            JsonObject jsonObject= new Gson().fromJson(jsonResponse, JsonObject.class);
            jsonObject.addProperty(PROPERTY_TOKEN,token);

            return Response
                    .status(Response.Status.OK)
                    .entity(jsonObject.toString())
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
    @Path("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginAmministratore(Amministratore amministratore){
        if (amministratore.getNomeAdmin() == null || amministratore.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        String nomeAdmin = amministratore.getNomeAdmin();
        String password = amministratore.getPassword();
        if (amministratoreDAO.loginAdmin(nomeAdmin,password)) {
            String token = createJWT(nomeAdmin, TimeUnit.DAYS.toMillis(365));
            amministratore=amministratoreDAO.getAmministratoreByNomeAdmin(nomeAdmin);
            String jsonResponse = new Gson().toJson(amministratore);
            JsonObject jsonObject= new Gson().fromJson(jsonResponse, JsonObject.class);
            jsonObject.addProperty(PROPERTY_TOKEN,token);

            return Response
                    .status(Response.Status.OK)
                    .entity(jsonObject.toString())
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }

    private String createJWT(String username, long ttlMillis) {

        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public static boolean validateToken(String token){
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
            return false;
        }
    }

}