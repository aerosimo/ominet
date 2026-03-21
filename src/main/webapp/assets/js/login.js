/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      login.js                                                        *
 * Created:   21/03/2026, 02:47                                               *
 * Modified:  21/03/2026, 02:47                                               *
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

(function() {
    // 1. Configuration
    const API_LOGIN_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/login";
    // Using JSP expression to ensure the path is always correct for your local Servlet
    const SESSION_BRIDGE_URL = "<%= request.getContextPath() %>/auth/set-session";

    const handleLogin = async (event) => {
        // 2. STOP the browser from refreshing/redirecting automatically
        event.preventDefault();
        event.stopPropagation();

        const form = event.currentTarget;
        const submitBtn = document.getElementById('loginBtn');
        const redirectUrl = form.getAttribute('data-redirect');

        // 3. Collect data from the IDs you provided
        const unameVal = document.getElementById('username').value;
        const pwordVal = document.getElementById('password').value;

        console.log("Attempting login for username:", unameVal);

        try {
            submitBtn.disabled = true;
            submitBtn.innerText = "Authenticating...";

            // 4. Call External API
            const response = await fetch(API_LOGIN_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: unameVal,
                    password: pwordVal
                })
            });

            const result = await response.json();
            console.log("External API Response:", result);

            if (result.status === "success") {
                // 5. Success! Now tell Tomcat to save the username and token in the Session
                console.log("External success. Syncing local session...");

                const sessionSync = await fetch(SESSION_BRIDGE_URL, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        username: unameVal,
                        token: result.message // The hash/token from the API
                    })
                });

                if (sessionSync.ok) {
                    console.log("Session established. Redirecting to:", redirectUrl);
                    window.location.href = redirectUrl;
                } else {
                    throw new Error("Local session bridge failed. Check your Servlet mapping.");
                }
            } else {
                // 6. Handle Login Failure (Wrong password, user not found, etc.)
                alert("Login Error: " + (result.message || "Invalid credentials"));
                submitBtn.disabled = false;
                submitBtn.innerText = "Sign In";
            }
        } catch (error) {
            console.error("Login Error Trace:", error);
            alert("Connection error: Could not reach the authentication services.");
            submitBtn.disabled = false;
            submitBtn.innerText = "Sign In";
        }
    };

    // 7. Explicitly attach the listener to the form ID
    const formElement = document.getElementById('loginForm');
    if (formElement) {
        formElement.onsubmit = handleLogin;
        console.log("Login listener successfully attached to #loginForm");
    } else {
        console.error("CRITICAL: Could not find #loginForm in the DOM. Check your JSP.");
    }
})();