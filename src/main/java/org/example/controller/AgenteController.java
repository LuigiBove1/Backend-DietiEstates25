package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.AgenteDAOPostgres;
import org.example.dto.Agente;
import org.example.exceptions.*;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.AgenteDAO;

@Path("/agente")
public class AgenteController {

    private final AgenteDAO agenteDAO = new AgenteDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Agente getAgenteByEmail(@PathParam("email") String email){
        try {
            return agenteDAO.getAgenteByEmail(email);
        } catch (NonTrovatoException e) {
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAgente(Agente agente){
        try{
            agenteDAO.saveAgente(agente);
            return Response.status(Response.Status.CREATED).build();
        }catch(InserimentoNonRiuscitoException | CredenzialiNonValideException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAgente(Agente agente){
        try{
            agenteDAO.updateAgente(agente);
            return Response.status(Response.Status.CREATED).build();
        } catch (AggiornamentoNonRiuscitoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAgenteByEmail(@PathParam("email") String email){
        try{
            agenteDAO.deleteAgenteByEmail(email);
            return Response.status(Response.Status.OK).build();
        } catch (CancellazioneNonRiuscitaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }



}
