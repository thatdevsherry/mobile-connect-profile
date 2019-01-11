package com.ufone.api.validation;

import com.ufone.api.exceptions.InvalidContentTypeException;
import com.ufone.api.exceptions.InvalidGrantTypeException;

public class TokenRequestValidation implements ITokenRequestValidation {
        public boolean validateGrantType(String grantType) throws InvalidGrantTypeException {
                if (grantType.equals("authorization_code")) {
                        return true;
                } else {
                        throw new InvalidGrantTypeException();
                }
        }
        public boolean validateAuthorizationCode(String authorizationCode) {
                // testing
                return true;
        }
        // the redirect uri validation could be put into a util class so it wouldn't need to be
        // repeated
        public boolean validateRedirectURI(String redirectURI) {
                // testing
                return true;
        }

        public boolean validateContentType(String contentType) throws InvalidContentTypeException {
                if (contentType.equals("application/x-www-form-urlencoded")) {
                        return true;
                } else {
                        throw new InvalidContentTypeException();
                }
        }
}
