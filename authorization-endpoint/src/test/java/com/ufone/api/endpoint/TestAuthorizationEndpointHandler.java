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
package com.ufone.api.endpoint;

import com.ufone.api.endpoint.AuthorizationEndpointHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;

public class TestAuthorizationEndpointHandler {
        @Nested
        public class AllParametersSupplied {
                AuthorizationEndpointHandler testClass;
                Response response;
                @BeforeEach
                public void simulateRESTCall() throws UnsupportedEncodingException {
                        testClass = new AuthorizationEndpointHandler();
                        response = testClass.returnParam("test_client_id", "test_redirect_uri",
                            "code", "openid mc_authn", "test_version", "123", "test_nonce", "page",
                            "none", "test_max_age", "test_ui_locales", "test_claims_locales",
                            "test_id_token_hint", "test_login_hint", "test_login_hint_token", "2",
                            "test_response_mode", "9000", "test_dtbs");
                }

                @Test
                public void correctResponseCode() {
                        assertEquals(302, response.getStatus());
                }
                @Test
                public void codeSentToCorrectRedirectURI() {
                        assertEquals("test_redirect_uri?code=",
                            response.getLocation().toString().substring(0, 23));
                }
                @Test
                public void codeInResponse() {
                        String pattern = "https://sp.example.com/callback?code=";
                        Pattern match = Pattern.compile(pattern);
                        Matcher matches = match.matcher(response.getLocation().toString());

                        if (matches.find()) {
                                assertEquals(pattern, matches.group());
                        }
                }
                @Test
                public void stateParameterPresentInResponse() {
                        String pattern = "&state=123";
                        Pattern match = Pattern.compile(pattern);
                        Matcher matches = match.matcher(response.getLocation().toString());

                        if (matches.find()) {
                                assertEquals(pattern, matches.group());
                        }
                }
                @Test
                public void correlationIDParameterPresentInResponse() {
                        String pattern = "&correlationID=9000";
                        Pattern match = Pattern.compile(pattern);
                        Matcher matches = match.matcher(response.getLocation().toString());

                        if (matches.find()) {
                                assertEquals(pattern, matches.group());
                        }
                }
        }
}
