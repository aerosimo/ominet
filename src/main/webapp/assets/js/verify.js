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

(function() {
    const API_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/verify";

    const handleVerification = async (event) => {
        // STOP THE REDIRECT IMMEDIATELY
        event.preventDefault();
        event.stopPropagation();

        const form = event.currentTarget;
        const inputField = document.getElementById('verify');
        const submitBtn = document.getElementById('verifyBtn');
        const redirectUrl = form.getAttribute('data-redirect');

        console.log("Form intercepted!");

        if (!inputField) {
            console.error("CRITICAL: Could not find input with ID 'verify'");
            return;
        }

        const tokenValue = inputField.value.trim();
        console.log("Token to send:", tokenValue);

        try {
            submitBtn.disabled = true;
            submitBtn.innerText = "Checking...";

            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ token: tokenValue })
            });

            const result = await response.json();
            console.log("Server Response:", result);

            if (result.status === "success") {
                console.log("Success! Redirecting to login...");
                window.location.href = redirectUrl;
            } else {
                alert("Verification Failed: " + result.message);
                submitBtn.disabled = false;
                submitBtn.innerText = "Verify";
            }
        } catch (error) {
            console.error("Fetch Error:", error);
            alert("Connection error. Is the API at 9443 reachable?");
            submitBtn.disabled = false;
            submitBtn.innerText = "Verify";
        }
    };

    // Attach to the form
    const formElement = document.getElementById('verifyForm');
    if (formElement) {
        formElement.onsubmit = handleVerification;
        console.log("Listener successfully attached to #verifyForm");
    } else {
        console.error("Could not find #verifyForm in the DOM!");
    }
})();