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
package com.ufone.api.policy_engine;

/* You are to implement this
 * interface to configure your own Policy Engine.
 *
 * I think the best way in which this would be flexible and to follow the principle of pluggable
 * authenticators is to implement a database call. That way we can easily assign mappings to
 * individual service providers. The Policy Engine would then query the database and the response
 * returned would be the type of authenticator to use.
 */
public abstract class AbstractPolicyEngine {
        public abstract String AuthenticatorSelection(
            String cliendID, String redirectURI, String levelOfAssurance);
}
