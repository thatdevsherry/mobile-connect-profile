/*
 * Copyright (c) 2019 Muhammad Shehriyar Qureshi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
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
import com.ufone.api.request.AuthorizationServerRequest;
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
        /*
         * @param clientID value of client_id query parameter
         *
         * @param redirectURI value of redirect_uri query parameter
         *
         * @param responseType value of response_type query parameter
         *
         * @param scope value of scope query parameter
         *
         * @param version value of version query parameter
         *
         * @param state value of state query parameter
         *
         * @param nonce value of nonce query parameter
         *
         * @param display value of display query parameter
         *
         * @param prompt value of prompt query parameter
         *
         * @param maxAge value of max_age query parameter
         *
         * @param uiLocales value of ui_locales query parameter
         *
         * @param claimsLocales value of claims_locales query parameter
         *
         * @param idTokenHint value of id_token_hint query parameter
         *
         * @param loginHint value of login_hint query parameter
         *
         * @param loginHintToken value of login_hint_token query parameter
         *
         * @param acrValues value of acr_values query parameter
         *
         * @param responseMode value of response_mode query parameter
         *
         * @param correlationID value of correlation_id query parameter
         *
         * @param dtbs value of dtbs query parameter
         *
         * @return Response Object returned by appropriate Response function
         */
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
                AuthorizationServerRequest request = new AuthorizationServerRequest(
                    clientID, redirectURI, responseType, scope, version, state, nonce)
                                                         .display(display)
                                                         .prompt(prompt)
                                                         .maxAge(maxAge)
                                                         .uiLocales(uiLocales)
                                                         .claimsLocales(claimsLocales)
                                                         .idTokenHint(idTokenHint)
                                                         .loginHint(loginHint)
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
