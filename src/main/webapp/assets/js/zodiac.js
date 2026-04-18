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

    if (!uname || !token) return;

    const url = `/persona/api/prime/announcer/${uname}`;

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();

        // 1. Check if the backend sent the "unsuccessful" DTO
        if (!response.ok || data.status === "unsuccessful") {
            handleMissingHoroscope();
            return;
        }

        // 2. Otherwise, render normally
        renderHoroscope(data);

    } catch (error) {
        console.error("Horoscope fetch failed:", error);
        handleMissingHoroscope(); // Default to fallback on network error too
    }
}

function handleMissingHoroscope() {
    // A. Set default Image
    document.getElementById('zodiac-img').src = `assets/img/zodiac/astrology.jpg`;

    // B. Set current date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('zodiac-date').innerText = today;

    // C. Set fallback text
    document.getElementById('zodiac-name').innerText = "Awaiting Alignment";
    document.getElementById('zodiac-narrative').innerHTML =
        `Your celestial path is currently hidden. <br><br>
        <strong>Note:</strong> Once you update your profile details (including your date of birth), 
        your personalized daily horoscope will be revealed here.`;

    document.getElementById('horoscope-subtitle').innerText = "Profile update required";
}