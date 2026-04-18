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

// Store data globally to switch views without re-fetching
let metricsData = null;

async function fetchMetrics() {
    try {
        const response = await fetch('/api/metric'); // Your Jersey Endpoint
        const data = await response.json();
        metricsData = data;
        // Default to Disk on first load
        updateChart('disk');
    } catch (error) {
        console.error("Failed to fetch metrics:", error);
    }
}

function updateChart(type) {
    const container = document.getElementById('chartBars');
    const yAxis = document.getElementById('yAxis');
    if (!metricsData) return;

    // Update Button UI
    document.querySelectorAll('.card-btn').forEach(btn => {
        btn.classList.remove('active');
        if(btn.innerText.toLowerCase().includes(type)) btn.classList.add('active');
    });

    let rawList = metricsData[type];

    // Reverse the data if your API is returning DESC (Newest first)
    // Charts usually read Left-to-Right (Oldest-to-Newest)
    const displayList = [...rawList].reverse();

    container.innerHTML = '';

    // Determine Max Value & Color Theme
    let maxValue = 100;
    let theme = 'bar-emerald';
    let unit = 'GB';

    if (type === 'disk') {
        maxValue = Math.max(...displayList.map(d => parseFloat(d.total)));
        theme = 'bar-emerald';
    } else if (type === 'memory') {
        maxValue = Math.max(...displayList.map(d => parseFloat(d.max)));
        theme = 'bar-gold';
    } else {
        // CPU Time is large, we'll scale it based on max found in set
        maxValue = Math.max(...displayList.map(d => parseInt(d.cpuTime)));
        theme = 'bar-sapphire';
        unit = '';
    }

    // Update Y-Axis Labels
    yAxis.innerHTML = `
        <span class="y-value">${maxValue.toFixed(0)}${unit}</span>
        <span class="y-value">${(maxValue * 0.75).toFixed(0)}${unit}</span>
        <span class="y-value">${(maxValue * 0.5).toFixed(0)}${unit}</span>
        <span class="y-value">${(maxValue * 0.25).toFixed(0)}${unit}</span>
        <span class="y-value">0</span>
    `;

    // Generate Bars
    displayList.forEach(item => {
        let value = 0;
        let timeLabel = "";

        // Map data fields based on type
        if (type === 'disk') {
            value = parseFloat(item.total) - parseFloat(item.free); // Show used space
            timeLabel = item.modifiedDate ? item.modifiedDate.split(' ')[1].substring(0, 5) : "--:--";
        } else if (type === 'memory') {
            value = parseFloat(item.used);
            timeLabel = item.modifiedDate ? item.modifiedDate.split(' ')[1].substring(0, 5) : "--:--";
        } else {
            value = parseInt(item.cpuTime);
            timeLabel = item.modifiedDate ? item.modifiedDate.split(' ')[1].substring(0, 5) : "--:--";
        }

        const heightPercentage = (value / maxValue) * 100;

        const barGroup = document.createElement('div');
        barGroup.className = 'chart-bar-group';
        barGroup.innerHTML = `
            <div class="chart-bar ${theme}" 
                 style="height: ${Math.max(heightPercentage, 5)}%;" 
                 title="${value.toFixed(2)}${unit} at ${timeLabel}">
            </div>
            <span class="chart-label">${timeLabel}</span>
        `;
        container.appendChild(barGroup);
    });
}

// Initialize on load
fetchMetrics();