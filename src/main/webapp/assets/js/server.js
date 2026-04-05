/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      server.js                                                       *
 * Created:   05/04/2026, 20:32                                               *
 * Modified:  05/04/2026, 20:32                                               *
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

/**
 * Ominet Infraguard Pulse Monitor
 * Fetches server status every 30 seconds and updates the UI
 */
(function() {
    const PULSE_API = "/infraguard/api/guard/pulse";

    const UI_TEMPLATES = {
        online: `
            <span class="stat-change positive pulse-animate">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/></svg>
                online
            </span>`,
        offline: `
            <span class="stat-change negative pulse-animate">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 18 13.5 8.5 8.5 13.5 1 6"/></svg>
                offline
            </span>`
    };

    const updatePulse = async () => {
        try {
            const response = await fetch(PULSE_API);
            if (!response.ok) throw new Error("Infraguard Service Unreachable");

            const data = await response.json();

            // Iterate through: jenkins, tomee, linux, oracle
            Object.keys(data).forEach(serverKey => {
                const container = document.getElementById(`status-${serverKey}`);
                if (container) {
                    const status = data[serverKey].toLowerCase();
                    const newHTML = UI_TEMPLATES[status] || UI_TEMPLATES['offline'];

                    // Only update DOM if the status actually changed to save resources
                    if (container.innerHTML.trim() !== newHTML.trim()) {
                        container.style.opacity = '0.5'; // Brief flicker effect
                        setTimeout(() => {
                            container.innerHTML = newHTML;
                            container.style.opacity = '1';
                        }, 200);
                    }
                }
            });

        } catch (error) {
            console.error("Pulse Error:", error);
            // On total API failure, we leave the current UI but log it
        }
    };

    // Run on load and every 30 seconds
    updatePulse();
    setInterval(updatePulse, 30000);
})();