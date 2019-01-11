// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;
import com.ufone.api.request.Request;

import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;

public class MissingScope extends BaseErrorResponse {
        private final String error = "invalid_scope";
        private final String errorDescription = "MANDATORY parameter scope is missing";
        private String baseResponse;

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }

        public Response buildAndReturnResponse(Request request)
            throws UnsupportedEncodingException {
                MissingScope errorResponse = new MissingScope();
                baseResponse = errorResponse.buildBaseErrorResponse(request.getRedirectURI());
                baseResponse = errorResponse.addStateQueryParam(baseResponse, request.getState());
                return errorResponse.returnResponse(baseResponse);
        }
}
