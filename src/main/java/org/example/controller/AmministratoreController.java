package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.AmministratoreDAOPostgres;
import org.example.dto.Amministratore;
import org.example.exceptions.*;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.AmministratoreDAO;

@Path("/amministratore")
public class AmministratoreController {

    private final AmministratoreDAO amministratoreDAO = new AmministratoreDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{nomeAdmin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAmministratoreByNomeAdmin(@PathParam("nomeAdmin") String nomeAdmin) {
        try {
            Amministratore amministratore = amministratoreDAO.getAmministratoreByNomeAdmin(nomeAdmin);
            return Response.ok(amministratore).build();
        } catch (NonTrovatoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Amministratore non trovato").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAmministratore(Amministratore amministratore){
        try {
            amministratoreDAO.saveAmministratore(amministratore);
            return Response.status(Response.Status.CREATED).build();
        } catch (InserimentoNonRiuscitoException | CredenzialiNonValideException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAmministratore(Amministratore amministratore){
        try {
            amministratoreDAO.updateAmministratore(amministratore);
            return Response.status(Response.Status.OK).build();
        } catch (AggiornamentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{nomeAdmin}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAmministratoreByNomeAdmin(@PathParam("nomeAdmin") String nomeAdmin){
        try {
            amministratoreDAO.deleteAmministratoreByNomeAdmin(nomeAdmin);
            return Response.status(Response.Status.OK).build();
        } catch (CancellazioneNonRiuscitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Path("/password/{nomeAdmin}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePasswordByNomeAdmin(@PathParam("nomeAdmin") String nomeAdmin, @PathParam("password") String password){
        try {
            amministratoreDAO.updatePasswordByNomeAdmin(nomeAdmin, password);
            return Response.status(Response.Status.OK).build();
        } catch (AggiornamentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

