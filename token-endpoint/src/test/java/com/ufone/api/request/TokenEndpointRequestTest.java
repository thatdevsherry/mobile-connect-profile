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

import com.ufone.api.request.TokenEndpointRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TokenEndpointRequestTest {
        @Nested
        public class TokenEndpointRequestObjectCreatedProperly {
                TokenEndpointRequest request;

                @BeforeEach
                public void setupRequestObject() {
                        request = new TokenEndpointRequest("authorization_code", "some_code",
                            "https://sp.example.com/redirect_here", "123",
                            "application/x-www-form-urlencoded", "Basic 81092");
                }
                @Test
                public void requestObjectNotNull() {
                        assertNotNull(request);
                }

                @Test
                public void correctGrantTypeFieldInRequest() {
                        assertEquals(request.getGrantType(), "authorization_code");
                }

                @Test
                public void correctAuthorizationCodeInRequest() {
                        assertEquals(request.getAuthorizationCode(), "some_code");
                }
                @Test
                public void correctRedirectURIInRequest() {
                        assertEquals(
                            request.getRedirectURI(), "https://sp.example.com/redirect_here");
                }
                @Test
                public void correctCorrelationIDInRequest() {
                        assertEquals(request.getCorrelationID(), "123");
                }
                @Test
                public void correctContentTypeInRequest() {
                        assertEquals(request.getContentType(), "application/x-www-form-urlencoded");
                }
                @Test
                public void correctAuthorizationInRequest() {
                        assertEquals(request.getAuthorization(), "Basic 81092");
                }
        }
}
