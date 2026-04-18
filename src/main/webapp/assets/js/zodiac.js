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

        if (!response.ok) throw new Error(`HTTP ${response.status}`);

        const data = await response.json();
        renderHoroscope(data);

    } catch (error) {
        console.error("Horoscope fetch failed:", error);
        document.getElementById('zodiac-name').innerText = "Celestial error";
        document.getElementById('zodiac-narrative').innerText = "Unable to read the stars at this moment.";
    }
}

function renderHoroscope(data) {
    const sign = data.zodiacSign; // e.g., "Scorpio"
    const signLower = sign.toLowerCase();

    // 1. Update Image
    document.getElementById('zodiac-img').src = `assets/img/zodiac/${signLower}.jpg`;

    // 2. Update Text Fields
    document.getElementById('zodiac-name').innerText = sign;
    document.getElementById('zodiac-date').innerText = data.currentDay;
    document.getElementById('zodiac-narrative').innerText = data.narrative;

    // 3. Update Subtitle for a personal touch
    document.getElementById('horoscope-subtitle').innerText = `A focused day ahead for ${sign}.`;
}

// Call inside your DOMContentLoaded listener
document.addEventListener('DOMContentLoaded', () => {
    // ... existing calls
    fetchHoroscope();
});