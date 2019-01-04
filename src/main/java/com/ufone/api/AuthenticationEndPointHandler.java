package com.ufone.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;

@Path("/")
public class AuthenticationEndPointHandler {
        @GET
        @Path("authorize")
        public Response ReturnParam(@QueryParam("response_type") String response_type,
            @QueryParam("scope") String scope, @QueryParam("client_id") String client_id,
            @QueryParam("redirect_uri") String redirect_uri) {
                if (response_type != null && scope != null && client_id != null
                    && redirect_uri != null) {
                        String output = "response_type= " + response_type + "\n"
                            + "scope= " + scope + "\n"
                            + "client_id= " + client_id + "\n"
                            + "redirect_uri= " + redirect_uri;
                        return Response.status(200).entity(output).build();
                } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Error").build();
                }
        }
}
