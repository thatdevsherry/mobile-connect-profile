package com.ufone.api.authorization_code;

import javax.ws.rs.core.Response;
import org.apache.commons.text.RandomStringGenerator;

public class AuthorizationCodeResponse {
        private String responseString;
        public String generateCode() {
                char[][] codeFormat = {{'0', '9'}, {'A', 'Z'}, {'a', 'z'}};
                RandomStringGenerator generator =
                    new RandomStringGenerator.Builder().withinRange(codeFormat).build();
                String code = generator.generate(30);
                return code;
        }
        public Response buildResponse(
            String redirectURI, String state, String nonce, String correlationID) {
                if (correlationID == null || correlationID.equals("")) {
                        responseString = String.format("%s?code=%s&state=%s&nonce=%s", redirectURI,
                            this.generateCode(), state, nonce);
                } else {
                        responseString =
                            String.format("%s?code=%s&state=%s&nonce=%s&correlation_id=%s",
                                redirectURI, this.generateCode(), state, nonce, correlationID);
                }
                return Response.status(302).header("Location", responseString).build();
        }
}
