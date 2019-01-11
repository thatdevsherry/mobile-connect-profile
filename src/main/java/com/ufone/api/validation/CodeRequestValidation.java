// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.validation;

import com.ufone.api.request.Request;
import com.ufone.api.exceptions.MissingClientIDException;
import com.ufone.api.errors.MissingScope;
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

import java.util.Arrays;

public class CodeRequestValidation implements ICodeRequestValidation {
        public boolean isRequestValid(Request request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException, InvalidScopeException, InvalidDisplayException,
                   InvalidPromptException, InvalidACRException {
                // empty check
                mandatoryParametersNull(request);
                // validity check
                areMandatoryParametersValid(request);
                areOptionalParametersValid(request);
                return true;
        }

        public boolean mandatoryParametersNull(Request request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException {
                if (request.getRedirectURI() == null) {
                        throw new InvalidRedirectURIException();
                } else if (request.getClientID() == null) {
                        throw new MissingClientIDException();
                } else if (request.getResponseType() == null) {
                        throw new InvalidResponseTypeException();
                } else if (request.getScope() == null) {
                        throw new MissingScopeException();
                } else if (request.getVersion() == null) {
                        throw new InvalidVersionException();
                } else if (request.getState() == null) {
                        throw new InvalidStateException();
                } else if (request.getNonce() == null) {
                        throw new MissingNonceException();
                } else {
                        return false;
                }
        }

        public boolean areMandatoryParametersValid(Request request)
            throws InvalidResponseTypeException, InvalidScopeException {
                validateClientID(request.getClientID());
                validateRedirectURI(request.getRedirectURI());
                validateResponseType(request.getResponseType());
                validateScope(request.getScope());
                validateVersion(request.getVersion());
                validateState(request.getState());
                validateNonce(request.getNonce());
                return true;
        }

        public boolean areOptionalParametersValid(Request request)
            throws InvalidDisplayException, InvalidPromptException, InvalidACRException {
                validateDisplay(request.getDisplay());
                validatePrompt(request.getPrompt());
                validateAcrValues(request.getAcrValues());
                return true;
        }

        public boolean validateClientID(String clientID) {
                return true;
        }

        public boolean validateRedirectURI(String redirectURI) {
                return true;
        }

        public boolean validateResponseType(String responseType)
            throws InvalidResponseTypeException {
                if (responseType.equals("code")) {
                        return true;
                } else {
                        throw new InvalidResponseTypeException();
                }
        }

        public boolean validateScope(String scope) throws InvalidScopeException {
                if (scope.equals("openid mc_authn")) {
                        return true;
                } else {
                        throw new InvalidScopeException();
                }
        }

        public boolean validateVersion(String version) {
                return true;
        }

        public boolean validateState(String state) {
                return true;
        }

        public boolean validateNonce(String nonce) {
                return true;
        }

        public boolean validateDisplay(String display) throws InvalidDisplayException {
                if (display == null || display.equals("page") || display.equals("pop-up")
                    || display.equals("touch") || display.equals("wap")) {
                        return true;
                } else {
                        throw new InvalidDisplayException();
                }
        }

        public boolean validatePrompt(String prompt) throws InvalidPromptException {
                if (prompt == null || prompt.equals("none") || prompt.equals("login")
                    || prompt.equals("consent") || prompt.equals("select_account")
                    || prompt.equals("no_seam")) {
                        return true;
                } else {
                        throw new InvalidPromptException();
                }
        }

        public boolean validateMaxAge(String maxAge) {
                return false;
        }

        public boolean validateUiLocales(String uiLocales) {
                return false;
        }

        public boolean validateClaimsLocales(String claimsLocales) {
                return false;
        }

        public boolean validateIDTokenHint(String idTokenHint) {
                return false;
        }

        public boolean validateLoginHint(String loginHint) {
                return false;
        }

        public boolean validateLoginHintToken(String loginHintToken) {
                return false;
        }

        public boolean validateAcrValues(String acrValues) throws InvalidACRException {
                String[] validAcrValues = {"2", "3 2", "4 3 2"};
                if (Arrays.asList(validAcrValues).contains(acrValues) || acrValues == null) {
                        return true;
                } else {
                        throw new InvalidACRException();
                }
        }

        public boolean validateResponseMode(String responseMode) {
                return false;
        }

        public boolean validateCorrelationID(String correlationID) {
                return false;
        }

        public boolean validateDtbs(String dtbs) {
                return false;
        }
}
