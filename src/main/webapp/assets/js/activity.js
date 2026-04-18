/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      activity.js                                                     *
 * Created:   18/04/2026, 11:30                                               *
 * Modified:  18/04/2026, 11:30                                               *
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

async function fetchHealth() {
    const url = '/infraguard/api/guard/health';
    try {
        const response = await fetch(url);
        const health = await response.json();
        renderActivityFeed(health);
    } catch (error) {
        console.error("Health fetch failed:", error);
    }
}

function renderActivityFeed(data) {
    const feed = document.getElementById('activityFeed');
    document.getElementById('hostname-subtitle').innerText = data.hostname;

    // Define the items we want to show based on the JSON keys
    const items = [
        {
            icon: '🚀',
            text: `System is <strong>${data.status}</strong>`,
            sub: `Total Uptime: ${data.uptime}`,
            theme: 'linear-gradient(135deg, var(--emerald-light), var(--emerald))'
        },
        {
            icon: '📊',
            text: `Current System Load: <strong>${parseFloat(data.load).toFixed(4)}</strong>`,
            sub: 'CPU scheduler average',
            theme: 'linear-gradient(135deg, var(--sapphire), var(--indigo))'
        },
        {
            icon: '🔌',
            text: `<strong>${data.connections}</strong> Active Connections`,
            sub: 'Live sessions on Ominet',
            theme: 'linear-gradient(135deg, var(--gold), var(--amber))'
        },
        {
            icon: '🏠',
            text: `Node: ${data.hostname}`,
            sub: 'Primary host identified',
            theme: 'linear-gradient(135deg, var(--teal), var(--emerald))'
        }
    ];

    feed.innerHTML = items.map(item => `
        <div class="activity-item">
            <div class="activity-avatar" style="background: ${item.theme};">${item.icon}</div>
            <div class="activity-content">
                <p class="activity-text">${item.text}</p>
                <span class="activity-time">${item.sub}</span>
            </div>
        </div>
    `).join('');
}

// Call this alongside your fetchMetrics()
document.addEventListener('DOMContentLoaded', () => {
    fetchMetrics();
    fetchHealth();

    // Refresh health every 30 seconds for real-time feel
    setInterval(fetchHealth, 30000);
});