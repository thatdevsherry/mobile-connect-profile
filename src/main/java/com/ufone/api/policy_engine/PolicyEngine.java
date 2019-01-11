package com.ufone.api.policy_engine;

import com.ufone.api.authenticators.USSDAuthenticator;

public class PolicyEngine implements IPolicyEngine {
        public boolean AuthenticatorSelection(String levelOfAssurance) {
                return new USSDAuthenticator().authenticateUser();
        }
}
