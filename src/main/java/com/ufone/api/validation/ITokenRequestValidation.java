package com.ufone.api.validation;

import com.ufone.api.exceptions.InvalidContentTypeException;
import com.ufone.api.exceptions.InvalidGrantTypeException;

public interface ITokenRequestValidation {
        public boolean validateGrantType(String grantType) throws InvalidGrantTypeException;
        public boolean validateAuthorizationCode(String authorizationCode);
        public boolean validateRedirectURI(String redirectURI);
        public boolean validateContentType(String contentType) throws InvalidContentTypeException;
}
