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
    event.preventDefault();
    event.stopPropagation();

    console.log("🚀 Login Process Started");

    const form = event.currentTarget;
    const submitBtn = document.getElementById('loginBtn');
    const redirectUrl = form.getAttribute('data-redirect');

    const unameVal = document.getElementById('username').value;
    const pwordVal = document.getElementById('password').value;

    try {
        submitBtn.disabled = true;
        submitBtn.innerText = "Connecting to API...";

        // --- STEP 1: CALL EXTERNAL API ---
        console.log("📡 Calling External API...");
        const response = await fetch("https://ominet.aerosimo.com:9443/authcore/api/auth/login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: unameVal, password: pwordVal })
        });

        console.log("✅ API responded with status:", response.status);
        const result = await response.json();
        console.log("📦 API Data received:", result);

        if (result.status === "success") {
            submitBtn.innerText = "Creating Session...";

            // --- STEP 2: CALL LOCAL TOMCAT SERVLET ---
            console.log("📡 Calling Local Session Bridge...");

            // Use an absolute path from the root to avoid "hanging" on 404s
            const sessionSync = await fetch("<%= request.getContextPath() %>/auth/set-session", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: unameVal,
                    token: result.message
                })
            });

            if (sessionSync.ok) {
                console.log("🎉 Session Sync Success! Redirecting...");
                window.location.href = redirectUrl;
            } else {
                console.error("❌ Session Bridge Failed:", sessionSync.status);
                alert("Internal Server Error: Could not establish session.");
            }
        } else {
            console.warn("⚠️ API returned failure:", result.message);
            alert("Login Failed: " + result.message);
        }

    } catch (error) {
        // This catches Network errors, CORS issues, and DNS failures
        console.error("🚨 CRITICAL ERROR:", error);
        alert("Connection Error: " + error.message);
    } finally {
        // Always re-enable the button if we didn't redirect
        console.log("🏁 Process Finished.");
        submitBtn.disabled = false;
        submitBtn.innerText = "Sign In";
    }

    return false;
}