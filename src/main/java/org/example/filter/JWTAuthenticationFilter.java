package org.example.filter;


import jakarta.annotation.Priority;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.controller.AuthController;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@RequireJWTAuthentication
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(JWTAuthenticationFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        LOGGER.log(Level.INFO,"Filtering request");
        // Get the HTTP Authorization header from the request
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        LOGGER.log(Level.INFO,String.format("Authorization header: %s",authorizationHeader));


        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            LOGGER.log(Level.WARNING,String.format("Invalid authorization header: %s",authorizationHeader));
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        if( AuthController.validateToken(token) ){
            LOGGER.log(Level.INFO,String.format("Token is valid: %s",token));
        } else {
            LOGGER.log(Level.WARNING,String.format("Token is not valid: %s",token));

            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
