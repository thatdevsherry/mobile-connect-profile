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
package com.ufone.api.discovery;

import com.ufone.api.discovery.DiscoveryConfiguration;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/*
 * This is the provider's Well-Known configuration endpoint. It returns an application/json response
 * containing provider endpoints and supported configurations.
 */
@Path("/.well-known")
public class DiscoveryEndpoint {
        /*
         * Returns application/json containing provider onfigurations.
         *
         * TODO: Need to develop exception to handle when building JSON fails
         */
        @GET
        @Path("openid-configuration")
        @Produces(MediaType.APPLICATION_JSON)
        public Response ReturnParam() {
                try {
                        return Response.status(200)
                            .entity(DiscoveryConfiguration.getResponseAsString())
                            .header("Cache-Control", "public, max-age=3600")
                            .header("X-Content-Type-Options", "nosniff")
                            .header("X-XSS-Protection", "1; mode=block")
                            .header("Accept-Ranges", "bytes")
                            .build();
                } catch (Exception e) {
                        return Response.status(400).entity("wut").build();
                }
        }
}
