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

import java.io.InputStream;
import java.util.Properties;

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
        @SerializedName("issuer") private String issuer;
        @SerializedName("authorization_endpoint") private String authorizationEndpoint;
        @SerializedName("token_endpoint") private String tokenEndpoint;
        @SerializedName("userinfo_endpoint") private String userInfoEndpoint;
        @SerializedName("revocation_endpoint") private String revocationEndpoint;
        @SerializedName("jwks_uri") private String jwksURI;
        @SerializedName("response_types_supported") private String[] responseTypesSupported;
        @SerializedName("scopes_supported") private String[] scopesSupported;
        @SerializedName("subject_types_supported") private String[] subjectTypesSupported;
        @SerializedName("id_token_signing_alg_values_supported")
        private String[] tokenSigningAlgorithm;
        @SerializedName("acr_values_supported") private String[] acrValuesSupported;

        private static DiscoveryConfiguration discoveryConfiguration = new DiscoveryConfiguration();

        private DiscoveryConfiguration() {
                Properties properties = new Properties();

                // Load values from config.properties, throw exception if something goes wrong
                try {
                        properties.load(this.getClass().getClassLoader().getResourceAsStream(
                            "/config.properties"));
                } catch (Exception e) {
                        // raise appropriate exception and catch it in the handler to call the
                        // correct response class
                }

                this.issuer = properties.getProperty("issuer");
                this.authorizationEndpoint = properties.getProperty("authorizationEndpoint");
                this.tokenEndpoint = properties.getProperty("tokenEndpoint");
                this.userInfoEndpoint = properties.getProperty("userInfoEndpoint");
                this.revocationEndpoint = properties.getProperty("revocationEndpoint");
                this.jwksURI = properties.getProperty("jwksURI");
                // TODO: Add way of converting config.properties values to array
                this.responseTypesSupported =
                    properties.getProperty("responseTypesSupported").toString().split(",");
                this.scopesSupported =
                    properties.getProperty("scopesSupported").toString().split(",");
                this.subjectTypesSupported =
                    properties.getProperty("subjectTypesSupported").toString().split(",");
                this.tokenSigningAlgorithm =
                    properties.getProperty("tokenSigningAlgorithm").toString().split(",");
                this.acrValuesSupported =
                    properties.getProperty("acrValuesSupported").toString().split(",");
        }

        /*
         * Builds the application/json response using the configured key, values.
         *
         * @return prettified JSON string
         */
        public static String getResponseAsString() {
                if (discoveryConfiguration == null) {
                        discoveryConfiguration = new DiscoveryConfiguration();
                }
                // TODO: This is not efficient. Figure out a way to store the string once the
                // singleton is initialized and refer to the string instead of creating a new json
                // string everytime.
                Gson jsonResponse = new GsonBuilder()
                                        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                                        .setPrettyPrinting()
                                        .create();
                String responseBody = jsonResponse.toJson(discoveryConfiguration);
                return responseBody;
        }
}
