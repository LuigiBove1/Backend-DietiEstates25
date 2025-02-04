package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.RicercaDAOPostgres;
import org.example.dto.Ricerca;
import org.example.exceptions.*;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.RicercaDAO;

import java.util.List;

@Path("/ricerca")
public class RicercaController {

    private final RicercaDAO ricercaDAO = new RicercaDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ricerca getRicercaById(@PathParam("id") int id) {
        try {
            return ricercaDAO.getRicercaById(id);
        } catch (NonTrovatoException e) {
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRicerca(Ricerca ricerca) {
        try {
            ricercaDAO.saveRicerca(ricerca);
            return Response.status(Response.Status.CREATED).build();
        } catch (InserimentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRicerca(Ricerca ricerca) {
        try {
            ricercaDAO.updateRicerca(ricerca);
            return Response.status(Response.Status.OK).build();
        } catch (AggiornamentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRicercaById(@PathParam("id") int id) {
        try {
            ricercaDAO.deleteRicercaById(id);
            return Response.status(Response.Status.OK).build();
        } catch (CancellazioneNonRiuscitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/utente/{utente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ricerca> getUltimeRicercheByUtente(@PathParam("utente") String utente) {
        try {
            return ricercaDAO.getUltimeRicercheByUtente(utente);

        } catch (NonTrovatoException e) {
            return List.of();
        }
    }
}
