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

async function handleLogin(event) {
    // 1. IMMEDIATELY kill the browser's default redirect
    event.preventDefault();
    event.stopPropagation();

    console.log("Login intercept triggered.");

    const form = event.currentTarget;
    const submitBtn = document.getElementById('loginBtn');
    const redirectUrl = form.getAttribute('data-redirect');

    // 2. Collect data
    const unameVal = document.getElementById('username').value;
    const pwordVal = document.getElementById('password').value;

    try {
        submitBtn.disabled = true;
        submitBtn.innerText = "Authenticating...";

        // 3. Call External API
        const response = await fetch("https://ominet.aerosimo.com:9443/authcore/api/auth/login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                username: unameVal,
                password: pwordVal
            })
        });

        const result = await response.json();
        console.log("API Result:", result);

        // 4. CHECK SUCCESS
        if (result.status === "success") {
            console.log("Success! Syncing Session...");

            const sessionSync = await fetch("<%= request.getContextPath() %>/auth/set-session", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: unameVal,
                    token: result.message
                })
            });

            if (sessionSync.ok) {
                window.location.href = redirectUrl;
                return false;
            }
        } else {
            // 5. FAILURE: Show the error and STOP the redirect
            console.error("Login failed:", result.message);
            alert("Error: " + (result.message || "Invalid Username/Password"));

            submitBtn.disabled = false;
            submitBtn.innerText = "Sign In";
            return false; // Tells the HTML form: DO NOT SUBMIT
        }
    } catch (error) {
        console.error("Network Error:", error);
        alert("Server unreachable.");
        submitBtn.disabled = false;
        submitBtn.innerText = "Sign In";
        return false;
    }

    return false; // Ultimate fallback to prevent redirect
}