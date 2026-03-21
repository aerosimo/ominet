/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      register.js                                                     *
 * Created:   21/03/2026, 01:47                                               *
 * Modified:  21/03/2026, 01:47                                               *
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

    // Config: If you move this to production, you only change it here
    const API_BASE_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth";

    document.querySelector('form[data-validate]').addEventListener('submit', async (e) => {
        e.preventDefault();

        const form = e.target;
        const submitBtn = form.querySelector('button[type="submit"]');
        const redirectUrl = form.getAttribute('data-redirect');

        // Mapping your HTML IDs to the API Request Body
        const payload = {
            username: document.getElementById('username').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        };

        try {
            // Visual feedback
            submitBtn.disabled = true;
            submitBtn.innerHTML = 'Creating Account... <span class="spinner"></span>';

            const response = await fetch(`${API_BASE_URL}/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            // Parse the response using the structure you provided
            const result = await response.json();

            if (response.ok && result.status === "success") {
                console.log("Registration Code Received:", result.message);

                // Optional: Store the message/code in session storage if login.jsp needs to show it
                sessionStorage.setItem('reg_msg', result.message);

                // Redirect to login.html (or login.jsp)
                window.location.href = redirectUrl;
            } else {
                // Handle API errors (e.g. user already exists)
                alert("Registration Error: " + (result.message || "Please check your details."));
            }

        } catch (error) {
            console.error("Network/CORS Error:", error);
            alert("Could not connect to the Auth Service. Please ensure the API is reachable.");
        } finally {
            submitBtn.disabled = false;
            submitBtn.innerText = 'Create Account';
        }
    });