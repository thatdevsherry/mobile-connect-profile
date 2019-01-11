package com.ufone.api.request;

import com.ufone.api.request.Request;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RequestTest {
        Request request;

        @BeforeEach
        public void createRequestObjects() {
                request = new Request("client_id", "redirect_uri", "response_type", "scope",
                    "version", "state", "nonce");
        }
        @Test
        public void testClientID() {
                assertEquals(request.getClientID(), "client_id");
        }
        @Test
        public void testRedirectURI() {
                assertEquals(request.getRedirectURI(), "redirect_uri");
        }
}
