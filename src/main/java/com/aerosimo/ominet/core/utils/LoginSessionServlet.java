/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      LoginSessionServlet.java                                        *
 * Created:   21/03/2026, 02:52                                               *
 * Modified:  21/03/2026, 02:52                                               *
 *                                                                            *
 * Copyright (c)  2026.  Aerosimo Ltd                                         *
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

package com.aerosimo.ominet.core.utils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

@WebServlet("/auth/set-session")
public class LoginSessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Read the JSON sent from our JavaScript
            JSONObject data = new JSONObject(new JSONTokener(req.getInputStream()));
            String username = data.getString("username");
            String token = data.getString("token");

            // Create/Get the Server-Side Session
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username);
            session.setAttribute("session_token", token);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"saved\"}");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}