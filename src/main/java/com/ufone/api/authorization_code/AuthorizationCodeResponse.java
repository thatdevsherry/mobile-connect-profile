package com.ufone.api.authorization_code;

import javax.ws.rs.core.Response;

public class AuthorizationCodeResponse {
        private String responseString;
        public String generateCode() {
                return "sOmeRanDomCoDe";
        }
        public Response buildResponse(
            String redirectURI, String state, String nonce, String correlationID) {
                if (correlationID != null) {
                        responseString =
                            String.format("%s?code=%s&state=%s&nonce=%s&correlation_id=%s",
                                redirectURI, this.generateCode(), state, nonce, correlationID);
                } else {
                        responseString = String.format("%s?code=%s&state=%s&nonce=%s", redirectURI,
                            this.generateCode(), state, nonce);
                }
                return Response.status(302).header("Location", responseString).build();
        }
}
