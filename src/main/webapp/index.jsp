<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance ominet project functionality.            ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      index.jsp                                                      ~
  ~ Created:   21/03/2026, 03:07                                              ~
  ~ Modified:  21/03/2026, 03:07                                              ~
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

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Title -->
    <title>Dashboard | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="assets/css/style.css" />
    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&family=Space+Mono:wght@400;700&display=swap">
</head>
<body>
<!-- Animated Background -->
<div class="background"></div>
<div class="orb orb-1"></div>
<div class="orb orb-2"></div>
<div class="orb orb-3"></div>

<div class="dashboard">
    <!-- Sidebar -->
    <aside class="sidebar" id="sidebar">
        <div class="sidebar-logo">
            <img src="assets/img/logo/logo.png" alt="Ominet Logo" class="logo-img">
        </div>

        <ul class="nav-menu">
            <li class="nav-section">
                <span class="nav-section-title">Main Menu</span>
                <ul>
                    <li class="nav-item">
                        <a href="index.jsp" class="nav-link active">
                            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <rect x="3" y="3" width="7" height="7" rx="1"/>
                                <rect x="14" y="3" width="7" height="7" rx="1"/>
                                <rect x="3" y="14" width="7" height="7" rx="1"/>
                                <rect x="14" y="14" width="7" height="7" rx="1"/>
                            </svg>
                            Dashboard
                            <span class="nav-badge">Current</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="settings.jsp" class="nav-link">
                            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <circle cx="12" cy="12" r="3"/>
                                <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"/>
                            </svg>
                            Settings
                        </a>
                    </li>
                </ul>
            </li>

            <li class="nav-section">
                <span class="nav-section-title">Account</span>
                <ul>
                    <li class="nav-item">
                        <a href="logout.jsp" class="nav-link">
                            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                                <polyline points="16 17 21 12 16 7"/>
                                <line x1="21" y1="12" x2="9" y2="12"/>
                            </svg>
                            Logout
                        </a>
                    </li>
                </ul>
            </li>
        </ul>

        <div class="sidebar-footer">
            <div class="user-profile">
                <div class="user-avatar">O</div>
                <div class="user-info">
                    <div class="user-name">OmniProv</div>
                    <div class="user-role">Dashboard</div>
                </div>
            </div>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Top Navbar -->
        <nav class="navbar">
            <h1 class="page-title">Dashboard Overview</h1>
            <div class="navbar-right">
                <div class="user-menu">
                    <img src="assets/img/user/user.webp" alt="User Avatar" class="avatar">
                </div>
            </div>
        </nav>

        <!-- Stats Cards -->
        <section class="stats-grid">
            <div class="glass-card glass-card-3d stat-card">
                <div class="stat-card-inner">
                    <div class="stat-info">
                        <h3>Oracle Linux</h3>
                        <div class="stat-value">10</div>
                        <span class="stat-change positive">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/></svg>
                                online
                            </span>
                    </div>
                    <div class="server-menu">
                        <img src="assets/img/server/linux.png" alt="Oracle Linux Server" class="server">
                    </div>
                </div>
            </div>

            <div class="glass-card glass-card-3d stat-card">
                <div class="stat-card-inner">
                    <div class="stat-info">
                        <h3>Jenkins CI / CD</h3>
                        <div class="stat-value">2</div>
                        <span class="stat-change positive">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/></svg>
                                online
                            </span>
                    </div>
                    <div class="server-menu">
                        <img src="assets/img/server/jenkins.png" alt="Jenkins Server" class="server">
                    </div>
                </div>
            </div>

            <div class="glass-card glass-card-3d stat-card">
                <div class="stat-card-inner">
                    <div class="stat-info">
                        <h3>Oracle Database 26ai</h3>
                        <div class="stat-value">26</div>
                        <span class="stat-change negative">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 18 13.5 8.5 8.5 13.5 1 6"/></svg>
                                offline
                            </span>
                    </div>
                    <div class="server-menu">
                        <img src="assets/img/server/oracle.jpeg" alt="Oracle Database" class="server">
                    </div>
                </div>
            </div>

            <div class="glass-card glass-card-3d stat-card">
                <div class="stat-card-inner">
                    <div class="stat-info">
                        <h3>Apache Tomcat 11</h3>
                        <div class="stat-value">11</div>
                        <span class="stat-change positive">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/></svg>
                                online
                            </span>
                    </div>
                    <div class="server-menu">
                        <img src="assets/img/server/tomee.png" alt="Apache Tomcat Server" class="server">
                    </div>
                </div>
            </div>
        </section>

        <!-- Content Grid -->
        <section class="content-grid">
            <!-- Chart Card -->
            <div class="glass-card chart-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">System Metrics</h2>
                        <p class="card-subtitle">Realtime performance indicators</p>
                    </div>
                    <div class="card-actions">
                        <button class="card-btn active">Disk Usage</button>
                        <button class="card-btn">Memory Usage</button>
                        <button class="card-btn">CPU Thread Activity</button>
                    </div>
                </div>
                <div class="chart-wrapper">
                    <div class="chart-container">
                        <div class="chart-y-axis">
                            <span class="y-value">$100K</span>
                            <span class="y-value">$80K</span>
                            <span class="y-value">$60K</span>
                            <span class="y-value">$40K</span>
                            <span class="y-value">$20K</span>
                            <span class="y-value">$0</span>
                        </div>
                        <div class="chart-placeholder">
                            <div class="chart-bar-group"><div class="chart-bar bar-emerald" style="height: 120px;"></div><span class="chart-label">Jan</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-gold" style="height: 160px;"></div><span class="chart-label">Feb</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-sapphire" style="height: 90px;"></div><span class="chart-label">Mar</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-ruby" style="height: 140px;"></div><span class="chart-label">Apr</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-amethyst" style="height: 180px;"></div><span class="chart-label">May</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-teal" style="height: 130px;"></div><span class="chart-label">Jun</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-orange" style="height: 170px;"></div><span class="chart-label">Jul</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-indigo" style="height: 150px;"></div><span class="chart-label">Aug</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-magenta" style="height: 190px;"></div><span class="chart-label">Sep</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-slate" style="height: 140px;"></div><span class="chart-label">Oct</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-lime" style="height: 175px;"></div><span class="chart-label">Nov</span></div>
                            <div class="chart-bar-group"><div class="chart-bar bar-bronze" style="height: 200px;"></div><span class="chart-label">Dec</span></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Activity Feed -->
            <div class="glass-card activity-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">Alerts & Logs</h2>
                        <p class="card-subtitle">Latest transactions</p>
                    </div>
                </div>
                <div class="activity-list">
                    <div class="activity-item">
                        <div class="activity-avatar" style="background: linear-gradient(135deg, var(--emerald-light), var(--emerald));">⚠️</div>
                        <div class="activity-content">
                            <p class="activity-text">High CPU usage detected at 10:20</p>
                            <span class="activity-time">2 minutes ago</span>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-avatar" style="background: linear-gradient(135deg, var(--gold), var(--amber));">ℹ️</div>
                        <div class="activity-content">
                            <p class="activity-text">User admin logged in at 10:05</p>
                            <span class="activity-time">15 minutes ago</span>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-avatar" style="background: linear-gradient(135deg, var(--coral), var(--gold));">✅</div>
                        <div class="activity-content">
                            <p class="activity-text">Backup completed successfully</p>
                            <span class="activity-time">1 hour ago</span>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-avatar" style="background: linear-gradient(135deg, var(--gold), var(--amber));">ℹ️</div>
                        <div class="activity-content">
                            <p class="activity-text">Scheduled job completed</p>
                            <span class="activity-time">2 hours ago</span>
                        </div>
                    </div>
                    <div class="activity-item">
                        <div class="activity-avatar" style="background: linear-gradient(135deg, var(--emerald-light), var(--emerald));">⚠️</div>
                        <div class="activity-content">
                            <p class="activity-text">Network latency spike detected</p>
                            <span class="activity-time">3 hours ago</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Data Table -->
            <div class="glass-card table-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">Error Intelligence</h2>
                        <p class="card-subtitle">Latest critical & recurring errors</p>
                    </div>
                </div>
                <div class="table-wrapper">
                    <table class="data-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Reference</th>
                            <th>Time</th>
                            <th>Code</th>
                            <th>Message</th>
                            <th>Service</th>
                            <th>Status</th>
                            <th>State</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><div class="table-user"><div class="table-user-info"><span class="table-user-name">John Doe</span><span class="table-user-email">john@example.com</span></div></div></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td><span class="status-badge completed">Completed</span></td>
                            <td><span class="table-amount">$299.00</span></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td>Jan 15, 2025</td>
                        </tr>
                        <tr>
                            <td><div class="table-user"><div class="table-user-info"><span class="table-user-name">Anna Smith</span><span class="table-user-email">anna@example.com</span></div></div></td>
                            <td>Enterprise License</td>
                            <td>Jan 14, 2025</td>
                            <td><span class="status-badge processing">Processing</span></td>
                            <td><span class="table-amount">$1,499.00</span></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td>Jan 15, 2025</td>
                        </tr>
                        <tr>
                            <td><div class="table-user"><div class="table-user-info"><span class="table-user-name">Mike Johnson</span><span class="table-user-email">mike@example.com</span></div></div></td>
                            <td>Team Bundle</td>
                            <td>Jan 13, 2025</td>
                            <td><span class="status-badge completed">Completed</span></td>
                            <td><span class="table-amount">$599.00</span></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td>Jan 15, 2025</td>
                        </tr>
                        <tr>
                            <td><div class="table-user"><div class="table-user-info"><span class="table-user-name">Emily White</span><span class="table-user-email">emily@example.com</span></div></div></td>
                            <td>Starter Plan</td>
                            <td>Jan 12, 2025</td>
                            <td><span class="status-badge pending">Pending</span></td>
                            <td><span class="table-amount">$49.00</span></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td>Jan 15, 2025</td>
                        </tr>
                        <tr>
                            <td><div class="table-user"><div class="table-user-info"><span class="table-user-name">Robert Brown</span><span class="table-user-email">robert@example.com</span></div></div></td>
                            <td>Pro Annual</td>
                            <td>Jan 11, 2025</td>
                            <td><span class="status-badge completed">Completed</span></td>
                            <td><span class="table-amount">$199.00</span></td>
                            <td>Premium Plan</td>
                            <td>Jan 15, 2025</td>
                            <td>Jan 15, 2025</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <!-- Bottom Grid -->
        <section class="bottom-grid">

            <!-- Daily Bread -->
            <div class="glass-card progress-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">Daily Bread</h2>
                        <p class="card-subtitle">Daily Manna for personal development and reflection</p>
                    </div>
                </div>
                <div class="progress-item">
                    <div class="progress-header"><span class="progress-label">Passage</span></div>
                    <div class="progress-bar"><div class="progress-fill cyan" style="width: 100%;"></div></div>
                    <div class="legend-item"><span class="legend-color cyan"></span><span>To those who by persistence in doing good seek glory, honor and immortality, he will give eternal life. But for those who are self-seeking and who reject the truth and follow evil, there will be wrath and anger.</span></div>
                </div>
                <div class="progress-item">
                    <div class="progress-header"><span class="progress-label">Verse</span></div>
                    <div class="progress-bar"><div class="progress-fill magenta" style="width: 100%;"></div></div>
                    <div class="legend-item"><span class="legend-color magenta"></span><span>Romans 2:7-8</span></div>
                </div>
                <div class="progress-item">
                    <div class="progress-header"><span class="progress-label">Version</span></div>
                    <div class="progress-bar"><div class="progress-fill slate" style="width: 100%;"></div></div>
                    <div class="legend-item"><span class="legend-color slate"></span><span>NIV</span></div>
                </div>
            </div>

            <!-- Profile Status -->
            <div class="glass-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">Profile Status</h2>
                        <p class="card-subtitle">Completion of your account details</p>
                    </div>
                </div>
                <div class="donut-container">
                    <div class="donut-chart">
                        <svg width="140" height="140" viewBox="0 0 140 140">
                            <circle class="donut-bg" cx="70" cy="70" r="54"/>
                            <circle class="donut-segment" cx="70" cy="70" r="54" stroke="var(--blue)" stroke-dasharray="169.6 339.3" stroke-dashoffset="0"/>
                            <circle class="donut-segment" cx="70" cy="70" r="54" stroke="var(--gold)" stroke-dasharray="101.8 339.3" stroke-dashoffset="-169.6"/>
                            <circle class="donut-segment" cx="70" cy="70" r="54" stroke="var(--red)" stroke-dasharray="67.9 339.3" stroke-dashoffset="-271.4"/>
                        </svg>
                        <div class="donut-center">
                            <div class="donut-value">82%</div>
                            <div class="donut-label">Complete</div>
                        </div>
                    </div>
                    <div class="donut-legend">
                        <div class="legend-item"><span class="legend-color cyan"></span><span>Congratulations your profile is complete</span></div>
                        <div class="legend-item"><span class="legend-color slate"></span><span>Update the missing information in your profile settings</span></div>
                    </div>
                </div>
            </div>

            <!-- Daily Horoscope -->
            <div class="glass-card">
                <div class="card-header">
                    <div>
                        <h2 class="card-title">Daily Horoscope</h2>
                        <p class="card-subtitle">A calm and focused day lies ahead.</p>
                    </div>
                </div>
                <div class="donut-container">
                    <div class="donut-chart">
                        <div class="zodiac-menu">
                            <img src="assets/img/zodiac/aquarius.jpg" alt="User zodiac" class="zodiac">
                        </div>
                    </div>
                    <div class="zodiac-legend">
                        <div class="legend-item"><span class="legend-color magenta"></span><span>Aquarius</span></div>
                        <div class="legend-item"><span class="legend-color slate"></span><span>Feb 9, 2026</span></div>
                        <div class="legend-item"><span class="legend-color cyan"></span><span>News could come your way, perhaps through local magazines or newspapers, about possible renovations occurring in your community. These changes could be rather controversial, Aquarius, so don't be surprised if you hear a lot of gossip and a number of opinions, both pro and con, on the matter. You might want to delve more deeply into the facts before forming an opinion of your own. This could prove quite enlightening! </span></div>
                    </div>
                </div>
            </div>

        </section>
    </main>
</div>

<!-- Footer -->
<footer class="site-footer">
    <div class="copy">&copy; <script>document.write(new Date().getFullYear());</script> Ominet by Aerosimo Ltd. All rights reserved.</div>
</footer>

<script src="assets/js/script.js"></script>
</body>
</html>