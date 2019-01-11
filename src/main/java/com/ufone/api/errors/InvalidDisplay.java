package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

import com.ufone.api.request.Request;

import java.io.UnsupportedEncodingException;

public class InvalidDisplay extends BaseErrorResponse {
        private final String error = "invalid_request";
        private final String errorDescription = "Invalid display value (or) not supported.";
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
                InvalidDisplay errorResponse = new InvalidDisplay();
                baseResponse = errorResponse.buildBaseErrorResponse(request.getRedirectURI());
                baseResponse = errorResponse.addStateQueryParam(baseResponse, request.getState());
                baseResponse = errorResponse.addCorrelationIDQueryParam(
                    baseResponse, request.getCorrelationID());
                return errorResponse.returnResponse(baseResponse);
        }
}
