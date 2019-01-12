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

import com.ufone.api.request.TokenEndpointRequest;
import com.ufone.api.exceptions.InvalidContentTypeException;
import com.ufone.api.exceptions.InvalidGrantTypeException;

public class TokenRequestValidation implements ITokenRequestValidation {
        public boolean isRequestValid(TokenEndpointRequest request)
            throws InvalidGrantTypeException, InvalidContentTypeException {
                validateGrantType(request.getGrantType());
                validateAuthorizationCode(request.getAuthorizationCode());
                validateRedirectURI(request.getRedirectURI());
                validateContentType(request.getContentType());
                return true;
        }

        public boolean validateGrantType(String grantType) throws InvalidGrantTypeException {
                if (grantType.equals("authorization_code")) {
                        return true;
                } else {
                        throw new InvalidGrantTypeException();
                }
        }

        public boolean validateAuthorizationCode(String authorizationCode) {
                // testing
                return true;
        }

        // the redirect uri validation could be put into a util class so it wouldn't need to be
        // repeated
        public boolean validateRedirectURI(String redirectURI) {
                // testing
                return true;
        }

        public boolean validateContentType(String contentType) throws InvalidContentTypeException {
                if (contentType.equals("application/x-www-form-urlencoded")) {
                        return true;
                } else {
                        throw new InvalidContentTypeException();
                }
        }
}
