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
package com.ufone.api.request;

import com.ufone.api.request.AuthorizationServerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AuthorizationServerRequestTest {
        @Nested
        public class TestAuthorizationServerRequestObjectProperlyCreated {
                AuthorizationServerRequest request;

                @BeforeEach
                public void createRequestObjects() {
                        request = new AuthorizationServerRequest("test_client_id",
                            "test_redirect_uri", "test_response_type", "test_scope", "test_version",
                            "test_state", "test_nonce")
                                      .display("test_display")
                                      .prompt("test_prompt")
                                      .maxAge("test_max_age")
                                      .uiLocales("test_ui_locales")
                                      .claimsLocales("test_claims_locales")
                                      .idTokenHint("test_id_token_hint")
                                      .loginHint("test_login_hint")
                                      .loginHintToken("test_login_hint_token")
                                      .acrValues("test_acr_values")
                                      .responseMode("test_response_mode")
                                      .correlationID("test_correlation_id")
                                      .dtbs("test_dtbs")
                                      .build();
                }
                @Test
                public void testClientID() {
                        assertEquals(request.getClientID(), "test_client_id");
                }
                @Test
                public void testRedirectURI() {
                        assertEquals(request.getRedirectURI(), "test_redirect_uri");
                }
                @Test
                public void testResponseType() {
                        assertEquals(request.getResponseType(), "test_response_type");
                }
                @Test
                public void testScope() {
                        assertEquals(request.getScope(), "test_scope");
                }
                @Test
                public void testVersion() {
                        assertEquals(request.getVersion(), "test_version");
                }
                @Test
                public void testState() {
                        assertEquals(request.getState(), "test_state");
                }
                @Test
                public void testNonce() {
                        assertEquals(request.getNonce(), "test_nonce");
                }
                @Test
                public void testDisplay() {
                        assertEquals(request.getDisplay(), "test_display");
                }
                @Test
                public void testPrompt() {
                        assertEquals(request.getPrompt(), "test_prompt");
                }
                @Test
                public void testMaxAge() {
                        assertEquals(request.getMaxAge(), "test_max_age");
                }
                @Test
                public void testUiLocales() {
                        assertEquals(request.getUiLocales(), "test_ui_locales");
                }
                @Test
                public void testClaimsLocales() {
                        assertEquals(request.getClaimsLocales(), "test_claims_locales");
                }
                @Test
                public void testIDTokenHint() {
                        assertEquals(request.getIdTokenHint(), "test_id_token_hint");
                }
                @Test
                public void testLoginHint() {
                        assertEquals(request.getLoginHint(), "test_login_hint");
                }
                @Test
                public void testLoginHintToken() {
                        assertEquals(request.getLoginHintToken(), "test_login_hint_token");
                }
                @Test
                public void testAcrValues() {
                        assertEquals(request.getAcrValues(), "test_acr_values");
                }
                @Test
                public void testResponseMode() {
                        assertEquals(request.getResponseMode(), "test_response_mode");
                }
                @Test
                public void testCorrelationID() {
                        assertEquals(request.getCorrelationID(), "test_correlation_id");
                }
                @Test
                public void testDtbs() {
                        assertEquals(request.getDtbs(), "test_dtbs");
                }
        }
}
