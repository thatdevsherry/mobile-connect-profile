// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

public class UnknownUser extends BaseErrorResponse {
        private final String error = "access_denied";
        private final String errorDescription = "Unknown user";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
