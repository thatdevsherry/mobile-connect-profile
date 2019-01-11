// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.validation;

import com.ufone.api.request.Request;
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

public interface IRequestValidation {
        public boolean isRequestValid(Request request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException, InvalidScopeException, InvalidDisplayException,
                   InvalidPromptException, InvalidACRException;

        public boolean mandatoryParametersNull(Request request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException;

        public boolean areMandatoryParametersValid(Request request)
            throws InvalidResponseTypeException, InvalidScopeException;

        public boolean areOptionalParametersValid(Request request)
            throws InvalidDisplayException, InvalidPromptException, InvalidACRException;

        public boolean validateClientID(String clientID);

        public boolean validateRedirectURI(String redirectURI);

        public boolean validateResponseType(String responseType)
            throws InvalidResponseTypeException;

        public boolean validateScope(String scope) throws InvalidScopeException;

        public boolean validateVersion(String version);

        public boolean validateState(String state);

        public boolean validateNonce(String nonce);

        public boolean validateDisplay(String display) throws InvalidDisplayException;

        public boolean validatePrompt(String prompt) throws InvalidPromptException;

        public boolean validateMaxAge(String maxAge);

        public boolean validateUiLocales(String uiLocales);

        public boolean validateClaimsLocales(String claimsLocales);

        public boolean validateIDTokenHint(String idTokenHint);

        public boolean validateLoginHint(String loginHint);

        public boolean validateLoginHintToken(String loginHintToken);

        public boolean validateAcrValues(String acrValues) throws InvalidACRException;

        public boolean validateResponseMode(String responseMode);

        public boolean validateCorrelationID(String correlationID);

        public boolean validateDtbs(String dtbs);
}
