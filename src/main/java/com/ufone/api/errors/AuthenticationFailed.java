// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;
import com.ufone.api.request.Request;

import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;

public class AuthenticationFailed extends BaseErrorResponse {
        private final String error = "access_denied";
        private final String errorDescription = "User failed to authenticate";
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
                AuthenticationFailed errorResponse = new AuthenticationFailed();
                baseResponse = errorResponse.buildBaseErrorResponse(request.getRedirectURI());
                baseResponse = errorResponse.addStateQueryParam(baseResponse, request.getState());
                baseResponse = errorResponse.addCorrelationIDQueryParam(
                    baseResponse, request.getCorrelationID());
                return errorResponse.returnResponse(baseResponse);
        }
}
