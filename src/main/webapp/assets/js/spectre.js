/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      spectre.js                                                      *
 * Created:   18/04/2026, 11:53                                               *
 * Modified:  18/04/2026, 11:53                                               *
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

async function fetchErrors() {
    const url = '/spectre/api/errors/retrieve?records=5';
    try {
        const response = await fetch(url);
        const errors = await response.json();
        renderErrorTable(errors);
    } catch (error) {
        console.error("Spectre fetch failed:", error);
    }
}

function renderErrorTable(errors) {
    const tbody = document.getElementById('errorTableBody');

    tbody.innerHTML = errors.map(err => {
        // Map Spectre Status to your CSS classes
        let statusClass = 'pending'; // Default
        const status = err.errorStatus.toUpperCase();

        if (status === 'RESOLVED' || status === 'CLOSED') statusClass = 'completed';
        else if (status === 'OPEN') statusClass = 'processing'; // Use Blue for Open
        else if (status === 'PENDING') statusClass = 'pending'; // Use Gold for Pending

        // Shorten the message if it's too long for the table cell
        const shortMsg = err.errorMessage.length > 50
            ? err.errorMessage.substring(0, 50) + '...'
            : err.errorMessage;

        // Extract time (HH:mm:ss)
        const timePart = err.errorTime.split(' ')[1].substring(0, 8);

        return `
            <tr>
                <td><strong>#${err.errorID}</strong></td>
                <td style="font-family: 'Space Mono', monospace; font-size: 12px;">${err.errorRef}</td>
                <td>${timePart}</td>
                <td><code style="color: var(--text-primary)">${err.errorCode}</code></td>
                <td title="${err.errorMessage}">${shortMsg}</td>
                <td style="font-size: 11px;">${err.errorService.split('.').pop()}</td>
                <td><span class="status-badge ${statusClass}">${err.errorStatus}</span></td>
                <td><span style="font-size: 11px; opacity: 0.8;">${err.errorSate}</span></td>
            </tr>
        `;
    }).join('');
}

// Update your initialization
document.addEventListener('DOMContentLoaded', () => {
    fetchMetrics();
    fetchHealth();
    fetchErrors(); // Initial load

    // Auto-refresh errors every 60 seconds
    setInterval(fetchErrors, 60000);
});