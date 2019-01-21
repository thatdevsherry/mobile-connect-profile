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
package com.ufone.api.validation;

import com.ufone.api.request.AuthorizationServerRequest;
import com.ufone.api.exceptions.MissingClientIDException;
import com.ufone.api.errors.MissingScope;
import com.ufone.api.exceptions.MissingScopeException;
import com.ufone.api.exceptions.InvalidRedirectURIException;
import com.ufone.api.exceptions.InvalidResponseTypeException;
import com.ufone.api.exceptions.InvalidVersionException;
import com.ufone.api.exceptions.InvalidStateException;
import com.ufone.api.exceptions.MissingNonceException;
import com.ufone.api.exceptions.InvalidScopeException;
import com.ufone.api.exceptions.InvalidDisplayException;
import com.ufone.api.exceptions.InvalidPromptException;
import com.ufone.api.exceptions.InvalidACRException;
import com.ufone.api.exceptions.InvalidClientIDException;

import javax.ws.rs.core.Response;

import java.sql.*;

import java.sql.SQLException;
import java.lang.ClassNotFoundException;

import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ArrayList;

public class CodeRequestValidation {
        public void isRequestValid(AuthorizationServerRequest request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException, InvalidClientIDException, InvalidScopeException,
                   InvalidPromptException, InvalidDisplayException, InvalidACRException {
                areMandatoryParametersNull(request);
                // we'll query the database here so we can store the whole row. That way, we won't
                // have to query the database every time we validate a new query parameter.
                ArrayList<String> databaseRow = getRowFromDatabase(request.getClientID());
                areMandatoryParametersValid(request, databaseRow);
                areOptionalParametersValid(request);
        }

        public ArrayList<String> getRowFromDatabase(String clientID) {
                /*
                 * NOTE: Please don't use any other datatype. This will do. If you have a complex
                 * schema that makes using an array difficult, use a different schema.
                 *
                 * Use an array when possible. It's going to be better for caching. Read up on
                 * Cache Locality if you don't know what I"m talking about. In short, use arrays
                 * over linked lists (or any other non-contiguous data type).
                 */
                ArrayList<String> databaseRow = new ArrayList<String>();

                Properties properties = new Properties();
                Connection connection = null;

                // Load values from config.properties, throw exception if something goes wrong
                try {
                        properties.load(this.getClass().getClassLoader().getResourceAsStream(
                            "/config.properties"));
                } catch (Exception e) {
                        // raise appropriate exception and catch it in the handler to call the
                        // correct response class
                }
                try {
                        // this shouldn't be required on newer versions but this project doesn't
                        // seem to work without this for me
                        Class.forName(properties.getProperty("databaseDriver"));

                        connection = DriverManager.getConnection(
                            properties.getProperty("ValidationDatabaseConnection"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));
                        PreparedStatement statement = connection.prepareStatement(
                            properties.getProperty("getRowFromDatabase"));
                        statement.setString(1, clientID);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                databaseRow.add(resultSet.getString(1));
                                databaseRow.add(resultSet.getString(2));
                                databaseRow.add(resultSet.getString(3));
                        }
                        resultSet.close();
                        statement.close();
                        return databaseRow;
                } catch (SQLException e) {
                        // TODO: Appropriate Response pls
                        return null;
                } catch (ClassNotFoundException e) {
                        // TODO: Appropriate Response pls
                        return null;
                }
        }

        public void areMandatoryParametersNull(AuthorizationServerRequest request)
            throws InvalidClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException {
                if (request.getRedirectURI() == null || request.getRedirectURI().equals("")) {
                        throw new InvalidRedirectURIException();
                } else if (request.getClientID() == null || request.getClientID().equals("")) {
                        throw new InvalidClientIDException();
                } else if (request.getResponseType() == null
                    || request.getResponseType().equals("")) {
                        throw new InvalidResponseTypeException();
                } else if (request.getScope() == null || request.getScope().equals("")) {
                        throw new MissingScopeException();
                } else if (request.getVersion() == null || request.getVersion().equals("")) {
                        throw new InvalidVersionException();
                } else if (request.getState() == null || request.getState().equals("")) {
                        throw new InvalidStateException();
                } else if (request.getNonce() == null || request.getNonce().equals("")) {
                        throw new MissingNonceException();
                } else {
                        return;
                }
        }

