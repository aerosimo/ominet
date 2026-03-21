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

const API_LOGIN_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/login";
const LOCAL_SESSION_URL = "<%= request.getContextPath() %>/auth/set-session";

document.querySelector('form[data-validate]').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const submitBtn = form.querySelector('button[type="submit"]');

    // 1. Prepare Request (Using username as requested)
    const uname = document.getElementById('username').value; // Ensure your input ID is 'username'
    const pword = document.getElementById('password').value;

    try {
        submitBtn.disabled = true;

        // 2. Call External Auth API
        const response = await fetch(API_LOGIN_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: uname, password: pword })
        });

        const result = await response.json();

        if (result.status === "success") {
            // 3. INTERNAL STEP: Tell Tomcat to remember this user
            const sessionSync = await fetch(LOCAL_SESSION_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: uname,
                    token: result.message // This is your long hash
                })
            });

            if (sessionSync.ok) {
                // 4. Final Redirect
                window.location.href = form.getAttribute('data-redirect');
            }
        } else {
            alert("Login Failed: " + result.message);
            submitBtn.disabled = false;
        }
    } catch (err) {
        console.error(err);
        submitBtn.disabled = false;
    }
});