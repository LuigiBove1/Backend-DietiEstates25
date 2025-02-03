package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.UtenteDAOPostgres;
import org.example.dto.Utente;
import org.example.exceptions.*;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.UtenteDAO;

@Path("/utente")
public class UtenteController {

    private final UtenteDAO utenteDAO=new UtenteDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Utente getUtenteByEmail(@PathParam("email") String email){
        try {
            return utenteDAO.getUtenteByEmail(email);
        } catch (NonTrovatoException e) {
            return null;
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUtente(Utente utente){
        try{
            utenteDAO.saveUtente(utente);
            return Response.status(Response.Status.CREATED).build();
        }catch(InserimentoNonRiuscitoException | CredenzialiNonValideException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUtente(Utente utente){
        try{
            utenteDAO.updateUtente(utente);
            return Response.status(Response.Status.CREATED).build();
        } catch (AggiornamentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @RequireJWTAuthentication
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUtenteByEmail(@PathParam("email") String email){
        try{
            utenteDAO.deleteUtenteByEmail(email);
            return Response.status(Response.Status.OK).build();
        } catch (CancellazioneNonRiuscitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
