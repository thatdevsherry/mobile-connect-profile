// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

/*
 * Error class which provides description when client is not allowed.
 */
public class ClientNotAllowed extends BaseErrorResponse {
        private final String error = "access_denied";
        private final String errorDescription =
            "The client is not allowed to make MC service requests  ";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
