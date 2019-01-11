package com.ufone.api.request;

import com.ufone.api.request.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RequestTest {
        @Nested
        public class TestRequestParametersNotNull {
                Request request;

                @BeforeEach
                public void createRequestObjects() {
                        request =
                            new Request("test_client_id", "test_redirect_uri", "test_response_type",
                                "test_scope", "test_version", "test_state", "test_nonce")
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
