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
package com.ufone.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Given the value of login_hint, parses out the value, which can be MSISDN, Encrypted MSISDN or PCR
 */
public class LoginHintParser {
        /*
         * Given the value of login_hint query parameter, parses the value and returns it.
         *
         * @param loginHint value of login_hint query parameter.
         *
         * @returns value String array with two elements, first one being the type of login_hint
         *     value and second being the actual value.
         */
        public static String[] parseLoginHint(String loginHint) {
                String[] loginHintInfo = new String[2];
                String type, value = null;
                int indexOfLastCharacter = 0;
                String pattern = "(PCR:|MSISDN:|ENCR_MSISDN:)";
                Pattern matches = Pattern.compile(pattern);

                Matcher match = matches.matcher(loginHint);

                if (match.find()) {
                        indexOfLastCharacter = match.end();
                } else {
                }
                if (indexOfLastCharacter != 0) {
                        type = loginHint.substring(0, indexOfLastCharacter - 1);
                        value = loginHint.substring(indexOfLastCharacter, loginHint.length());
                        loginHintInfo[0] = type;
                        loginHintInfo[1] = value;
                        return loginHintInfo;
                } else {
                        // should throw exception here so the endpoint catches it and doesn't
                        // proceed forward
                        return null;
                }
        }
}
