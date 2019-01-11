// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.authentication;

import com.ufone.api.authentication.AuthenticationHandler;
import com.ufone.api.errors.MissingClientID;
import com.ufone.api.errors.MissingScope;
import com.ufone.api.errors.InvalidRedirectURI;
import com.ufone.api.errors.InvalidResponseType;
import com.ufone.api.errors.InvalidVersion;
import com.ufone.api.errors.InvalidState;
import com.ufone.api.errors.InvalidScope;
import com.ufone.api.errors.MissingNonce;
import com.ufone.api.errors.ServerError;
import com.ufone.api.errors.AuthenticationFailed;
import com.ufone.api.errors.InvalidDisplay;
import com.ufone.api.errors.InvalidPrompt;
import com.ufone.api.errors.InvalidACR;
import com.ufone.api.request.Request;
import com.ufone.api.validation.CodeRequestValidation;
import com.ufone.api.exceptions.MissingClientIDException;
import com.ufone.api.exceptions.MissingScopeException;
import com.ufone.api.exceptions.InvalidRedirectURIException;
import com.ufone.api.exceptions.InvalidResponseTypeException;
import com.ufone.api.exceptions.InvalidVersionException;
import com.ufone.api.exceptions.InvalidStateException;
import com.ufone.api.exceptions.MissingNonceException;
import com.ufone.api.exceptions.InvalidScopeException;
import com.ufone.api.exceptions.InvalidDisplayException;
import com.ufone.api.exceptions.InvalidPromptException;
import com.ufone.api.exceptions.InvalidACRException;
import com.ufone.api.policy_engine.PolicyEngine;
import com.ufone.api.authorization_code.AuthorizationCodeResponse;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;

/*
 * This is the authentication endpoint handler. This class is responsible for calling all
 * relevant classes and returning an appropriate response raised by the called classes.
 */
@Path("/")
public class AuthenticationEndPointHandler {
        @GET
        @Path("authorize")
        @Produces(MediaType.APPLICATION_FORM_URLENCODED)
        public Response ReturnParam(@QueryParam("client_id") String clientID,
            @QueryParam("redirect_uri") String redirectURI,
            @QueryParam("response_type") String responseType, @QueryParam("scope") String scope,
            @QueryParam("version") String version, @QueryParam("state") String state,
            @QueryParam("nonce") String nonce, @QueryParam("display") String display,
            @QueryParam("prompt") String prompt, @QueryParam("max_age") String maxAge,
            @QueryParam("ui_locales") String uiLocales,
            @QueryParam("claims_locales") String claimsLocales,
            @QueryParam("id_token_hint") String idTokenHint,
            @QueryParam("login_hint") String loginHint,
            @QueryParam("login_hint_token") String loginHintToken,
            @QueryParam("acr_values") String acrValues,
            @QueryParam("response_mode") String responseMode,
            @QueryParam("correlation_id") String correlationID, @QueryParam("dtbs") String dtbs)
            throws UnsupportedEncodingException {
                // Create a request object
                Request request =
                    new Request(clientID, redirectURI, responseType, scope, version, state, nonce)
                        .display(display)
                        .prompt(prompt)
                        .maxAge(maxAge)
                        .uiLocales(uiLocales)
                        .claimsLocales(claimsLocales)
                        .idTokenHint(idTokenHint)
                        .loginHintToken(loginHintToken)
                        .acrValues(acrValues)
                        .responseMode(responseMode)
                        .correlationID(correlationID)
                        .dtbs(dtbs)
                        .build();

                try {
                        // Call Request Validator to validate request and throw appropriate
                        // exception if any
                        new CodeRequestValidation().isRequestValid(request);
                        return new AuthenticationHandler().handler(request);
                } catch (InvalidRedirectURIException invalidRedirectURI) {
                        return new InvalidRedirectURI().buildAndReturnResponse(request);
                } catch (MissingClientIDException missingClientID) {
                        return new MissingClientID().buildAndReturnResponse(request);
                } catch (InvalidResponseTypeException invalidResponseType) {
                        return new InvalidResponseType().buildAndReturnResponse(request);
                } catch (MissingScopeException missingScope) {
                        return new MissingScope().buildAndReturnResponse(request);
                } catch (InvalidVersionException invalidVersion) {
                        return new InvalidVersion().buildAndReturnResponse(request);
                } catch (InvalidStateException invalidState) {
                        return new InvalidState().buildAndReturnResponse(request);
                } catch (MissingNonceException missingNonce) {
                        return new MissingNonce().buildAndReturnResponse(request);
                } catch (InvalidScopeException invalidScope) {
                        return new InvalidScope().buildAndReturnResponse(request);
                } catch (InvalidDisplayException invalidDisplay) {
                        return new InvalidDisplay().buildAndReturnResponse(request);
                } catch (InvalidPromptException invalidPrompt) {
                        return new InvalidPrompt().buildAndReturnResponse(request);
                } catch (InvalidACRException invalidACR) {
                        return new InvalidACR().buildAndReturnResponse(request);
                } catch (Exception serverError) {
                        return new ServerError().buildAndReturnResponse(request);
                }
        }
}