        public void areMandatoryParametersValid(
            AuthorizationServerRequest request, ArrayList<String> databaseRow)
            throws InvalidClientIDException, InvalidRedirectURIException, InvalidVersionException,
                   InvalidStateException, InvalidResponseTypeException, InvalidScopeException {
                validateClientID(request.getClientID(), databaseRow);
                validateRedirectURI(request.getRedirectURI(), databaseRow.get(1));
                validateResponseType(request.getResponseType());
                validateScope(request.getScope());
                validateVersion(request.getVersion());
        }

        public void areOptionalParametersValid(AuthorizationServerRequest request)
            throws InvalidDisplayException, InvalidPromptException, InvalidACRException {
                validateDisplay(request.getDisplay());
                validatePrompt(request.getPrompt());
                validateAcrValues(request.getAcrValues());
        }

        /*
         * @param clientID value of query parameter client_id
         *
         * @param databaseRow A string ArrayList that holds all column values for row fetched using
         *     client_id value in request
         *
         */
        public void validateClientID(String clientIDFromRequest, ArrayList<String> databaseRow)
            throws InvalidClientIDException {
                if (databaseRow.size() != 0 && databaseRow.get(0).equals(clientIDFromRequest)) {
                        return;
                } else {
                        throw new InvalidClientIDException();
                }
        }

        /*
         * @param redirectURIFromRequest value of query parameter redirect_uri
         *
         * @param redirectURIFromDatabase value of redirect_uri in database, of client_id value
         *     passed in request
         *
         */
        public void validateRedirectURI(String redirectURIFromRequest,
            String redirectURIFromDatabase) throws InvalidRedirectURIException {
                if (redirectURIFromRequest.equals(redirectURIFromDatabase)) {
                        return;
                } else {
                        throw new InvalidRedirectURIException();
                }
        }

        public void validateResponseType(String responseType) throws InvalidResponseTypeException {
                if (responseType.equals("code")) {
                        return;
                } else {
                        throw new InvalidResponseTypeException();
                }
        }

        public void validateScope(String scope) throws InvalidScopeException {
                if (scope.equals("openid mc_authn") || scope.equals("openid")) {
                        return;
                } else {
                        throw new InvalidScopeException();
                }
        }

        public void validateVersion(String version) throws InvalidVersionException {
                if (version.equals("mc_v1.1") || version.equals("mc_v2.0")
                    || version.equals("mc_di_r2_v2.3")) {
                        return;
                } else {
                        throw new InvalidVersionException();
                }
        }

        public void validateDisplay(String display) throws InvalidDisplayException {
                if (display == null || display.equals("page") || display.equals("pop-up")
                    || display.equals("touch") || display.equals("wap")) {
                        return;
                } else {
                        throw new InvalidDisplayException();
                }
        }

        public void validatePrompt(String prompt) throws InvalidPromptException {
                if (prompt == null || prompt.equals("none") || prompt.equals("login")
                    || prompt.equals("consent") || prompt.equals("select_account")
                    || prompt.equals("no_seam")) {
                        return;
                } else {
                        throw new InvalidPromptException();
                }
        }

        public void validateMaxAge(String maxAge) {}

        public void validateUiLocales(String uiLocales) {}

        public void validateClaimsLocales(String claimsLocales) {}

        public void validateIDTokenHint(String idTokenHint) {}

        public void validateLoginHint(String loginHint) {}

        public void validateLoginHintToken(String loginHintToken) {}

        public void validateAcrValues(String acrValues) throws InvalidACRException {}

        public void validateResponseMode(String responseMode) {}

        public void validateCorrelationID(String correlationID) {}

        public void validateDtbs(String dtbs) {}
}
