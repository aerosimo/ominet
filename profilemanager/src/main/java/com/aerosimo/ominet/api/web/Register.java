/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Register.java                                                   *
 * Created:   24/02/2023, 16:53                                               *
 * Modified:  24/02/2023, 16:53                                               *
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

package com.aerosimo.ominet.api.web;

import com.aerosimo.ominet.app.Authentication;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Register",
        description = "A simple registration servlet to capture the information from registration form",
        value = "/register")
public class Register extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Register.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String uname = req.getParameter("uname");
        String email = req.getParameter("email");
        String pword = req.getParameter("pword");
        log.info("Preparing to register new user account for " + uname);
        String response = Authentication.signup(uname,pword,email,"Profile-Manager");
        log.info("Logging response of Account registration " + response);
        if (response.equals("Success")) {
            resp.sendRedirect("login.html");
        } else {
            resp.sendRedirect("register.html");
        }
    }
}