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
