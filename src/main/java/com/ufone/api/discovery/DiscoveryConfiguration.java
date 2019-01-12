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
package com.ufone.api.discovery;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/* Contains key, value configurations that are returned in the application/json type when provider's
 * Well-Known configuration endpoint receives a request.
 */
public class DiscoveryConfiguration {
        // NOTE: Not reading from file or something as that's gonna slow down
        // performance plus these fields won't be changed often so hardcoding them here
        // shouldn't be a problem and they will be compiled to bytecode so they can't be
        // changed by someone which might have malicious intent. This will be changed if a better
        // solution is brought up.
        @SerializedName("issuer") private static final String issuer = "https://api.ufone.com";
        @SerializedName("authorization_endpoint")
        private static final String authorizationEndpoint = "https://api.ufone.com/oidc/authorize";
        @SerializedName("token_endpoint")
        private static final String tokenEndpoint = "https://api.ufone.com/token";
        @SerializedName("userinfo_endpoint")
        private static final String userinfoEndpoint = "https://api.ufone.com/oidc/userinfo";
        @SerializedName("revocation_endpoint")
        private static final String revocationEndpoint = "https://api.ufone.com/oidc/revoke";
        @SerializedName("jwks_uri")
        private static final String jwksURI = "https://api.ufone.com/oidc/certs";
        @SerializedName("response_types_supported")
        private static final String[] responseTypesSupported = {"code"};
        @SerializedName("scopes_supported")
        private static final String[] scopesSupported = {"openid"};
        @SerializedName("subject_types_supported")
        private static final String[] subject_types_supported = {"public"};
        @SerializedName("id_token_signing_alg_values_supported")
        private static final String[] tokenSigningAlgorithm = {"RS256"};
        @SerializedName("acr_values_supported")
        private static final String[] acrValuesSupported = {"2"};

        /*
         * Builds the application/json response using the configured key, values.
         *
         * @return prettified JSON string
         */
        public static String getResponseAsString() {
                Gson jsonResponse = new GsonBuilder()
                                        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                                        .setPrettyPrinting()
                                        .create();
                String responseBody = jsonResponse.toJson(new DiscoveryConfiguration());
                return responseBody;
        }
}
