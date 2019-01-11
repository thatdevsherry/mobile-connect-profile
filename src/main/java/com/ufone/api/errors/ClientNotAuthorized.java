// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

/*
 * Error class which prepares description when client is not authorized.
 */
public class ClientNotAuthorized extends BaseErrorResponse {
        private final String error = "invalid_request";
        private final String errorDescription =
            "The client is not authorized to request an authorization code";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
