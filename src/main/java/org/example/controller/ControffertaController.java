package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.ControffertaDAOPostgres;
import org.example.dto.Controfferta;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.ControffertaDAO;

import java.util.List;

@Path("/controfferta")
public class ControffertaController {

    private final ControffertaDAO controffertaDAO=new ControffertaDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Controfferta getControffertaById(@PathParam("id") int id){
        try{
            return controffertaDAO.getControffertaById(id);
        }catch (NonTrovatoException e){
            return null;
        }
    }

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveControfferta(Controfferta controfferta){
        try{
            controffertaDAO.saveControfferta(controfferta);
            return Response.status(Response.Status.CREATED).build();
        }catch (InserimentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateControfferta(Controfferta controfferta){
        try {
            controffertaDAO.updateControfferta(controfferta);
            return Response.status(Response.Status.CREATED).build();
        }catch (AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteControfferta(@PathParam("id") int id){
        try{
            controffertaDAO.deleteControffertaById(id);
            return Response.status(Response.Status.OK).build();
        }catch (CancellazioneNonRiuscitaException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @GET
    @RequireJWTAuthentication
    @Path("/emailUtente/{emailUtente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Controfferta> getControfferteByUtente(@PathParam("emailUtente")String emailUtente){
        try{
            return controffertaDAO.getControffertaByUtente(emailUtente);
        }catch(NonTrovatoException e) {
            return List.of();
        }
    }


    @GET
    @RequireJWTAuthentication
    @Path("/emailAgente/{emailAgente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Controfferta> getControfferteByAgente(@PathParam("emailAgente")String emailAgente){
        try{
            return controffertaDAO.getControffertaByAgente(emailAgente);
        }catch(NonTrovatoException e){
            return List.of();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Path("/id/{id}/esito/{esito}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEsitoById(@PathParam("id")int id, @PathParam("esito")String esito){
        try{
            controffertaDAO.updateEsitoById(id,esito);
            return Response.status(Response.Status.CREATED).build();
        }catch(AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
