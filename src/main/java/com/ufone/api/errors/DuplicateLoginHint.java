// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

public class DuplicateLoginHint extends BaseErrorResponse {
        private final String error = "invalid_request";
        private final String errorDescription = "Malformed request, duplicate parameter entries";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
