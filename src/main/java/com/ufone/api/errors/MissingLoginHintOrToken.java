// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.errors;

import com.ufone.api.errors.BaseErrorResponse;

import javax.ws.rs.core.Response;

public class MissingLoginHintOrToken extends BaseErrorResponse {
        private final String error = "invalid_request";
        private final String errorDescription =
            "MANDATORY parameters login_hint_token (or) login_hint does not exist.";

        @Override
        public String getErrorTitle() {
                return this.error;
        }

        @Override
        public String getErrorDescription() {
                return this.errorDescription;
        }
}
