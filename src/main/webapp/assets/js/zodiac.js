/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      zodiac.js                                                       *
 * Created:   18/04/2026, 21:17                                               *
 * Modified:  18/04/2026, 21:17                                               *
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

async function fetchHoroscope() {
    const uname = window.CURRENT_USER;
    const token = window.AUTH_TOKEN;

    // Safety check for session
    if (!uname || !token) {
        console.warn("Horoscope: Missing session data (User/Token)");
        return;
    }

    const url = `/persona/api/prime/announcer/${uname}`;

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        });

        // Handle empty or error responses
        if (!response.ok) {
            handleMissingHoroscope();
            return;
        }

        const data = await response.json();

        if (data.status === "unsuccessful") {
            handleMissingHoroscope();
        } else {
            renderHoroscope(data);
        }

    } catch (error) {
        console.error("Horoscope fetch failed:", error);
        handleMissingHoroscope();
    }
}

function handleMissingHoroscope() {
    const img = document.getElementById('zodiac-img');
    if (img) img.src = `assets/img/zodiac/astrology.jpg`;

    const today = new Date().toISOString().split('T')[0];

    document.getElementById('zodiac-date').innerText = today;
    document.getElementById('zodiac-name').innerText = "Awaiting Alignment";
    document.getElementById('zodiac-narrative').innerHTML =
        `Your celestial path is currently hidden. <br><br>
        <strong>Note:</strong> Once you update your profile details (including your date of birth), 
        your personalized daily horoscope will be revealed here.`;

    document.getElementById('horoscope-subtitle').innerText = "Profile update required";
}

function renderHoroscope(data) {
    console.log("Rendering Horoscope Data:", data);

    const sign = data.zodiacSign || 'Unknown';
    const signLower = sign.toLowerCase();

    const imgElement = document.getElementById('zodiac-img');
    if (imgElement) {
        imgElement.src = `assets/img/zodiac/${signLower}.jpg`;
    }

    document.getElementById('zodiac-name').innerText = sign;
    document.getElementById('zodiac-date').innerText = data.currentDay || "";
    document.getElementById('zodiac-narrative').innerText = data.narrative || "No narrative available for today.";
    document.getElementById('horoscope-subtitle').innerText = `Celestial guidance for ${sign}`;
}

// --- THE ENGINE START ---
document.addEventListener('DOMContentLoaded', fetchHoroscope);