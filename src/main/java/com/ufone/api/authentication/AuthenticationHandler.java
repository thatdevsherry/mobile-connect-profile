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
