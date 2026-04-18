/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      manna.js                                                        *
 * Created:   18/04/2026, 13:25                                               *
 * Modified:  18/04/2026, 13:25                                               *
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

async function fetchDailyBread() {
    const url = '/manna/api/dailybread/gift';
    try {
        const response = await fetch(url);
        const manna = await response.json();

        // Inject data into the card
        document.getElementById('manna-passage').innerText = manna.passage;
        document.getElementById('manna-verse').innerText = manna.verse;
        document.getElementById('manna-version').innerText = manna.version;

    } catch (error) {
        console.error("Manna fetch failed:", error);
        document.getElementById('manna-passage').innerText = "The connection to the Manna service was lost.";
    }
}

// Update your main entry point
document.addEventListener('DOMContentLoaded', () => {
    fetchMetrics();
    fetchHealth();
    fetchErrors();
    fetchDailyBread(); // New Daily Bread call
});