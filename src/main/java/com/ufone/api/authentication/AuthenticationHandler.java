// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.authentication;

import com.ufone.api.request.Request;
import com.ufone.api.validation.CodeRequestValidation;
import com.ufone.api.policy_engine.PolicyEngine;
import com.ufone.api.authorization_code.AuthorizationCodeResponse;
import com.ufone.api.errors.AuthenticationFailed;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;

public class AuthenticationHandler {
        public Response handler(Request request) throws UnsupportedEncodingException {
                boolean authenticationResponse =
                    new PolicyEngine().AuthenticatorSelection(request.getAcrValues());
                if (authenticationResponse == true) {
                        return new AuthorizationCodeResponse().buildResponse(
                            request.getRedirectURI(), request.getState(), request.getNonce(),
                            request.getCorrelationID());
                } else {
                        return new AuthenticationFailed().buildAndReturnResponse(request);
                }
        }
}
