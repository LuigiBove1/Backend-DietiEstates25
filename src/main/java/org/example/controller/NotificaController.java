package org.example.controller;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.NotificaDAOPostgres;
import org.example.dto.Notifica;
import org.example.exceptions.AggiornamentoNonRiuscitoException;
import org.example.exceptions.CancellazioneNonRiuscitaException;
import org.example.exceptions.InserimentoNonRiuscitoException;
import org.example.exceptions.NonTrovatoException;
import org.example.filter.RequireJWTAuthentication;
import org.example.interfaccedao.NotificaDAO;
import java.util.List;

@Path("/notifica")
public class NotificaController {

    private final NotificaDAO notificaDAO = new NotificaDAOPostgres();

    @GET
    @RequireJWTAuthentication
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Notifica getNotificaById(@PathParam("id")int id){
        try{
            return notificaDAO.getNotificaById(id);
        }catch(NonTrovatoException e){
            return null;
        }
    }

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveNotifica(Notifica notifica){
        try{
            notificaDAO.saveNotifica(notifica);
            return Response.status(Response.Status.CREATED).build();
        }catch(InserimentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNotifica(Notifica notifica){
        try{
            notificaDAO.updateNotifica(notifica);
            return Response.status(Response.Status.CREATED).build();
        }catch (AggiornamentoNonRiuscitoException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RequireJWTAuthentication
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteNotificaById(@PathParam("id") int id){
        try{
            notificaDAO.deleteNotificaById(id);
            return Response.status(Response.Status.OK).build();
        }catch (CancellazioneNonRiuscitaException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @RequireJWTAuthentication
    @Path("/emailUtente/{emailUtente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Notifica> getNotificheByUtente(@PathParam("emailUtente") String emailUtente){
        try{
            return notificaDAO.getNotificheByUtente(emailUtente);
        }catch(NonTrovatoException e){
            return List.of();
        }
    }

}
