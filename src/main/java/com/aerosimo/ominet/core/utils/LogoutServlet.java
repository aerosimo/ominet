/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      LogoutServlet.java                                              *
 * Created:   05/04/2026, 19:05                                               *
 * Modified:  05/04/2026, 19:05                                               *
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

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

    // Using the same base path you used for login
    private static final String API_LOGOUT_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/logout";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null) {
            String token = (String) session.getAttribute("session_token");

            if (token != null && !token.trim().isEmpty()) {
                // Trigger the external API call in the background
                try {
                    sendApiLogoutRequest(token);
                    System.out.println("External API logout signal sent.");
                } catch (Exception e) {
                    // Log the error but don't stop the user from logging out locally
                    System.err.println("Failed to notify external logout API: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // Clean up Tomcat session
            session.removeAttribute("user");
            session.removeAttribute("session_token");
            session.invalidate();
            System.out.println("Local user session invalidated successfully.");
        }

        // Set Cache-Control to ensure the browser doesn't keep a ghost copy
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        // Redirect to login page
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    /**
     * Sends a background POST request to the authcore API with the authKey payload.
     */
    private void sendApiLogoutRequest(String token) throws Exception {
        // Construct the expected JSON body: {"authKey": "the_token"}
        String jsonPayload = "{\"authKey\": \"" + token + "\"}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest apiRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_LOGOUT_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Fire and receive the response (We log it but proceed regardless)
        HttpResponse<String> apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("API Logout Response Code: " + apiResponse.statusCode());
        System.out.println("API Logout Body: " + apiResponse.body());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}