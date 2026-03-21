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

document.addEventListener('DOMContentLoaded', () => {
    const verifyForm = document.getElementById('verifyForm');
    if (!verifyForm) return;

    const API_VERIFY_URL = "https://ominet.aerosimo.com:9443/authcore/api/auth/verify";

    verifyForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const submitBtn = verifyForm.querySelector('button[type="submit"]');
        const errorAlert = document.getElementById('error-alert');
        const errorText = document.getElementById('error-message');
        const redirectUrl = verifyForm.getAttribute('data-redirect');

        // Reset UI
        errorAlert.style.display = 'none';
        submitBtn.disabled = true;
        const originalContent = submitBtn.innerHTML;
        submitBtn.innerText = "Verifying...";

        // Payload: Maps HTML 'verify' value to API 'token' key
        const payload = {
            token: document.getElementById('verify').value.trim()
        };

        try {
            const response = await fetch(API_VERIFY_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            const result = await response.json();

            if (response.ok && result.status === "success") {
                // SUCCESS: Proceed to login
                console.log("Verification success:", result.message);
                window.location.href = redirectUrl;
            } else {
                // ERROR: Show the "Invalid code" or "Expired" message from your API
                errorText.innerText = result.message || "Verification failed. Please try again.";
                errorAlert.style.display = 'block';
                submitBtn.disabled = false;
                submitBtn.innerHTML = originalContent;
            }

        } catch (error) {
            console.error("Connection Error:", error);
            errorText.innerText = "Cannot connect to Ominet Auth service.";
            errorAlert.style.display = 'block';
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalContent;
        }
    });
});