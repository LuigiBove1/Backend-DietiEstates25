package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.OffertaDAOPostgres;
import org.example.dto.Offerta;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.OffertaDAO;
import java.util.List;

@Path("/offerta")
public class OffertaController {

    private final OffertaDAO offertaDAO=new OffertaDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Offerta getOffertaById(@PathParam("id")int id){
        try{
            return offertaDAO.getOffertaById(id);
        }catch(NonTrovatoException e){
            return null;
        }
    }

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOfferta(Offerta offerta){
        try{
            offertaDAO.saveOfferta(offerta);
            return Response.status(Response.Status.CREATED).build();
        }catch(InserimentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOfferta(Offerta offerta){
        try{
            offertaDAO.updateOfferta(offerta);
            return Response.status(Response.Status.CREATED).build();
        }catch (AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOffertaById(@PathParam("id") int id){
        try{
            offertaDAO.deleteOffertaById(id);
            return Response.status(Response.Status.OK).build();
        }catch (CancellazioneNonRiuscitaException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/emailUtente/{emailUtente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offerta> getOfferteByUtente(@PathParam("emailUtente") String emailUtente){
        try{
            return offertaDAO.getOfferteByUtente(emailUtente);
        }catch(NonTrovatoException e){
            return List.of();
        }
    }
    @GET
    @RequireJWTAuthentication
    @Path("/emailAgente/{emailAgente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offerta> getOfferteByAgente(@PathParam("emailAgente") String emailAgente){
        try{
            return offertaDAO.getOfferteByAgente(emailAgente);
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
            offertaDAO.updateEsitoById(id,esito);
            return Response.status(Response.Status.OK).build();
        }catch (AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
