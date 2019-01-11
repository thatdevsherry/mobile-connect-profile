// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

import com.ufone.api.request.Request;

import java.io.UnsupportedEncodingException;

public class InvalidScope extends BaseErrorResponse {
        private final String error = "access_denied";
        private final String errorDescription = "The scope value is invalid";
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
                InvalidScope errorResponse = new InvalidScope();
                baseResponse = errorResponse.buildBaseErrorResponse(request.getRedirectURI());
                baseResponse = errorResponse.addStateQueryParam(baseResponse, request.getState());
                baseResponse = errorResponse.addCorrelationIDQueryParam(
                    baseResponse, request.getCorrelationID());
                return errorResponse.returnResponse(baseResponse);
        }
}
