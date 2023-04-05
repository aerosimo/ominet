/******************************************************************************
 * This piece of work is to enhance profile-manager project functionality.    *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Logout.java                                                     *
 * Created:   24/02/2023, 16:55                                               *
 * Modified:  24/02/2023, 16:55                                               *
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
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "Logout",
        description = "A simple logout servlet to log a user out of the application",
        value = "/logout")
public class Logout extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Logout.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        HttpSession sess = req.getSession();
        String uname = (String) sess.getAttribute("uname");
        String inet = (String) sess.getAttribute("inet");
        log.info("Preparing to sign out " + uname);
        String response = Authentication.signout(uname,inet);
        log.info("Logging response of sign out " + response);
        if (response == "Success") {
            sess.removeAttribute("uname");
            sess.removeAttribute("inet");
            sess.invalidate();
            resp.sendRedirect("login.html");
        } else {
            resp.sendRedirect("index.jsp");
        }
    }
}