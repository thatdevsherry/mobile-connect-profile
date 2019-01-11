package com.ufone.api.authorization_code;

import com.ufone.api.authorization_code.AuthorizationCodeResponse;
import org.junit.Test;
import org.junit.Assert;

public class AuthorizationCodeResponseTest {
        @Test
        public void testAuthorizationCodeGeneration() {
                // return;
                AuthorizationCodeResponse testClass = new AuthorizationCodeResponse();
                String code = testClass.generateCode();
                Assert.assertEquals(30, code.length());
        }
}
