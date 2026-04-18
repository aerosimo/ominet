/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      status.js                                                       *
 * Created:   18/04/2026, 14:23                                               *
 * Modified:  18/04/2026, 14:23                                               *
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

async function fetchProfileStatus() {
    // uname is typically passed from the server side or stored in a global JS var
    const uname = "${sessionScope.user}";
    const url = `/persona/api/prime/flow/${uname}`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        // 1. Extract the number from the string "Profile completion: 4.55%"
        const percentage = parseFloat(data.message.match(/[\d.]+/)[0]) || 0;

        updateProfileDonut(percentage);
    } catch (error) {
        console.error("Profile fetch failed:", error);
    }
}

function updateProfileDonut(percent) {
    const segment = document.getElementById('profile-segment');
    const valueText = document.getElementById('profile-value');
    const message = document.getElementById('profile-message');
    const legendColor = document.getElementById('profile-legend-color');

    // 2. SVG Math: Circumference = 2 * PI * R (54) = ~339.29
    const circumference = 339.29;
    const offset = (percent / 100) * circumference;

    // Update SVG and Text
    segment.setAttribute('stroke-dasharray', `${offset} ${circumference}`);
    valueText.innerText = `${Math.round(percent)}%`;

    // 3. Update UI feedback based on completion
    if (percent >= 100) {
        message.innerText = "Congratulations, your profile is complete!";
        legendColor.className = "legend-color cyan";
        segment.setAttribute('stroke', 'var(--emerald-light)');
    } else {
        message.innerText = "Update missing info in settings";
        legendColor.className = "legend-color magenta";
        segment.setAttribute('stroke', 'var(--gold)');
    }
}

// Call inside your DOMContentLoaded listener
document.addEventListener('DOMContentLoaded', () => {
    // ... previous calls ...
    fetchProfileStatus();
});