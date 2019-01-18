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
package com.ufone.api.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class TokenResponse {
        @SerializedName("token_type") private String tokenType = "bearer";
        @SerializedName("id_token") private String IDToken;
        @SerializedName("access_token") private String accessToken;
        // Mobile Connect API Doc says it should be string, but OIDC doesn't.
        @SerializedName("expires_in") private String expiresIn;
        @SerializedName("correlation_id") private String correlationID;

        // Setters
        public void setIDToken(String IDToken) {
                this.IDToken = IDToken;
        }
        public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
        }
        public void setExpiresIn(String expiresIn) {
                this.expiresIn = expiresIn;
        }
        public void setCorrelationID(String correlationID) {
                this.correlationID = correlationID;
        }

        /*
         * Given response values, build and return a String which is a formatted JSON body.
         *
         * @param IDToken value of id_token to return in response.
         *
         * @param accessToken value of access_token to return in response.
         *
         * @param expiresIn value of expires_in to return in response.
         *
         * @param correlationID value of correlation_id to return in response.
         */
        public String tokenResponse(
            String IDToken, String accessToken, String expiresIn, String correlationID) {
                // create a new TokenResponse Object
                TokenResponse tokenResponse = new TokenResponse();

                // Fill in the fields
                tokenResponse.setIDToken(IDToken);
                tokenResponse.setAccessToken(accessToken);
                tokenResponse.setExpiresIn(expiresIn);
                tokenResponse.setCorrelationID(correlationID);

                // create Gson object
                Gson response = new GsonBuilder()
                                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                                    .setPrettyPrinting()
                                    .create();

                // fill in Gson object using the tokenResponse object we created and convert it to
                // String
                String responseBody = response.toJson(tokenResponse);
                return responseBody;
        }
}
