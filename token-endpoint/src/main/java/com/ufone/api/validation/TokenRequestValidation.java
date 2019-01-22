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

import com.ufone.api.request.TokenEndpointRequest;
import com.ufone.api.exceptions.InvalidContentTypeException;
import com.ufone.api.exceptions.InvalidAuthorizationException;
import com.ufone.api.exceptions.InvalidGrantTypeException;
import com.ufone.api.exceptions.InvalidRedirectURIException;
import com.ufone.api.exceptions.InvalidAuthorizationException;
import com.ufone.api.exceptions.InvalidAuthorizationCodeException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.sql.*;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Base64;

public class TokenRequestValidation {
        public void isRequestValid(TokenEndpointRequest request)
            throws InvalidGrantTypeException, InvalidContentTypeException,
                   InvalidAuthorizationException, InvalidAuthorizationCodeException,
                   InvalidRedirectURIException {
                String encodedCodeInAuthorizationHeader;
                Properties properties = new Properties();

                // Load values from config.properties, throw exception if something goes wrong
                try {
                        properties.load(this.getClass().getClassLoader().getResourceAsStream(
                            "/config.properties"));
                } catch (Exception e) {
                        // raise appropriate exception and catch it in the handler to call the
                        // correct response class
                }

                ArrayList<String> databaseRow =
                    getRowFromDatabase(request.getAuthorizationCode(), properties);
                // now that we have the authorization code, we'll mark it as used so it can't be
                // used again, even if some error occurs.
                markCodeAsUsed(request.getAuthorizationCode(), properties);

                validateAuthorizationCode(request.getAuthorizationCode(), databaseRow);
                validateRedirectURI(request.getRedirectURI(), databaseRow);
                validateGrantType(request.getGrantType());
                validateContentType(request.getContentType());
                // Validate Authorization Header
                // Parsing it first to ensure "Basic" is present along with code
                encodedCodeInAuthorizationHeader =
                    getCodeFromAuthorizationHeader(request.getAuthorization());
                validateAuthorization(encodedCodeInAuthorizationHeader, properties);
        }

        public String getCodeFromAuthorizationHeader(String authorizationHeader)
            throws InvalidAuthorizationException {
                int indexOfLastCharacter = 0;
                String pattern = "Basic";
                Pattern matches = Pattern.compile(pattern);

                Matcher match = matches.matcher(authorizationHeader);

                if (match.find()) {
                        indexOfLastCharacter = match.end();
                } else {
                        throw new InvalidAuthorizationException();
                }
                return authorizationHeader.substring(
                    indexOfLastCharacter + 1, authorizationHeader.length());
        }

        public void markCodeAsUsed(String authorizationCode, Properties properties) {
                Connection connection = null;
                try {
                        // this shouldn't be required on newer versions but this project
                        // doesn't seem to work without this for me
                        Class.forName(properties.getProperty("databaseDriver"));

                        connection = DriverManager.getConnection(
                            properties.getProperty("CodeDatabaseConnection"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));

                        PreparedStatement statement = connection.prepareStatement(
                            properties.getProperty("markAuthorizationCodeAsUsed"));
                        statement.setString(1, authorizationCode);
                        statement.executeUpdate();

                        statement.close();
                } catch (SQLException e) {
                        // TODO: Appropriate Response pls
                        return;
                } catch (ClassNotFoundException e) {
                        // TODO: Appropriate Response pls
                        return;
                }
        }

