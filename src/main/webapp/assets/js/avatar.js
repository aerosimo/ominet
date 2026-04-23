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
    const avatarImg = document.getElementById('nav-user-avatar');
    const defaultAvatar = "assets/img/user/user.webp";

    if (!uname || !token) {
        console.warn("Avatar: Missing credentials.");
        return;
    }

    // Try both paths if one fails, or stick to the verified citizenone path
    const url = `https://ominet.aerosimo.com:9443/citizenone/api/profile/avatar/${uname}`;

    try {
        console.log(`Fetching avatar for ${uname}...`);
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': token,
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Server returned ${response.status}`);
        }

        const data = await response.json();

        if (data.avatar && data.avatar.startsWith('data:image')) {
            console.log("Avatar loaded successfully.");
            avatarImg.src = data.avatar;
        } else if (data.avatar) {
            // If the API sends pure base64 without the "data:image..." prefix
            console.log("Avatar missing prefix, adding it manually.");
            avatarImg.src = `data:image/png;base64,${data.avatar}`;
        } else {
            console.warn("Avatar field empty in JSON.");
            avatarImg.src = defaultAvatar;
        }

    } catch (error) {
        console.error("Avatar API Error:", error);
        avatarImg.src = defaultAvatar;
    }
}

// Ensure it runs
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fetchUserAvatar);
} else {
    fetchUserAvatar();
}