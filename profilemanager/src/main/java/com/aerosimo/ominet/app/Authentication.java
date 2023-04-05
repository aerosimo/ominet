/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Authentication.java                                             *
 * Created:   24/02/2023, 12:30                                               *
 * Modified:  24/02/2023, 12:30                                               *
 *                                                                            *
 * Copyright (c)  2023.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.app;

import com.aerosimo.ominet.com.ForgotMail;
import com.aerosimo.ominet.com.ResetMail;
import com.aerosimo.ominet.com.WelcomeMail;
import com.aerosimo.ominet.core.Connect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class Authentication {

    private static final Logger log = LogManager.getLogger(Authentication.class.getName());

    static String response;
    static String sql;
    static Connection con = Connect.dbase();
    static CallableStatement stmt;

    public static String signup(String username, String password, String email, String application){
        log.info("Preparing to register new user account");
        try {
            sql = "{call authenticate_pkg.Signup(?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, application);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(5);
            if (Objects.equals(stmt.getString(5), "Success")) {
                log.info("User: " + username + " registration account successfully created and now sending welcome email");
                WelcomeMail.sendMail(username,email);
            } else {
                log.error("User: " + username + " registration account creation failed with the following: " + response);
            }
        } catch (SQLException err) {
            response = "Account registration attempt failed";
            log.error("Unknown error occurred with the following details: " + err);
        }
        return response;
    }

    public static String signin(String username, String password, String ipaddress) {
        log.info("Preparing to log user in");
        try {
            sql = "{call authenticate_pkg.Login(?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, ipaddress);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.VARCHAR);
            stmt.registerOutParameter(7, Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(7);
            if (Objects.equals(stmt.getString(7), "Success")) {
                log.info("User: " + username + " login to account successfully");
            } else {
                log.error("User: " + username + " login attempt failed with the following: " + response);
            }
        } catch (SQLException err) {
            response = "Account login attempt failed";
            log.error("Unknown Error occurred with the following details: " + err);
        }
        return response;
    }

    public static String signout(String username, String ipaddress){
        log.info("Preparing to log user out");
        try {
            sql = "{call authenticate_pkg.Logout(?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, username);
            stmt.setString(2, ipaddress);
            stmt.execute();
            response = "Success";
            log.info("User: " + username + " successfully logout");
        } catch (SQLException err) {
            response = "Account logout attempt failed";
            log.error("Unknown Error occurred with the following details: " + err);
        }
        return response;
    }

    public static String forgot(String emailaddress){
        log.info("Preparing to activate forgot user credentials");
        String authcode = Transaction.otp("Forgot Password");
        try {
            sql = "{call authenticate_pkg.ForgotPassword(?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, emailaddress);
            stmt.setString(2, authcode);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(5);
            if (Objects.equals(stmt.getString(5), "Success")) {
                log.info("User " + stmt.getString(4) + " with email " + emailaddress + " successfully located and now sending forgot email");
                ForgotMail.sendMail(stmt.getString(4),stmt.getString(3),emailaddress);
            } else {
                log.error("User with email " + emailaddress + " could not be found: " + response);
            }
        } catch (SQLException err) {
            response = "Forgot Password attempt failed " + err;
            log.error("Unknown Error occurred with the following details: " + err);
        }
        return response;
    }

    public static String reset(String accesscode, String password){
        log.info("Preparing to reset user credentials");
        try {
            sql = "{call authenticate_pkg.ResetPassword(?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, accesscode);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(5);
            if (Objects.equals(stmt.getString(5), "Success")) {
                log.info("User " + stmt.getString(3) + " with email " + stmt.getString(4) + " successfully located and now sending reset email");
                ResetMail.sendMail(stmt.getString(3),stmt.getString(4));
            } else {
                log.error("User details could not be found: " + response);
            }
        } catch (SQLException err) {
            response = "Reset Password attempt failed " + err;
            log.error("Unknown Error occurred with the following details: " + err);
        }
        return response;
    }
}