        public ArrayList<String> getRowFromDatabase(
            String authorizationCode, Properties properties) {
                /*
                 * NOTE: Please don't use any other datatype. This will do. If you have a
                 * complex schema that makes using an array difficult, use a different
                 * schema.
                 *
                 * Use an array when possible. It's going to be better for caching. Read up
                 * on Cache Locality if you don't know what I"m talking about. In short, use
                 * arrays over linked lists (or any other non-contiguous data type).
                 */
                ArrayList<String> databaseRow = new ArrayList<String>();
                Connection connection = null;

                try {
                        // this shouldn't be required on newer versions but this project
                        // doesn't seem to work without this for me
                        Class.forName(properties.getProperty("databaseDriver"));

                        connection = DriverManager.getConnection(
                            properties.getProperty("CodeDatabaseConnection"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));
                        PreparedStatement statement = connection.prepareStatement(
                            properties.getProperty("getAuthorizationCodeValues"));
                        statement.setString(1, authorizationCode);
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

        public void validateGrantType(String grantType) throws InvalidGrantTypeException {
                if (grantType.equals("authorization_code")) {
                        return;
                } else {
                        throw new InvalidGrantTypeException();
                }
        }

        public void validateAuthorizationCode(String authorizationCode,
            ArrayList<String> databaseRow) throws InvalidAuthorizationCodeException {
                if (databaseRow.size() != 0 && authorizationCode.equals(databaseRow.get(0))) {
                        return;
                } else {
                        throw new InvalidAuthorizationCodeException();
                }
        }

        public void validateRedirectURI(String redirectURIInRequest, ArrayList<String> databaseRow)
            throws InvalidRedirectURIException {
                if (databaseRow.get(1).equals(redirectURIInRequest)) {
                        return;
                } else {
                        throw new InvalidRedirectURIException();
                }
        }

        public void validateAuthorization(String encodedCode, Properties properties)
            throws InvalidAuthorizationException {
                String decodedClientID = "";
                String decodedClientSecret = "";
                byte[] decodedBytes = Base64.getDecoder().decode(encodedCode);
                String decodedString = new String(decodedBytes);

                // Use RegEx to find the index of colon, which we'll use to split out client_id and
                // client_secret. You can simply use substring to split them out if the length of
                // client_id and client_secret is known.
                int indexOfColon = 0;
                String pattern = ":";
                Pattern matches = Pattern.compile(pattern);

                Matcher match = matches.matcher(decodedString);

                if (match.find()) {
                        indexOfColon = match.start();
                } else {
                        throw new InvalidAuthorizationException();
                }

                decodedClientID = decodedString.substring(0, indexOfColon);
                decodedClientSecret =
                    decodedString.substring(indexOfColon + 1, decodedString.length());
                ArrayList<String> clientInfo =
                    getClientInfoFromDatabase(decodedClientID, decodedClientSecret, properties);

                if (clientInfo.size() != 0 && clientInfo.get(0).equals(decodedClientID)
                    && clientInfo.get(1).equals(decodedClientSecret)) {
                        return;
                } else {
                        throw new InvalidAuthorizationException();
                }
        }

        public ArrayList<String> getClientInfoFromDatabase(
            String clientID, String clientSecret, Properties properties) {
                /*
                 * NOTE: Please don't use any other datatype. This will do. If you have a
                 * complex schema that makes using an array difficult, use a different
                 * schema.
                 *
                 * Use an array when possible. It's going to be better for caching. Read up
                 * on Cache Locality if you don't know what I"m talking about. In short, use
                 * arrays over linked lists (or any other non-contiguous data type).
                 */
                ArrayList<String> databaseRow = new ArrayList<String>();
                Connection connection = null;

                try {
                        // this shouldn't be required on newer versions but this project
                        // doesn't seem to work without this for me
                        Class.forName(properties.getProperty("databaseDriver"));

                        connection = DriverManager.getConnection(
                            properties.getProperty("ValidationDatabaseConnection"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));
                        PreparedStatement statement = connection.prepareStatement(
                            properties.getProperty("getClientInfoFromDatabase"));
                        statement.setString(1, clientID);
                        statement.setString(2, clientSecret);
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

        public void validateContentType(String contentType) throws InvalidContentTypeException {
                if (contentType.equals("application/x-www-form-urlencoded")) {
                        return;
                } else {
                        throw new InvalidContentTypeException();
                }
        }
}
