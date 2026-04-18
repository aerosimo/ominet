/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      metrics.js                                                      *
 * Created:   18/04/2026, 10:46                                               *
 * Modified:  18/04/2026, 10:46                                               *
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
    fetchMetrics();
});

let metricsData = null;

async function fetchMetrics() {
    try {
        const response = await fetch('/api/metric');
        metricsData = await response.json();
        console.log("Data Received:", metricsData); // Debug check
        updateChart('disk');
    } catch (error) {
        document.getElementById('chartBars').innerHTML = `<p style="color:red; font-size:12px;">Failed to load metrics</p>`;
    }
}

function updateChart(type) {
    const container = document.getElementById('chartBars');
    const yAxis = document.getElementById('yAxis');
    if (!metricsData || !metricsData[type]) return;

    // Toggle Button UI
    document.querySelectorAll('.card-btn').forEach(btn => btn.classList.remove('active'));
    event?.target?.classList.add('active');

    const displayList = [...metricsData[type]].reverse();
    container.innerHTML = '';

    // Helper to strip "GB" and return a float
    const cleanNum = (val) => parseFloat(String(val).replace(/[^\d.-]/g, '')) || 0;

    // Calculate Scale
    let maxValue = 0;
    let theme = 'bar-emerald';
    let unit = 'GB';

    if (type === 'disk') {
        maxValue = Math.max(...displayList.map(d => cleanNum(d.total)));
        theme = 'bar-emerald';
    } else if (type === 'memory') {
        maxValue = Math.max(...displayList.map(d => cleanNum(d.max)));
        theme = 'bar-gold';
    } else {
        maxValue = Math.max(...displayList.map(d => cleanNum(d.cpuTime)));
        theme = 'bar-sapphire';
        unit = 'ns';
    }

    // Safety check for divide by zero
    if (maxValue === 0) maxValue = 100;

    // Update Y-Axis
    yAxis.innerHTML = `
        <span class="y-value">${Math.round(maxValue)}${unit}</span>
        <span class="y-value">${Math.round(maxValue * 0.5)}${unit}</span>
        <span class="y-value">0</span>
    `;

    // Render Bars
    displayList.forEach(item => {
        let val = 0;
        let dateStr = item.modifiedDate || "00:00:00";
        let label = dateStr.includes(' ') ? dateStr.split(' ')[1].substring(0, 5) : dateStr.substring(0, 5);

        if (type === 'disk') val = cleanNum(item.total) - cleanNum(item.free);
        else if (type === 'memory') val = cleanNum(item.used);
        else val = cleanNum(item.cpuTime);

        const pct = (val / maxValue) * 100;

        const group = document.createElement('div');
        group.className = 'chart-bar-group';
        group.innerHTML = `
            <div class="chart-bar ${theme}" style="height: ${Math.max(pct, 2)}%;"></div>
            <span class="chart-label">${label}</span>
        `;
        container.appendChild(group);
    });
}