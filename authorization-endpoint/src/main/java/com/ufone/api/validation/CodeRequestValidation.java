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
import java.util.Arrays;

public class CodeRequestValidation {
        public void isRequestValid(AuthorizationServerRequest request)
            throws MissingClientIDException, MissingScopeException, InvalidRedirectURIException,
                   InvalidResponseTypeException, InvalidVersionException, InvalidStateException,
                   MissingNonceException, InvalidClientIDException, InvalidScopeException,
                   InvalidPromptException, InvalidDisplayException, InvalidACRException {
                // I'm loading up the config.properties file here so we can just pass it to the
                // functions that need it. This way, we prevent having to read the file every time
                // we execute a function that reuqires this file.
                Properties properties = new Properties();

                // Load values from config.properties, throw exception if something goes wrong
                try {
                        properties.load(this.getClass().getClassLoader().getResourceAsStream(
                            "/config.properties"));
                } catch (Exception e) {
                        // raise appropriate exception and catch it in the handler to call the
                        // correct response class
                }

                areMandatoryParametersNull(request);
                // we'll query the database here so we can store the whole row. That way, we won't
                // have to query the database every time we validate a new query parameter.
                ArrayList<String> databaseRow =
                    getRowFromDatabase(request.getClientID(), properties);
                areMandatoryParametersValid(request, databaseRow, properties);
                areOptionalParametersValid(request, properties);
        }

        public ArrayList<String> getRowFromDatabase(String clientID, Properties properties) {
                /*
                 * NOTE: Please don't use any other datatype. This will do. If you have a complex
                 * schema that makes using an array difficult, use a different schema.
                 *
                 * Use an array when possible. It's going to be better for caching. Read up on
                 * Cache Locality if you don't know what I"m talking about. In short, use arrays
                 * over linked lists (or any other non-contiguous data type).
                 */
                ArrayList<String> databaseRow = new ArrayList<String>();
                Connection connection = null;

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

        public void areMandatoryParametersValid(AuthorizationServerRequest request,
            ArrayList<String> databaseRow, Properties properties)
            throws InvalidClientIDException, InvalidRedirectURIException, InvalidVersionException,
                   InvalidStateException, InvalidResponseTypeException, InvalidScopeException {
                validateClientID(request.getClientID(), databaseRow);
                validateRedirectURI(request.getRedirectURI(), databaseRow.get(2));
                validateResponseType(request.getResponseType(), properties);
                validateScope(request.getScope(), properties);
                validateVersion(request.getVersion(), properties);
        }

        public void areOptionalParametersValid(
            AuthorizationServerRequest request, Properties properties)
            throws InvalidDisplayException, InvalidPromptException, InvalidACRException {
                validateDisplay(request.getDisplay(), properties);
                validatePrompt(request.getPrompt(), properties);
                validateAcrValues(request.getAcrValues(), properties);
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

        public void validateResponseType(String responseType, Properties properties)
            throws InvalidResponseTypeException {
                if (responseType.equals(properties.getProperty("responseType"))) {
                        return;
                } else {
                        throw new InvalidResponseTypeException();
                }
        }

        public void validateScope(String scope, Properties properties)
            throws InvalidScopeException {
                String[] scopesSupported = properties.getProperty("scope").toString().split(",");
                if (Arrays.stream(scopesSupported).anyMatch(scope::equals)) {
                        return;
                } else {
                        throw new InvalidScopeException();
                }
        }

        public void validateVersion(String version, Properties properties)
            throws InvalidVersionException {
                String[] versionValuesSupported =
                    properties.getProperty("version").toString().split(",");
                if (version.equals("mc_v1.1")
                    || Arrays.stream(versionValuesSupported).anyMatch(version::equals)) {
                        return;
                } else {
                        throw new InvalidVersionException();
                }
        }

        public void validateDisplay(String display, Properties properties)
            throws InvalidDisplayException {
                String[] displayValuesSupported =
                    properties.getProperty("display").toString().split(",");
                if (display == null
                    || Arrays.stream(displayValuesSupported).anyMatch(display::equals)) {
                        return;
                } else {
                        throw new InvalidDisplayException();
                }
        }

        public void validatePrompt(String prompt, Properties properties)
            throws InvalidPromptException {
                String[] promptValuesSupported =
                    properties.getProperty("prompt").toString().split(",");
                if (prompt == null
                    || Arrays.stream(promptValuesSupported).anyMatch(prompt::equals)) {
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

        public void validateAcrValues(String acrValues, Properties properties)
            throws InvalidACRException {}

        public void validateResponseMode(String responseMode) {}

        public void validateCorrelationID(String correlationID) {}

        public void validateDtbs(String dtbs) {}
}
