// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;
import com.ufone.api.request.Request;

import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class InvalidGrantType {
        private final String error = "invalid_grant";
        private final String errorDescription =
            "Mandatory parameter grant_type is missing (or) invalid";

        public String getErrorTitle() {
                return this.error;
        }

        public String getErrorDescription() {
                return this.errorDescription;
        }

        public Response returnResponse(String jsonResponse) {
                return Response.status(302).entity(jsonResponse).build();
        }

        public Response buildAndReturnResponse() {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonResponse = gson.toJson(new InvalidContentType());
                return this.returnResponse(jsonResponse);
        }
}
