package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.InserzioneDAOPostgres;
import org.example.dto.Inserzione;
import org.example.dto.Ricerca;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.InserzioneDAO;
import java.util.List;


@Path("/inserzione")
public class InserzioneController {
    private final InserzioneDAO inserzioneDAO= new InserzioneDAOPostgres();


    @GET
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Inserzione getInserzioneById(@PathParam("id") int id){
        try{
            return inserzioneDAO.getInserzioneById(id);
        }catch(NonTrovatoException e){
            return null;
        }
    }

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveInserzione(Inserzione inserzione){
        try{
            inserzioneDAO.saveInserzione(inserzione);
            return Response.status(Response.Status.CREATED).build();
        }catch (InserimentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInserzione(Inserzione inserzione){
        try{
            inserzioneDAO.updateInserzione(inserzione);
            return Response.status(Response.Status.CREATED).build();
        }catch(AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteInserzioneById(@PathParam("id") int id){
        try{
            inserzioneDAO.deleteInserzioneById(id);
            return Response.status(Response.Status.OK).build();
        }catch (CancellazioneNonRiuscitaException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @RequireJWTAuthentication
    @Path("ricerca")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByParametriMultipli(Ricerca ricerca){
        try{
            return inserzioneDAO.getInserzioniByParametriMultipli(ricerca);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/prezzoMinimo/{prezzoMinimo}/prezzoMassimo/{prezzoMassimo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByRangePrezzo(@PathParam("prezzoMinimo") int prezzoMinimo,@PathParam("prezzoMassimo") int prezzoMassimo){
        try{
            return inserzioneDAO.getInserzioniByRangePrezzo(prezzoMinimo,prezzoMassimo);
        }catch(NonTrovatoException e){
            return List.of();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/numeroStanze/{numeroStanze}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByNumeroStanze(@PathParam("numeroStanze")int numeroStanze){
        try{
            return inserzioneDAO.getInserzioniByNumeroDiStanze(numeroStanze);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/classeEnergetica/{classeEnergetica}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByClasseEnergetica(@PathParam("classeEnergetica")String classeEnergetica){
        try{
            return inserzioneDAO.getInserzioniByClasseEnergetica(classeEnergetica);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/citta/{citta}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByCitta(@PathParam("citta")String citta){
        try{
            return inserzioneDAO.getInserzioniByCitta(citta);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/indirizzo/{indirizzo}/raggio/{raggio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByIndirizzo(@PathParam("indirizzo")String indirizzo,@PathParam("raggio")int raggio){
        try{
            return inserzioneDAO.getInserzioniByIndirizzo(indirizzo,raggio);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }
    @GET
    @RequireJWTAuthentication
    @Path("/latitudine/{latitudine}/longitudine/{longitudine}/raggio/{raggio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByRaggio(@PathParam("latitudine") double latitudine,
                                                  @PathParam("longitudine") double longitudine,
                                                  @PathParam("raggio") int raggio){
        try{
            return inserzioneDAO.getInserzioniByRaggio(latitudine,longitudine,raggio);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }
    @GET
    @RequireJWTAuthentication
    @Path("/agenzia/{agenzia}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByAgenzia(@PathParam("agenzia") String agenzia){
        try{
            return inserzioneDAO.getInserzioniByAgenzia(agenzia);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }
    @GET
    @RequireJWTAuthentication
    @Path("/emailAgente/{emailAgente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Inserzione> getInserzioniByAgente(@PathParam("emailAgente")String emailAgente){
        try{
            return inserzioneDAO.getInserzioniByAgente(emailAgente);
        }catch (NonTrovatoException e){
            return List.of();
        }
    }


}
