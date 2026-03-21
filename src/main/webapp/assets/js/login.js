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

    const submitBtn = document.getElementById('loginBtn');
    const form = event.currentTarget;
    const redirectUrl = form.getAttribute('data-redirect');

    const unameVal = document.getElementById('username').value;
    const pwordVal = document.getElementById('password').value;

    try {
        submitBtn.disabled = true;
        submitBtn.innerText = "Authenticating...";

        // --- STEP 1: External API (Port 9443) ---
        // This is what triggers your email notification
        const response = await fetch(OMINET_CONFIG.apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: unameVal, password: pwordVal })
        });

        const result = await response.json();

        if (result.status === "success") {
            submitBtn.innerText = "Saving Session...";

            // --- STEP 2: Local Session Sync ---
            // This is where the 400 error was happening
            const sessionSync = await fetch(OMINET_CONFIG.sessionUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: unameVal,
                    token: result.message
                })
            });

            if (sessionSync.ok) {
                // Success! Redirect to index.jsp
                window.location.href = redirectUrl;
            } else {
                // This means the Servlet crashed or wasn't found
                const errText = await sessionSync.text();
                console.error("Session Sync Failed:", errText);
                alert("Login successful (Email sent), but could not start local session.");
                submitBtn.disabled = false;
                submitBtn.innerText = "Sign In";
            }
        } else {
            alert("Login Failed: " + result.message);
            submitBtn.disabled = false;
            submitBtn.innerText = "Sign In";
        }
    } catch (err) {
        console.error("Network Error:", err);
        alert("Communication Error: Is the server running?");
        submitBtn.disabled = false;
        submitBtn.innerText = "Sign In";
    }
    return false;
}