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

/*
 * This class is to be used to hold the request parameters sent from the client.
 * It is aimed to make it easier to pass around the request's query parameters.
 *
 * It uses the builder pattern. Though there is no strong use case for it, I'm using it in case
 * query parameters change.
 */
public class TokenEndpointRequest {
        //
        // Required parameters
        private String grantType;
        private String authorizationCode;
        private String redirectURI;
        private String correlationID;

        // Header
        private String authorization;
        private String contentType;

        // Getters
        public String getGrantType() {
                return this.grantType;
        }

        public String getAuthorizationCode() {
                return this.authorizationCode;
        }

        public String getRedirectURI() {
                return this.redirectURI;
        }

        public String getCorrelationID() {
                return this.correlationID;
        }
        public String getAuthorizationHeader() {
                return this.authorization;
        }
        public String getContentTypeHeader() {
                return this.contentType;
        }

        public TokenEndpointRequest(String grantType, String authorizationCode, String redirectURI,
            String correlationID, String authorization, String contentType) {
                this.grantType = grantType;
                this.authorizationCode = authorizationCode;
                this.redirectURI = redirectURI;
                this.correlationID = correlationID;
                this.authorization = authorization;
                this.contentType = contentType;
        }
}
