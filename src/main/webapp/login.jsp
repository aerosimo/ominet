<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance ominet project functionality.            ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      login.jsp                                                      ~
  ~ Created:   21/03/2026, 02:29                                              ~
  ~ Modified:  21/03/2026, 02:29                                              ~
  ~                                                                           ~
  ~ Copyright (c)  2026.  Aerosimo Ltd                                        ~
  ~                                                                           ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a   ~
  ~ copy of this software and associated documentation files (the "Software"),~
  ~ to deal in the Software without restriction, including without limitation ~
  ~ the rights to use, copy, modify, merge, publish, distribute, sublicense,  ~
  ~ and/or sell copies of the Software, and to permit persons to whom the     ~
  ~ Software is furnished to do so, subject to the following conditions:      ~
  ~                                                                           ~
  ~ The above copyright notice and this permission notice shall be included   ~
  ~ in all copies or substantial portions of the Software.                    ~
  ~                                                                           ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,           ~
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES           ~
  ~ OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                  ~
  ~ NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                ~
  ~ HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,              ~
  ~ WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING              ~
  ~ FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                ~
  ~ OR OTHER DEALINGS IN THE SOFTWARE.                                        ~
  ~                                                                           ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <!-- Title -->
    <title>Login | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap -->
    <link href="assets/css/style.css" rel="stylesheet"/>
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link crossorigin href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&family=Space+Mono:wght@400;700&display=swap"
          rel="stylesheet">
</head>
<body>
<!-- Animated Background -->
<div class="background"></div>
<div class="orb orb-1"></div>
<div class="orb orb-2"></div>
<div class="orb orb-3"></div>

<div class="auth-page">
    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <div class="sidebar-logo">
                    <img alt="Ominet Logo" class="logo-img" src="assets/img/logo/logo.png">
                </div>
                <h1 class="auth-title">Sign In</h1>
                <p class="auth-subtitle">Sign in and start managing your apps. 👋</p>
            </div>

            <div id="error-alert" class="auth-subtitle" style="display:none; color: #ff4d4d; background: rgba(255,77,77,0.1); padding: 12px; border-radius: 8px; margin-bottom: 15px; border: 1px solid #ff4d4d; font-size: 14px;">
                ⚠️ <span id="error-message"></span>
            </div>

            <form id="loginForm" data-redirect="index.jsp" onsubmit="return handleLogin(event)" data-validate>
                <div class="form-group">
                    <label class="form-label" for="username">👤 Username</label>
                    <input class="form-input"
                               autofocus
                               id="username"
                               name="username"
                               placeholder="Enter your username"
                               required
                               title="Alternatively referred to as an account name, login ID, nickname, and user ID"
                               pattern="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"
                               type="text">
                </div>

                <div class="form-group">
                    <label class="form-label" for="password">🔒 Password</label>
                    <input class="form-input"
                           id="password"
                           name="password"
                           autocomplete="off"
                           minlength="8"
                           placeholder="Enter your password"
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                           required
                           type="password">
                </div>

                <div class="form-row">
                    <label class="checkbox-label">
                        <input checked type="checkbox">
                        Remember me
                    </label>
                    <a class="forgot-link" href="forgot.jsp">Forgot Password?</a>
                </div>

                <button id="loginBtn" class="btn btn-primary" type="submit">
                    Sign In
                    <svg fill="none" height="18" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" width="18">
                        <line x1="5" x2="19" y1="12" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </button>
            </form>

            <div class="divider">
                <span>Or don't have an account?</span>
            </div>

            <p class="auth-footer">
                <a href="register.jsp">Create Account</a>
            </p>
        </div>
    </div>

    <!-- Footer -->
    <footer class="site-footer">
        <div class="copy">&copy;
            <script>document.write(new Date().getFullYear());</script>
            Ominet by Aerosimo Ltd. All rights reserved.
        </div>
    </footer>
</div>

<script src="assets/js/script.js"></script>
<script src="assets/js/login.js"></script>
</body>
</html>