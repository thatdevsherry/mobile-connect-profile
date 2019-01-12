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
package com.ufone.api.validation;

import com.ufone.api.request.AuthorizationServerRequest;
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

public interface ICodeRequestValidation {
        public boolean isRequestValid(AuthorizationServerRequest request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException, InvalidScopeException, InvalidDisplayException,
                   InvalidPromptException, InvalidACRException;

        public boolean areMandatoryParametersNull(AuthorizationServerRequest request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException;

        public boolean areMandatoryParametersValid(AuthorizationServerRequest request)
            throws InvalidResponseTypeException, InvalidScopeException;

        public boolean areOptionalParametersValid(AuthorizationServerRequest request)
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
