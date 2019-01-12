/*
 * Copyright (c) 2019 Muhammad Shehriyar Qureshi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.ufone.api.token;

// import com.ufone.api.request.TokenEndpointRequest;

import com.ufone.api.request.TokenEndpointRequest;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;
import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;

@Path("/")
public class TokenEndpointHandler {
        @GET
        @Path("token")
        @Produces(MediaType.APPLICATION_JSON)
        public Response ReturnParam(@QueryParam("grant_type") String grantType,
            @QueryParam("code") String authorizationCode,
            @QueryParam("redirect_uri") String redirectURI,
            @QueryParam("correlation_id") String correlationID,
            @HeaderParam("Content-Type") String contentType,
            @HeaderParam("Authorization") String authorization) {
                TokenEndpointRequest request = new TokenEndpointRequest(grantType,
                    authorizationCode, redirectURI, correlationID, contentType, authorization);
                return Response.status(200).entity("HAHA= " + contentType).build();

                // Call Request Validator to validate request and throw appropriate
                // exception if any
        }
}
