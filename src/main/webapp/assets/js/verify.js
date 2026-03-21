/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      verify.js                                                       *
 * Created:   21/03/2026, 03:02                                               *
 * Modified:  21/03/2026, 03:02                                               *
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

// This wrapper ensures the script waits for the HTML to load
(function() {
    console.log("Verify script initialized.");

    const initVerify = () => {
        const form = document.querySelector('form[data-redirect="login.jsp"]');

        if (!form) {
            console.error("Verification form not found! Check if data-redirect is set.");
            return;
        }

        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            console.log("Form submitted. Intercepting...");

            const inputField = document.getElementById('verify');
            const submitBtn = form.querySelector('button[type="submit"]');

            // Collect value
            const codeValue = inputField.value.trim();
            console.log("Collected Token:", codeValue);

            if (codeValue.length !== 10) {
                alert("Please enter all 10 characters.");
                return;
            }

            const API_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/verify";
            const redirectUrl = form.getAttribute('data-redirect');

            try {
                submitBtn.disabled = true;
                submitBtn.innerText = "Verifying...";

                console.log("Sending request to:", API_URL);

                const response = await fetch(API_URL, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify({ token: codeValue })
                });

                console.log("Response status:", response.status);

                const result = await response.json();
                console.log("API Result:", result);

                if (result.status === "success") {
                    console.log("Success! Redirecting to:", redirectUrl);
                    window.location.href = redirectUrl;
                } else {
                    console.warn("Verification failed:", result.message);
                    alert("Error: " + result.message);
                    submitBtn.disabled = false;
                    submitBtn.innerText = "Verify";
                }

            } catch (error) {
                console.error("Network or Fetch error:", error);
                alert("Could not reach the verification server.");
                submitBtn.disabled = false;
                submitBtn.innerText = "Verify";
            }
        });
    };

    // Run init
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initVerify);
    } else {
        initVerify();
    }
})();