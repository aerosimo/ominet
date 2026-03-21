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
    const API_LOGIN_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/login";
    const SESSION_BRIDGE_URL = "auth/set-session"; // Your local Tomcat Servlet

    const handleLogin = async (event) => {
        event.preventDefault(); // Stop the redirect immediately!
        event.stopPropagation();

        const form = event.currentTarget;
        const submitBtn = document.getElementById('loginBtn');
        const redirectUrl = form.getAttribute('data-redirect');

        // Extracting data
        const emailVal = document.getElementById('email').value;
        const passwordVal = document.getElementById('password').value;

        console.log("Login attempt for:", emailVal);

        try {
            submitBtn.disabled = true;
            submitBtn.innerText = "Authenticating...";

            // 1. Call External API
            const response = await fetch(API_LOGIN_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: emailVal, // Using email input as 'username' per your API spec
                    password: passwordVal
                })
            });

            const result = await response.json();
            console.log("API Response:", result);

            if (result.status === "success") {
                // 2. Call Local Servlet to set session
                console.log("Setting local session...");
                const sessionResponse = await fetch(SESSION_BRIDGE_URL, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        username: emailVal,
                        token: result.message
                    })
                });

                if (sessionResponse.ok) {
                    console.log("Success! Redirecting...");
                    window.location.href = redirectUrl;
                } else {
                    throw new Error("Local session failed to start.");
                }
            } else {
                alert("Login Failed: " + result.message);
                submitBtn.disabled = false;
                submitBtn.innerText = "Sign In";
            }
        } catch (error) {
            console.error("Login Error:", error);
            alert("Could not complete login. Check console for details.");
            submitBtn.disabled = false;
            submitBtn.innerText = "Sign In";
        }
    };

    // Attach listener
    const formElement = document.getElementById('loginForm');
    if (formElement) {
        formElement.onsubmit = handleLogin;
        console.log("Login listener attached.");
    }
})();