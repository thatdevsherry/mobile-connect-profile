// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.request;

public class Request {
        /*
         * This class is to be used to hold the request parameters sent from the client.
         * It is aimed to make it easier to pass around the request's query parameters.
         */

        // Required parameters
        private String clientID;
        private String redirectURI;
        private String responseType;
        private String scope;
        private String version;
        private String state;
        private String nonce;

        // Optional parameters
        private String display;
        private String prompt;
        private String maxAge;
        private String uiLocales;
        private String claimsLocales;
        private String idTokenHint;
        private String loginHint;
        private String loginHintToken;
        private String acrValues;
        private String responseMode;
        private String correlationID;
        private String dtbs;

        // Getters
        public String getClientID() {
                return this.clientID;
        }

        public String getRedirectURI() {
                return this.redirectURI;
        }

        public String getResponseType() {
                return this.responseType;
        }

        public String getScope() {
                return this.scope;
        }

        public String getVersion() {
                return this.version;
        }

        public String getState() {
                return this.state;
        }

        public String getNonce() {
                return this.nonce;
        }

        public String getDisplay() {
                return this.display;
        }

        public String getPrompt() {
                return this.prompt;
        }

        public String getMaxAge() {
                return this.maxAge;
        }

        public String getUiLocales() {
                return this.uiLocales;
        }

        public String getClaimsLocales() {
                return this.claimsLocales;
        }

        public String getIdTokenHint() {
                return this.idTokenHint;
        }

        public String getLoginHint() {
                return this.loginHint;
        }

        public String getLoginHintToken() {
                return this.loginHintToken;
        }

        public String getAcrValues() {
                return this.acrValues;
        }

        public String getResponseMode() {
                return this.responseMode;
        }

        public String getCorrelationID() {
                return this.correlationID;
        }

        public String getDtbs() {
                return this.dtbs;
        }

        // Setters
        public Request display(String display) {
                this.display = display;
                return this;
        }

        public Request prompt(String prompt) {
                this.prompt = prompt;
                return this;
        }

        public Request maxAge(String maxAge) {
                this.maxAge = maxAge;
                return this;
        }

        public Request uiLocales(String uiLocales) {
                this.uiLocales = uiLocales;
                return this;
        }

        public Request claimsLocales(String claimsLocales) {
                this.claimsLocales = claimsLocales;
                return this;
        }

        public Request idTokenHint(String idTokenHint) {
                this.idTokenHint = idTokenHint;
                return this;
        }

        public Request loginHint(String loginHint) {
                this.loginHint = loginHint;
                return this;
        }

        public Request loginHintToken(String loginHintToken) {
                this.loginHintToken = loginHintToken;
                return this;
        }

        public Request acrValues(String acrValues) {
                this.acrValues = acrValues;
                return this;
        }

        public Request responseMode(String responseMode) {
                this.responseMode = responseMode;
                return this;
        }

        public Request correlationID(String correlationID) {
                this.correlationID = correlationID;
                return this;
        }

        public Request dtbs(String dtbs) {
                this.dtbs = dtbs;
                return this;
        }

        public Request(String clientID, String redirectURI, String responseType, String scope,
            String version, String state, String nonce) {
                this.clientID = clientID;
                this.redirectURI = redirectURI;
                this.responseType = responseType;
                this.scope = scope;
                this.version = version;
                this.state = state;
                this.nonce = nonce;
        }

        public Request build() {
                Request request =
                    new Request(clientID, redirectURI, responseType, scope, version, state, nonce);
                request.display = this.display;
                request.prompt = this.prompt;
                request.maxAge = this.maxAge;
                request.uiLocales = this.uiLocales;
                request.claimsLocales = this.claimsLocales;
                request.idTokenHint = this.idTokenHint;
                request.loginHint = this.loginHint;
                request.loginHintToken = this.loginHintToken;
                request.acrValues = this.acrValues;
                request.responseMode = this.responseMode;
                request.correlationID = this.correlationID;
                request.dtbs = this.dtbs;
                return request;
        }
}
