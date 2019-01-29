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

import com.ufone.api.exceptions.AuthenticationFailedException;
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

public class PolicyEngine {
        /*
         * This is a test function.
         *
         * It is for testing authentication of only predefined MSISDNS
         */
        public void validateMSISDN(String loginHint) throws AuthenticationFailedException {
                String status = null;
                Connection connection = null;

                Properties properties = new Properties();

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
                            properties.getProperty("TestingMSISDNDatabase"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));
                        PreparedStatement statement =
                            connection.prepareStatement(properties.getProperty("validateMSISDN"));
                        statement.setString(1, loginHint);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                status = resultSet.getString(1);
                        }
                        resultSet.close();
                        statement.close();
                        if (status != null && status.equals("t")) {
                                return;
                        } else {
                                throw new AuthenticationFailedException();
                        }
                } catch (SQLException e) {
                        // TODO: Appropriate Response pls
                        return;
                } catch (ClassNotFoundException e) {
                        // TODO: Appropriate Response pls
                        return;
                }
        }
        /*
         * Query the Policy Engine database to figure out what authenticator to use.
         *
         * @param clientID value of client_id query parameter.
         *
         * @param redirectURI value of redirect_uri query parameter.
         *
         * @param levelOfAssurance value of acr_values query parameter.
         *
         * @return authenticator String which is the name of the authenticator to use.
         */
        public String AuthenticatorSelection(
            String clientID, String redirectURI, String levelOfAssurance) {
                String authenticatorToUse = null;
                Properties properties = new Properties();

                // Load values from config.properties, throw exception if something goes wrong
                try {
                        properties.load(this.getClass().getClassLoader().getResourceAsStream(
                            "/config.properties"));
                } catch (Exception e) {
                        // raise appropriate exception and catch it in the handler to call the
                        // correct response class
                }

                try {
                        authenticatorToUse = getAuthenticatorToUse(
                            clientID, redirectURI, levelOfAssurance, properties);
                } catch (Exception e) {
                }

                return authenticatorToUse;
        }

        public String getAuthenticatorToUse(
            String clientID, String redirectURI, String levelOfAssurance, Properties properties) {
                String authenticatorToUse = null;
                Connection connection = null;

                try {
                        // this shouldn't be required on newer versions but this project doesn't
                        // seem to work without this for me
                        Class.forName(properties.getProperty("databaseDriver"));

                        connection = DriverManager.getConnection(
                            properties.getProperty("PolicyEngineDatabase"),
                            properties.getProperty("databaseUser"),
                            properties.getProperty("databaseUserPassword"));
                        PreparedStatement statement = connection.prepareStatement(
                            properties.getProperty("getAuthenticatorToUse"));
                        statement.setString(1, clientID);
                        statement.setString(2, redirectURI);
                        statement.setString(3, levelOfAssurance);
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                authenticatorToUse = resultSet.getString(1);
                        }
                        resultSet.close();
                        statement.close();
                        return authenticatorToUse;
                } catch (SQLException e) {
                        // TODO: Appropriate Response pls
                        return null;
                } catch (ClassNotFoundException e) {
                        // TODO: Appropriate Response pls
                        return null;
                }
        }
}
