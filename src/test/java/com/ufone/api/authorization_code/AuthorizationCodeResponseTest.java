package com.ufone.api.authorization_code;

import com.ufone.api.authorization_code.AuthorizationCodeResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AuthorizationCodeResponseTest {
        @Test
        public void testAuthorizationCodeGeneration() {
                AuthorizationCodeResponse testClass = new AuthorizationCodeResponse();
                String code = testClass.generateCode();
                assertEquals(30, code.length());
        }
}
