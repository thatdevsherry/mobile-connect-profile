// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

public class InternalServerError extends BaseErrorResponse {
        private final String error = "temporarily_unavailable";
        private final String errorDescription = "Internal Server Error";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
