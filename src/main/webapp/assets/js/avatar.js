/******************************************************************************
 * This piece of work is to enhance ominet project functionality.             *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      avatar.js                                                       *
 * Created:   23/04/2026, 12:50                                               *
 * Modified:  23/04/2026, 12:50                                               *
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

async function fetchUserAvatar() {
    const uname = window.CURRENT_USER;
    const token = window.AUTH_TOKEN;
    const defaultAvatar = "assets/img/user/user.webp";
    const avatarImg = document.getElementById('nav-user-avatar');

    if (!uname || !token) return;

    const url = `/citizenone/api/profile/avatar/${uname}`;

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error("Avatar not found");

        const data = await response.json();

        // Check if avatar field exists and has content
        if (data.avatar && data.avatar.length > 100) {
            avatarImg.src = data.avatar;
        } else {
            avatarImg.src = defaultAvatar;
        }

    } catch (error) {
        console.warn("Avatar load failed, using default:", error);
        avatarImg.src = defaultAvatar;
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    fetchUserAvatar();
});